package com.payflow.payflow.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payflow.payflow.service.auth.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // 메소드 수준 보안 활성화(ex. @PreAuthorize)
@RequiredArgsConstructor
@Slf4j
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final JwtAuthenticationEntryPoint jwtEntryPoint;
    private final CustomUserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;
    private final Oauth2SuccessHandler oauth2SuccessHandler;
    private final RestLoginSuccessHandler restLoginSuccessHandler;
    private final AuthFailureHandler authFailureHandler;
    private final CustomOAuth2UserService oAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // CORS 설정 적용
                .cors(cors -> cors.configurationSource(corsConfigSource()))
                // CSRF 비활성화 (JWT 사용 시 불필요)
                .csrf(AbstractHttpConfigurer::disable)
                // HTTP 기본 인증 비활성화
                .httpBasic(AbstractHttpConfigurer::disable)
                // 폼 로그인 비활성화 (REST API 방식)
                .formLogin(AbstractHttpConfigurer::disable)
                // 세션 사용 안함 (STATELESS)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // 요청 권한 설정
                .authorizeHttpRequests(auth -> auth
                        // 인증 없이 접근 허용할 경로
                        .requestMatchers(
                                "/",
                                "/auth/**",
                                "/oauth2/**",
                                "/error",
                                "/v3/api-docs/**",
                                "/api/v1/products",
                                "/api/v1/products/**",
                                "/api/v1/s3/presigned-url",
                                "/api/v1/auth/signup",
                                "/api/v1/auth/refresh",
                                "/api/v1/auth/logout"
                        ).permitAll()
                        // ADMIN 역할만 접근 가능한 경로
                        .requestMatchers(
                                "/api/admin/**"
                        ).hasRole("ADMIN")
                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(endpoint -> endpoint
                                .userService(oAuth2UserService)
                        )
                        .successHandler(oauth2SuccessHandler)
                        .failureHandler(authFailureHandler)
                )


                // 예외 처리
                .exceptionHandling(ex -> ex
                        // 인증 실패 시 처리
                        .authenticationEntryPoint(jwtEntryPoint)
                        // 인가 실패 시 처리
                        .accessDeniedHandler(new JwtAccessDeniedHandler(objectMapper)))
                // 일반 로그인 필터 추가
                .addFilterBefore(emailPasswordAuthFilter(authenticationManager(), objectMapper), UsernamePasswordAuthenticationFilter.class)
                // JWT 인증 필터 추가
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // 일반 로그인 필터 빈 등록
    @Bean
    public EmailPasswordAuthFilter emailPasswordAuthFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) throws Exception {
        EmailPasswordAuthFilter filter = new EmailPasswordAuthFilter(new AntPathRequestMatcher("/api/v1/auth/login", "POST"), authenticationManager, objectMapper);
        filter.setAuthenticationSuccessHandler(restLoginSuccessHandler);
        filter.setAuthenticationFailureHandler(authFailureHandler);
        return filter;
    }

    // 인증 관리자 구성
    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        // DB 기반 인증 제공자 생성
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        // 사용자 상세 서비스 설정
        daoProvider.setUserDetailsService(userDetailsService);
        // 패스워드 인코더 설정
        daoProvider.setPasswordEncoder(passwordEncoder());

        // 인증 제공자 등록
        return new ProviderManager(daoProvider);
    }

    // 실무용 CORS 설정
    @Bean
    public CorsConfigurationSource corsConfigSource() {
        CorsConfiguration config = new CorsConfiguration();
        // 허용할 오리진 목록
        config.setAllowedOrigins(Arrays.asList("https://production.com", "http://localhost:3000"));
        // 허용할 HTTP 메소드
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        // 허용할 헤더
        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        // 노출할 헤더
        config.setExposedHeaders(Arrays.asList("Authorization", "X-Refresh-Token"));
        // 자격 증명 허용
        config.setAllowCredentials(true);
        // 캐시 지속 시간(초)
        config.setMaxAge(3600L);

        // 모든 경로에 CORS 설정 적용
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    // 패스워드 인코더 빈 등록
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    // 웹 보안 커스터마이저 (보안 예외 적용 경로)
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                // 보안 필터 적용 제외할 경로
                .requestMatchers("/favicon.ico", "/static/**", "/resources/**", "/h2-console/**");
    }
}

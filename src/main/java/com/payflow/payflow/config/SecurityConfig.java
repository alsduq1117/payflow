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
                .cors(cors -> cors.configurationSource(corsConfigSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
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
                                "/api/v1/auth/logout",
                                "/api/v1/checkout",
                                "/api/v1/toss/confirm",
                                "/api/v1/purchases/check",
                                "/actuator/health",
                                "/actuator/prometheus",
                                "/api/v1/admin/**",
                                "/api/v1/carts/cart-item/**"
                        ).permitAll()
                        // ADMIN 역할만 접근 가능한 경로
//                        .requestMatchers(
//                                "/api/v1/admin/**"
//                        ).hasRole("ADMIN")
                        .anyRequest().authenticated())
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(endpoint -> endpoint
                                .userService(oAuth2UserService)
                        )
                        .successHandler(oauth2SuccessHandler)
                        .failureHandler(authFailureHandler)
                )
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(jwtEntryPoint)
                        .accessDeniedHandler(new JwtAccessDeniedHandler(objectMapper)))
                .addFilterBefore(emailPasswordAuthFilter(authenticationManager(), objectMapper), UsernamePasswordAuthenticationFilter.class)
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
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(userDetailsService);
        daoProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(daoProvider);
    }

    @Bean
    public CorsConfigurationSource corsConfigSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("https://payflow.my", "http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        config.setExposedHeaders(Arrays.asList("Authorization", "X-Refresh-Token"));
        config.setAllowCredentials(true);
        config.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                // 보안 필터 적용 제외할 경로
                .requestMatchers("/favicon.ico", "/static/**", "/resources/**", "/h2-console/**");
    }
}

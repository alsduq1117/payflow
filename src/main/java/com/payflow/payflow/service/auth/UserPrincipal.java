package com.payflow.payflow.service.auth;

import com.payflow.payflow.domain.auth.AuthProvider;
import com.payflow.payflow.domain.auth.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

// UserPrincipal.java
/**
 * 사용자 인증 정보를 담는 핵심 클래스
 * - UserDetails(일반 로그인)와 OAuth2User(OAuth2 로그인) 인터페이스 동시 구현
 * - 두 인증 방식을 통합하여 처리할 수 있도록 설계
 */
public class UserPrincipal implements UserDetails, OAuth2User {

    private final User user;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    // 생성자: User 엔티티를 기반으로 생성
    public UserPrincipal(User user) {
        this.user = user;
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    // OAuth2 로그인용 생성자
    public UserPrincipal(User user, Map<String, Object> attributes) {
        this(user);
        this.attributes = attributes;
    }

    // UserDetails 인터페이스 구현 ================================
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    public User getUser() {
        return user;
    }

    // OAuth2User 구현
    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return email;
    }

    public AuthProvider getProvider() {
        return user.getProvider();
    }

    //
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

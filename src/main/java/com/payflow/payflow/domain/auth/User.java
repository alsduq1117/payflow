package com.payflow.payflow.domain.auth;

import com.payflow.payflow.domain.common.BaseOnlyCreated;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"email", "provider"})})
public class User extends BaseOnlyCreated {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String nickname;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Set<Role> roles = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "provider", nullable = false)
    private AuthProvider provider;

    // OAuth2 사용자 생성 메서드
    public static User createOAuth2User(String email,AuthProvider provider) {
        User user = new User();
        user.email = email;
        user.provider = provider;
        user.roles.add(Role.USER);
        return user;
    }

    // 일반 사용자 생성 메서드
    public static User createLocalUser(String email, String password) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.provider = AuthProvider.LOCAL;
        user.roles.add(Role.USER);
        return user;
    }
}

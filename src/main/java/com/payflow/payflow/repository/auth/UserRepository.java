package com.payflow.payflow.repository.auth;

import com.payflow.payflow.domain.auth.User;
import com.payflow.payflow.dto.auth.UserProfileResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {
    Optional<User> findByEmail(String email);
}

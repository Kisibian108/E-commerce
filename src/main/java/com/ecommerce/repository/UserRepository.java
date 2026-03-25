package com.ecommerce.repository;

import com.ecommerce.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<Users, UUID> {

    @Query("SELECT u FROM Users u JOIN FETCH u.roles WHERE u.username = :username")
    Optional<Users> findByUsername(String username);

    Optional<Users> findByEmail(String email);
}

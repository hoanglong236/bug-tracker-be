package com.spring.bugtrackerbe.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT u FROM User u " +
            "WHERE u.deleteFlag = FALSE AND u.email = ?1")
    Optional<User> findByEmail(String email);
}

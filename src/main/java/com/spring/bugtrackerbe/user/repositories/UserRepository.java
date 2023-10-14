package com.spring.bugtrackerbe.user.repositories;

import com.spring.bugtrackerbe.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT u FROM User u " +
            "WHERE u.deleteFlag = FALSE AND u.email = ?1")
    Optional<User> findByEmail(String email);

    @Transactional(readOnly = true)
    @Query("SELECT u FROM User u " +
            "WHERE u.deleteFlag = FALSE AND u.role = 'USER'")
    List<User> findAllUsersWithRoleUser();
}

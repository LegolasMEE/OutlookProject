package com.Trainee.ProjectOutlook.repository;

import com.Trainee.ProjectOutlook.entity.User;
import com.Trainee.ProjectOutlook.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    List<User> findByRole(Role role);

    List<User> findBySpecialization(String specialization);
}

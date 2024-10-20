package com.Trainee.ProjectOutlook.service;

import com.Trainee.ProjectOutlook.entity.User;
import com.Trainee.ProjectOutlook.enums.Role;
import com.Trainee.ProjectOutlook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAllExperts() {
        return userRepository.findByRole(Role.EXPERT);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
    }
}

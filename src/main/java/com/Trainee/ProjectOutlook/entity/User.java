package com.Trainee.ProjectOutlook.entity;

import com.Trainee.ProjectOutlook.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String specialization;

    @Enumerated(EnumType.STRING)
    private Role role;

}


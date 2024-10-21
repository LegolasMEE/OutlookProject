package com.Trainee.ProjectOutlook.model;

import com.Trainee.ProjectOutlook.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllMeetingsByUserRequest {
    private Long userId;
    private Role role;
}

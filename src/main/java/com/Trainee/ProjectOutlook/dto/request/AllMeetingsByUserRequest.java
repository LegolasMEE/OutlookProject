package com.Trainee.ProjectOutlook.dto.request;

import com.Trainee.ProjectOutlook.enums.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllMeetingsByUserRequest {
    private Long userId;
    private Role role;
}

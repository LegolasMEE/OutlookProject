package com.Trainee.ProjectOutlook.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MeetingRequest {
    private Long userId;

    private Long expertId;

    private String name;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}

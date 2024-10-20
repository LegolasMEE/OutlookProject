package com.Trainee.ProjectOutlook.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class MeetingRequest {
    private Long userId;

    private Long expertId;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

}

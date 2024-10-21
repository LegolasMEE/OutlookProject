package com.Trainee.ProjectOutlook.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class MeetingUpdateRequest {
    private Long Id;

    private Long userId;

    private Long expertId;

    private String name;

    private String description;

    private String comment;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
}

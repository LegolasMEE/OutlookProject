package com.Trainee.ProjectOutlook.dto.request;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingSlotRequest {

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String name;

    private String description;

    private Long expertId;
}

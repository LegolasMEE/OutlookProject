package com.Trainee.ProjectOutlook.dto.response;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MeetingSlotResponse {

    private Long id;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private String name;

    private String description;

    private Long expertId;

    public MeetingSlotResponse(Long id, LocalDateTime startTime, LocalDateTime endTime, String name, String description, Long expertId) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.name = name;
        this.description = description;
        this.expertId = expertId;
    }

}

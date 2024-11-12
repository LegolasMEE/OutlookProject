package com.Trainee.ProjectOutlook.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MeetingResponse {
    private Long meetingId;

    private String name;

    private String expertName;

    private String userName;

    private String comment;

    private String description;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    public MeetingResponse(Long meetingId, String name, String expertName, String userName,
                           String comment, String description, LocalDateTime startTime, LocalDateTime endTime) {
        this.name = name;
        this.meetingId = meetingId;
        this.expertName = expertName;
        this.userName = userName;
        this.comment = comment;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}

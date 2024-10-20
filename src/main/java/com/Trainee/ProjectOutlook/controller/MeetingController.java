package com.Trainee.ProjectOutlook.controller;

import com.Trainee.ProjectOutlook.entity.Meeting;
import com.Trainee.ProjectOutlook.entity.User;
import com.Trainee.ProjectOutlook.model.MeetingRequest;
import com.Trainee.ProjectOutlook.service.MeetingService;
import com.Trainee.ProjectOutlook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserService userService;

    @PostMapping("/schedule")
    public Meeting scheduleMeeting(@RequestBody MeetingRequest meetingRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        if (!user.getRole().equals("USER")) {
            throw new RuntimeException("Only users with role USER can schedule meetings.");
        }
        return meetingService.scheduleMeeting(
                user.getId(),
                meetingRequest.getExpertId(),
                meetingRequest.getDescription(),
                meetingRequest.getStartTime(),
                meetingRequest.getEndTime()
        );
    }

    @GetMapping("/user/{userId}")
    public List<Meeting> getMeetingsByUser(@PathVariable Long userId) {
        return meetingService.getMeetingsByUser(userId);
    }

    @GetMapping("/expert/{expertId}")
    public List<Meeting> getMeetingsByExpert(@PathVariable Long expertId) {
        return meetingService.getMeetingsByExpert(expertId);
    }
}

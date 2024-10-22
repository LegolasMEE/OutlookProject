package com.Trainee.ProjectOutlook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Trainee.ProjectOutlook.entity.MeetingSlot;
import com.Trainee.ProjectOutlook.entity.User;
import com.Trainee.ProjectOutlook.enums.Role;
import com.Trainee.ProjectOutlook.model.MeetingSlotRequest;
import com.Trainee.ProjectOutlook.model.MeetingSlotsByExpertRequest;
import com.Trainee.ProjectOutlook.service.MeetingSlotService;
import com.Trainee.ProjectOutlook.service.UserService;

@RestController
@RequestMapping("api/slots")
public class MeetingSlotController {

    @Autowired
    private MeetingSlotService meetingSlotService;

    @Autowired
    private UserService userService;

    @PostMapping("/create-slot")
    public MeetingSlot createMeetingSlot(@RequestBody MeetingSlotRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());

        if (user.getRole() != Role.EXPERT) {
            System.out.println(user.getRole());
            throw new RuntimeException("Only experts can add new meeting slots!");
        }

        return meetingSlotService.createMeetingSlot(
                request.getName(),
                request.getExpertId(),
                request.getDescription(),
                request.getStartTime(),
                request.getEndTime()
        );
    }
    
    @GetMapping("/get-expert-slots")
    public List<MeetingSlot> getAllMeetingSlotsByExpert(@RequestBody MeetingSlotsByExpertRequest request) {

        return meetingSlotService.getMeetingSlotsByExpert(request.getExpertId());
    }
}

package com.Trainee.ProjectOutlook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.Trainee.ProjectOutlook.entity.MeetingSlot;
import com.Trainee.ProjectOutlook.entity.User;
import com.Trainee.ProjectOutlook.enums.Role;
import com.Trainee.ProjectOutlook.model.MeetingSlotDeletionRequest;
import com.Trainee.ProjectOutlook.model.MeetingSlotRequest;
import com.Trainee.ProjectOutlook.model.MeetingSlotResponse;
import com.Trainee.ProjectOutlook.service.MeetingSlotService;
import com.Trainee.ProjectOutlook.service.UserService;

import jakarta.persistence.EntityNotFoundException;

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
    public ResponseEntity<List<MeetingSlotResponse>> getAllMeetingSlotsByExpert(@RequestParam long expertId) {
        List<MeetingSlot> slots = meetingSlotService.getMeetingSlotsByExpert(expertId);

        List<MeetingSlotResponse> slotResponses = slots.stream()
                .map(slot -> new MeetingSlotResponse(slot.getId(), slot.getStartTime(), slot.getEndTime(), 
                        slot.getName(), slot.getDescription(), expertId))
                .toList();

        return new ResponseEntity<>(slotResponses, HttpStatus.OK);
    }

    @DeleteMapping("/delete-slot")
    public void deleteMeetingSlot(@RequestBody MeetingSlotDeletionRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUsername(currentUsername);
        if (currentUser.getRole() != Role.EXPERT) {
            throw new RuntimeException("Only experts can delete meeting slots!");
        }        

        MeetingSlot meetingSlot = meetingSlotService.getMeetingSlotById(request.getMeetingSlotId()).orElseThrow(() -> new EntityNotFoundException("Meeting slot not found!"));
        if (!meetingSlot.getExpert().getId().equals(currentUser.getId())) {
            throw new RuntimeException("Only creator of the meeting slot can delete it.");
        }
        meetingSlotService.delete(meetingSlot);
    }
}

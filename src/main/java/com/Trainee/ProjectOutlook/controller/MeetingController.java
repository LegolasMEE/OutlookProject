package com.Trainee.ProjectOutlook.controller;

import com.Trainee.ProjectOutlook.entity.Meeting;
import com.Trainee.ProjectOutlook.entity.User;
import com.Trainee.ProjectOutlook.enums.Role;
import com.Trainee.ProjectOutlook.model.*;
import com.Trainee.ProjectOutlook.service.MeetingService;
import com.Trainee.ProjectOutlook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserService userService;

    @PostMapping("/book-meeting")
    public void scheduleMeeting(@RequestBody MeetingRequest meetingRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        if (user.getRole() != Role.USER) {
            throw new RuntimeException("Only users with role USER can schedule meetings.");
        }
        meetingService.scheduleMeeting(
                user.getId(),
                meetingRequest.getExpertId(),
                meetingRequest.getName(),
                meetingRequest.getDescription(),
                meetingRequest.getStartTime(),
                meetingRequest.getEndTime()
        );
    }

    @GetMapping("/get-meeting")
    public ResponseEntity<List<MeetingResponse>> getMeetingsByUser(@RequestBody AllMeetingsByUserRequest allMeetingsByUserRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUsername(currentUsername);
        if(currentUser.getId().equals(allMeetingsByUserRequest.getUserId())) {
            List<Meeting> meetings;
            if (allMeetingsByUserRequest.getRole() == Role.USER) {
                meetings = meetingService.getMeetingsByUser(allMeetingsByUserRequest.getUserId());
            } else {
                meetings = meetingService.getMeetingsByExpert(allMeetingsByUserRequest.getUserId());
            }
            List<MeetingResponse> meetingResponses = meetings.stream()
                    .map(meeting -> new MeetingResponse(meeting.getId(), meeting.getName(), meeting.getExpert().getUsername(),
                            meeting.getUser().getUsername(), meeting.getComment(), meeting.getDescription(),
                            meeting.getStartTime(), meeting.getEndTime()))
                    .toList();
            return new ResponseEntity<>(meetingResponses, HttpStatus.OK);
        } else {
            // Если пользователь не имеет прав на просмотр, возвращаем ошибку
            throw new RuntimeException("Access denied: You can only view your own meetings.");
        }
    }

    @PatchMapping("/get-meeting")
    public void updateMeetingDescription(@RequestBody MeetingUpdateRequest updateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Ищем встречу по ID
        Meeting meeting = meetingService.getMeetingById(updateRequest.getMeetingId()).orElseThrow(() -> new RuntimeException("Meeting not found"));

        // Ищем текущего пользователя по имени
        User currentUser = userService.findByUsername(currentUsername);

        // Проверяем, что текущий пользователь либо создал встречу, либо является назначенным экспертом
        if (meeting.getUser().getId().equals(currentUser.getId()) ||
                meeting.getExpert().getId().equals(currentUser.getId())) {

            if (updateRequest.getComment() != null) {
                meeting.setComment(updateRequest.getComment());
            }
            if (updateRequest.getDescription() != null) {
                meeting.setDescription(updateRequest.getDescription());
            }
            if (updateRequest.getName() != null) {
                meeting.setName(updateRequest.getName());
            }
            if (updateRequest.getStartTime() != null) {
                meeting.setStartTime(updateRequest.getStartTime());
            }
            if (updateRequest.getEndTime() != null) {
                meeting.setEndTime(updateRequest.getEndTime());
            }
            meetingService.save(meeting);
        } else {
            // Если пользователь не имеет прав на изменение, возвращаем ошибку
            throw new RuntimeException("Access denied: Only the creator or assigned expert can edit the description.");
        }
    }

    @DeleteMapping("/get-meeting")
    public void deleteMeeting(@RequestBody MeetingDeleteRequest meetingDeleteRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUsername(currentUsername);
        Meeting meeting = meetingService.getMeetingById(meetingDeleteRequest.getMeetingId()).orElseThrow(() -> new RuntimeException("Meeting not found"));
        if (meeting.getUser().getId().equals(currentUser.getId()) ||
                meeting.getExpert().getId().equals(currentUser.getId())) {
            meetingService.delete(meeting);
        } else {
            // Если пользователь не имеет прав на изменение, возвращаем ошибку
            throw new RuntimeException("Access denied: Only the creator or assigned expert can edit the description.");
        }
    }

    @GetMapping("/get-reviewers")
    public ResponseEntity<List<GetReviewersResponse>> getAllReviewers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUsername(currentUsername);
        if (currentUser.getRole() == Role.USER) {
            List<User> users = userService.findAllExperts();
            List<GetReviewersResponse> reviewersResponses = users.stream()
                    .map(user -> new GetReviewersResponse(user.getUsername(), user.getId(), user.getSpecialization()))
                    .toList();
            return new ResponseEntity<>(reviewersResponses, HttpStatus.OK);
        } else
            throw new RuntimeException("Access denied: Only users with role USER can view experts list.");

    }
}

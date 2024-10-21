package com.Trainee.ProjectOutlook.controller;

import com.Trainee.ProjectOutlook.entity.Meeting;
import com.Trainee.ProjectOutlook.entity.User;
import com.Trainee.ProjectOutlook.enums.Role;
import com.Trainee.ProjectOutlook.model.AllMeetingsByUserRequest;
import com.Trainee.ProjectOutlook.model.MeetingRequest;
import com.Trainee.ProjectOutlook.model.MeetingUpdateRequest;
import com.Trainee.ProjectOutlook.service.MeetingService;
import com.Trainee.ProjectOutlook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MeetingController {

    @Autowired
    private MeetingService meetingService;

    @Autowired
    private UserService userService;

    @PostMapping("/book-meeting")
    public Meeting scheduleMeeting(@RequestBody MeetingRequest meetingRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUsername(auth.getName());
        if (user.getRole() != Role.USER) {
            System.out.println(user.getRole());
            throw new RuntimeException("Only users with role USER can schedule meetings.");
        }
        return meetingService.scheduleMeeting(
                user.getId(),
                meetingRequest.getExpertId(),
                meetingRequest.getName(),
                meetingRequest.getDescription(),
                meetingRequest.getComment(),
                meetingRequest.getStartTime(),
                meetingRequest.getEndTime()
        );
    }

    @GetMapping("/get-meeting")
    public List<Meeting> getMeetingsByUser(@RequestBody AllMeetingsByUserRequest allMeetingsByUserRequest) {
        if(allMeetingsByUserRequest.getRole() == Role.USER)
            return meetingService.getMeetingsByUser(allMeetingsByUserRequest.getUserId());
        else
            return meetingService.getMeetingsByExpert(allMeetingsByUserRequest.getUserId());
    }

    @GetMapping("/get-artifacts")
    public List<Meeting> getArtifactsByUser(@RequestBody AllMeetingsByUserRequest allMeetingsByUserRequest) {
        if(allMeetingsByUserRequest.getRole() == Role.USER)
            return meetingService.getMeetingsByUser(allMeetingsByUserRequest.getUserId());
        else
            return meetingService.getMeetingsByExpert(allMeetingsByUserRequest.getUserId());
    }

    @PatchMapping("/artifacts-get-comment")
    public Meeting updateMeetingDescription(@RequestBody MeetingUpdateRequest updateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();

        // Ищем встречу по ID
        Meeting meeting = meetingService.getMeetingById(updateRequest.getId()).orElseThrow(() -> new RuntimeException("Meeting not found"));

        // Ищем текущего пользователя по имени
        User currentUser = userService.findByUsername(currentUsername);

        // Проверяем, что текущий пользователь либо создал встречу, либо является назначенным экспертом
        if (meeting.getUser().getId().equals(currentUser.getId()) ||
                meeting.getExpert().getId().equals(currentUser.getId())) {

            // Обновляем описание встречи
            meeting.setComment(updateRequest.getComment());
            return meetingService.save(meeting);
        } else {
            // Если пользователь не имеет прав на изменение, возвращаем ошибку
            throw new RuntimeException("Access denied: Only the creator or assigned expert can edit the description.");
        }
    }

    @GetMapping("/get-reviewers")
    public List<User> getAllReviewers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userService.findByUsername(currentUsername);
        if(currentUser.getRole() == Role.USER)
            return userService.findAllExperts();
        else
            throw new RuntimeException("Access denied: Only users with role USER can view experts list.");

    }

}

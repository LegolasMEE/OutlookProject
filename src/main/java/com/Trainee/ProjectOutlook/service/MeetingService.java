package com.Trainee.ProjectOutlook.service;

import com.Trainee.ProjectOutlook.dto.request.MeetingDeleteRequest;
import com.Trainee.ProjectOutlook.dto.request.MeetingRequest;
import com.Trainee.ProjectOutlook.dto.request.MeetingUpdateRequest;
import com.Trainee.ProjectOutlook.dto.request.ReviewersFilterRequest;
import com.Trainee.ProjectOutlook.dto.response.GetReviewersResponse;
import com.Trainee.ProjectOutlook.dto.response.MeetingResponse;
import com.Trainee.ProjectOutlook.entity.Meeting;
import com.Trainee.ProjectOutlook.entity.User;
import com.Trainee.ProjectOutlook.enums.Role;
import com.Trainee.ProjectOutlook.repository.MeetingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void scheduleMeeting(MeetingRequest meetingRequest, Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        User expert = userService.findById(meetingRequest.getExpertId());

        Meeting meeting = new Meeting();
        meeting.setUser(user);
        meeting.setExpert(expert);
        meeting.setName(meetingRequest.getName());
        meeting.setDescription(meetingRequest.getDescription());
        meeting.setStartTime(meetingRequest.getStartTime());
        meeting.setEndTime(meetingRequest.getEndTime());

        meetingRepository.save(meeting);
    }
    @Transactional
    public ResponseEntity<List<MeetingResponse>> getMeetingByUserId(Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        List<Meeting> meetings;
        if (("ROLE" + user.getRole()).equals(Role.USER.toString())) {
            meetings = meetingRepository.findByUserId(user.getId());
        } else {
            meetings = meetingRepository.findByExpertId(user.getId());
        }
        List<MeetingResponse> meetingResponses = meetings.stream()
                .map(meeting -> new MeetingResponse(meeting.getId(), meeting.getName(), meeting.getExpert().getUsername(),
                        meeting.getUser().getUsername(), meeting.getComment(), meeting.getDescription(),
                        meeting.getStartTime(), meeting.getEndTime()))
                .toList();
        return new ResponseEntity<>(meetingResponses, HttpStatus.OK);
    }

    @Transactional
    public void delete(Authentication auth, MeetingDeleteRequest meetingDeleteRequest) {
        User currentUser = userService.findByUsername(auth.getName());
        Meeting meeting = meetingRepository.findById(meetingDeleteRequest.getMeetingId()).orElseThrow(() -> new EntityNotFoundException("Meeting not found"));
        if (meeting.getUser().getId().equals(currentUser.getId()) ||
                meeting.getExpert().getId().equals(currentUser.getId())) {
            meetingRepository.delete(meeting);
        } else {
            throw new RuntimeException("Access denied: Only the creator or assigned expert can delete meeting.");
        }
    }

    @Transactional
    public void update(Authentication auth, MeetingUpdateRequest updateRequest) {
        User currentUser = userService.findByUsername(auth.getName());
        Meeting meeting = meetingRepository.findById(updateRequest.getMeetingId()).orElseThrow(() -> new EntityNotFoundException("Meeting not found"));
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
            meetingRepository.save(meeting);
        } else {
            throw new RuntimeException("Access denied: Only the creator or assigned expert can edit the description.");
        }
    }

    @Transactional
    public ResponseEntity<List<GetReviewersResponse>> getReviewers(ReviewersFilterRequest request) {
        List<User> users;
        if(request.getSpecialization() != null) {
            users = userService.findBySpecialization(request.getSpecialization());
        } else {
            users = userService.findAllExperts();
        }
        List<GetReviewersResponse> reviewersResponses = users.stream()
                .map(user -> new GetReviewersResponse(user.getUsername(), user.getId(), user.getSpecialization()))
                .toList();
        return new ResponseEntity<>(reviewersResponses, HttpStatus.OK);
    }
}

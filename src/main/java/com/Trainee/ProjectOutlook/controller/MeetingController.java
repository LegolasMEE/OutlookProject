package com.Trainee.ProjectOutlook.controller;

import com.Trainee.ProjectOutlook.dto.request.MeetingDeleteRequest;
import com.Trainee.ProjectOutlook.dto.request.MeetingRequest;
import com.Trainee.ProjectOutlook.dto.request.MeetingUpdateRequest;
import com.Trainee.ProjectOutlook.dto.request.ReviewersFilterRequest;
import com.Trainee.ProjectOutlook.dto.response.GetReviewersResponse;
import com.Trainee.ProjectOutlook.dto.response.MeetingResponse;
import com.Trainee.ProjectOutlook.rateLimiter.RateLimit;
import com.Trainee.ProjectOutlook.service.ExpertService;
import com.Trainee.ProjectOutlook.service.MeetingService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MeetingController {

    private final MeetingService meetingService;
    private final ExpertService expertService;

    @PostMapping("/book-meeting")
    @PreAuthorize("hasRole('USER')")
    @RateLimit(capacity = 5, refillTokens = 5, refillPeriod = 60)
    public void scheduleMeeting(@RequestBody MeetingRequest meetingRequest) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        meetingService.scheduleMeeting(meetingRequest, auth);
    }

    @GetMapping("/get-meeting")
    @RateLimit(capacity = 10, refillTokens = 10, refillPeriod = 60)
    public ResponseEntity<List<MeetingResponse>> getMeetingsByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return meetingService.getMeetingByUserId(authentication);
    }

    @PatchMapping("/get-meeting")
    @RateLimit(capacity = 5, refillTokens = 5, refillPeriod = 60)
    public void updateMeetingDescription(@RequestBody MeetingUpdateRequest updateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        meetingService.update(authentication, updateRequest);
    }

    @DeleteMapping("/get-meeting")
    @RateLimit(capacity = 10, refillTokens = 5, refillPeriod = 60)
    public void deleteMeeting(@RequestBody MeetingDeleteRequest meetingDeleteRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        meetingService.delete(authentication, meetingDeleteRequest);
    }

    @PostMapping("/get-reviewers")
    @PreAuthorize("hasRole('USER')")
    @RateLimit(capacity = 10, refillTokens = 10, refillPeriod = 30)
    public ResponseEntity<List<GetReviewersResponse>> getAllReviewers(@RequestBody ReviewersFilterRequest request) throws JsonProcessingException {
        return expertService.getExperts(request.getSpecialization());
    }
}

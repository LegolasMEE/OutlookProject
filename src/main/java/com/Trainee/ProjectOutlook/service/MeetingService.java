package com.Trainee.ProjectOutlook.service;

import com.Trainee.ProjectOutlook.entity.Meeting;
import com.Trainee.ProjectOutlook.entity.User;
import com.Trainee.ProjectOutlook.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private UserService userService;

    @Transactional
    public void scheduleMeeting(Long userId, Long expertId, String name, String description, LocalDateTime startTime, LocalDateTime endTime) {
        User user = userService.findById(userId);
        User expert = userService.findById(expertId);

        Meeting meeting = new Meeting();
        meeting.setUser(user);
        meeting.setExpert(expert);
        meeting.setName(name);
        meeting.setDescription(description);
        meeting.setStartTime(startTime);
        meeting.setEndTime(endTime);

        meetingRepository.save(meeting);
    }

    @Transactional
    public List<Meeting> getMeetingsByUser(Long userId) {
        return meetingRepository.findByUserId(userId);
    }

    @Transactional
    public List<Meeting> getMeetingsByExpert(Long expertId) {
        return meetingRepository.findByExpertId(expertId);
    }

    @Transactional
    public Optional<Meeting> getMeetingById(Long meetingId) {
        return meetingRepository.findById(meetingId);
    }

    @Transactional
    public void save(Meeting meeting) {
        meetingRepository.save(meeting);
    }

    @Transactional
    public void delete(Meeting meeting) {
        meetingRepository.delete(meeting);
    }
}

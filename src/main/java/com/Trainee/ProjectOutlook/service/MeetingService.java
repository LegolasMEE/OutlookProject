package com.Trainee.ProjectOutlook.service;

import com.Trainee.ProjectOutlook.entity.Meeting;
import com.Trainee.ProjectOutlook.entity.User;
import com.Trainee.ProjectOutlook.repository.MeetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MeetingService {

    @Autowired
    private MeetingRepository meetingRepository;

    @Autowired
    private UserService userService;

    // Создание новой встречи
    public Meeting scheduleMeeting(Long userId, Long expertId, String description, LocalDateTime startTime, LocalDateTime endTime) {
        User user = userService.findById(userId);
        User expert = userService.findById(expertId);

        Meeting meeting = new Meeting();
        meeting.setUser(user);
        meeting.setExpert(expert);
        meeting.setDescription(description);
        meeting.setStartTime(startTime);
        meeting.setEndTime(endTime);

        return meetingRepository.save(meeting);
    }

    // Получение всех встреч пользователя
    public List<Meeting> getMeetingsByUser(Long userId) {
        return meetingRepository.findByUserId(userId);
    }

    // Получение всех встреч эксперта
    public List<Meeting> getMeetingsByExpert(Long expertId) {
        return meetingRepository.findByExpertId(expertId);
    }
}

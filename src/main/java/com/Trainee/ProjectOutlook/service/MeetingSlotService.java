package com.Trainee.ProjectOutlook.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Trainee.ProjectOutlook.entity.MeetingSlot;
import com.Trainee.ProjectOutlook.entity.User;
import com.Trainee.ProjectOutlook.repository.MeetingSlotRepository;

@Service
public class MeetingSlotService {
    @Autowired
    private MeetingSlotRepository meetingSlotRepository;

    @Autowired
    private UserService userService;

    //добавление слота
    @Transactional
    public MeetingSlot createMeetingSlot(String name, Long expertId, String description, 
            LocalDateTime startTime, LocalDateTime endTime) {
        User expert = userService.findById(expertId);

        MeetingSlot slot = new MeetingSlot();
        slot.setName(name);
        slot.setExpert(expert);
        slot.setDescription(description);
        slot.setStartTime(startTime);
        slot.setEndTime(endTime);

        return meetingSlotRepository.save(slot);
    }

    //получение всех слотов эксперта
    @Transactional
    public List<MeetingSlot> getMeetingSlotsByExpert(Long userId) {
        return meetingSlotRepository.findByExpertId(userId);
    }

    //удаление слота
    @Transactional
    public void save(MeetingSlot meetingSlot) {
        meetingSlotRepository.delete(meetingSlot);
    }
}

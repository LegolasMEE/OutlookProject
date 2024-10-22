package com.Trainee.ProjectOutlook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.Trainee.ProjectOutlook.entity.MeetingSlot;

public interface MeetingSlotRepository extends JpaRepository<MeetingSlot, Long> {
    List<MeetingSlot> findByExpertId(Long expertId);
}

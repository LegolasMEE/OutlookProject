package com.Trainee.ProjectOutlook.repository;

import com.Trainee.ProjectOutlook.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    List<Meeting> findByUserId(Long userId); // Найти встречи по пользователю

    List<Meeting> findByExpertId(Long expertId); // Найти встречи по эксперту

}
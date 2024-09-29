package com.gradingsystem.gradingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gradingsystem.gradingsystem.model.Assignment;

import java.time.LocalDateTime;
import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByDueDateAfter(LocalDateTime now);
}
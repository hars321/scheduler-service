package com.intuit.repository;

import com.intuit.core.entity.ScheduledJob;
import com.intuit.core.enums.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ScheduledJobRepository extends JpaRepository<ScheduledJob, Long> {
    List<ScheduledJob> findByTriggerTimeBeforeAndStatus(Long timeStamp, JobStatus status);
}

package com.intuit.service;

import com.intuit.client.ExternalServiceClient;
import com.intuit.core.entity.ScheduledJob;
import com.intuit.core.enums.JobStatus;
import com.intuit.repository.ScheduledJobRepository;
import com.intuit.util.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class SchedulerService {

    @Autowired
    private ScheduledJobRepository scheduledJobRepository;

    @Autowired
    private ExternalServiceClient externalServiceClient;

    @Scheduled(fixedRate = 60000)
    public void processScheduledJobs() {
        Long currentTimeStamp = TimeUtils.getCurrentTimeStamp();
        List<ScheduledJob> jobsToRun = scheduledJobRepository.findByTriggerTimeBeforeAndStatus(currentTimeStamp, JobStatus.PENDING);

        for (ScheduledJob job : jobsToRun) {
            try {
                executeJob(job);
                job.setStatus(JobStatus.COMPLETED);
            } catch (Exception e) {
                job.setStatus(JobStatus.FAILED);
            } finally {
                scheduledJobRepository.save(job);
            }
        }
    }

    private void executeJob(ScheduledJob job) {
        try {
            String response = externalServiceClient.sendNotification("someParam", job.getRequestBody());
        } catch (Exception e) {
            throw e;
        }
    }
}

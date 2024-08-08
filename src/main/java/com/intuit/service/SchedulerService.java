package com.intuit.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intuit.client.ExternalServiceClient;
import com.intuit.core.entity.ScheduledJob;
import com.intuit.core.enums.JobStatus;
import com.intuit.repository.ScheduledJobRepository;
import com.intuit.util.TimeUtils;
import com.intuit.util.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

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

    private void executeJob(ScheduledJob job) throws JsonProcessingException {
        try {
            String url = UrlUtils.constructUrlWithParams(job.getUrl(),job.getRequestParams());
            HttpMethod method = HttpMethod.valueOf(job.getRequestMethod()); // Convert string to HttpMethod
            Object requestBody = null;
            if(!StringUtils.isEmpty(requestBody)){
                requestBody = new ObjectMapper().readValue(job.getRequestBody(),Object.class);
            }

            String response = externalServiceClient.makeHttpCall(url, method, requestBody);
        } catch (Exception e) {
            throw e;
        }
    }
}

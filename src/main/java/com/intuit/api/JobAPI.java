package com.intuit.api;

import com.intuit.core.entity.ApiResponse;
import com.intuit.core.entity.ScheduledJob;
import com.intuit.core.enums.JobStatus;
import com.intuit.repository.ScheduledJobRepository;
import com.intuit.util.ResponseUtil;
import com.intuit.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/jobs")
public class JobAPI {

    @Autowired
    private ScheduledJobRepository scheduledJobRepository;

    @PostMapping
    public ResponseEntity<ApiResponse<Object>> scheduleJob(
            @RequestParam Long triggerTime,
            @RequestParam String url,
            @RequestBody String requestBody) {

        if (triggerTime < TimeUtils.getCurrentTimeStamp()) {
            return ResponseUtil.errorResponse("Trigger time must be in the future.",HttpStatus.BAD_REQUEST);
        }

        ScheduledJob job = new ScheduledJob();
        job.setTriggerTime(triggerTime);
        job.setUrl(url);
        job.setRequestBody(requestBody);
        job.setStatus(JobStatus.PENDING);

        ScheduledJob savedJob = scheduledJobRepository.save(job);
        return ResponseUtil.successResponse(savedJob,"success");
    }
}

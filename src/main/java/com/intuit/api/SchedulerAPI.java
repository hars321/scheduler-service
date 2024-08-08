package com.intuit.api;

import com.intuit.core.entity.ApiResponse;
import com.intuit.core.entity.ScheduledJob;
import com.intuit.core.enums.JobStatus;
import com.intuit.repository.ScheduledJobRepository;
import com.intuit.util.ResponseUtil;
import com.intuit.util.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api/v1/scheduler", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SchedulerAPI {

    @Autowired
    private ScheduledJobRepository scheduledJobRepository;

    @PostMapping("/new")
    public ResponseEntity<ApiResponse<Object>> scheduleJob(
            @RequestBody ScheduledJob scheduledJob) {

        if(Objects.isNull(scheduledJob)){
            return ResponseUtil.errorResponse("Invalid Request",HttpStatus.BAD_REQUEST);
        }
        if (scheduledJob.getTriggerTime() < TimeUtils.getCurrentTimeStamp()) {
            return ResponseUtil.errorResponse("Trigger time must be in the future.",HttpStatus.BAD_REQUEST);
        }
        scheduledJob.setStatus(JobStatus.PENDING);
        ScheduledJob savedJob = scheduledJobRepository.save(scheduledJob);
        return ResponseUtil.successResponse(savedJob,"success");
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ScheduledJob>>> getActiveJobs(){
        return ResponseUtil.successResponse(scheduledJobRepository.findAll());
    }
}

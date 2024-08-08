package com.intuit.core.entity;

import com.intuit.core.enums.JobStatus;
import com.intuit.service.MapToJsonConverter;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Map;

@Entity
@Data
@Table(name = "scheduled_jobs")
public class ScheduledJob {

    public ScheduledJob() {
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getTriggerTime() {
        return triggerTime;
    }

    public void setTriggerTime(Long triggerTime) {
        this.triggerTime = triggerTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public JobStatus getStatus() {
        return status;
    }

    public void setStatus(JobStatus status) {
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "trigger_time", nullable = false)
    private Long triggerTime;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "request_body", columnDefinition = "TEXT")
    private String requestBody;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private JobStatus status;

    @Column(name = "request_method", nullable = false)
    private String requestMethod;

    @Column(name = "request_params", nullable = false)
    private Map<String, String> requestParams;
    // Getters and Setters


    public ScheduledJob(Long id, Long triggerTime, String url, String requestBody, JobStatus status, String requestMethod, Map<String, String> requestParams) {
        this.id = id;
        this.triggerTime = triggerTime;
        this.url = url;
        this.requestBody = requestBody;
        this.status = JobStatus.PENDING;
        this.requestMethod = requestMethod;
        this.requestParams = requestParams;
    }

    public ScheduledJob(Long triggerTime, String url, String requestBody, String requestMethod, Map<String, String> requestParams) {
        this.triggerTime = triggerTime;
        this.url = url;
        this.requestBody = requestBody;
        this.requestMethod = requestMethod;
        this.requestParams = requestParams;
    }

    public Map<String, String> getRequestParams() {
        return requestParams;
    }

    @Convert(converter = MapToJsonConverter.class)
    public void setRequestParams(Map<String, String> requestParams) {
        this.requestParams = requestParams;
    }
}

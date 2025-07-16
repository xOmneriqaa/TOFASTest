package com.tofas.yky.model;
/* T40127 @ 16.11.2015. */

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(schema = "TFS_YKY", name = "YKY_JOB_RUN_LOGS")
public class JobRunLog {

    @Id
    @SequenceGenerator(name="SEQ_YKY_JOB_RUN_LOGS", sequenceName="SEQ_YKY_JOB_RUN_LOGS", allocationSize = 1)
    @GeneratedValue(generator="SEQ_YKY_JOB_RUN_LOGS", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "JOB_NAME")
    private String jobName;

    @Column(name = "START_TIME")
    private Timestamp startTime;

    @Column(name = "END_TIME")
    private Timestamp endTime;

    @Column(name = "RUN_MESSAGES")
    private String runMessages;

    @Column(name = "EXCEPTION_KEY")
    private String exceptionKey;

    public Long getDurationInMilliseconds() {
        if(startTime != null && endTime != null) {
            return endTime.getTime() - startTime.getTime();
        } else {
            return -1L;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public String getRunMessages() {
        return runMessages;
    }

    public void setRunMessages(String runMessages) {
        this.runMessages = runMessages;
    }

    public String getExceptionKey() {
        return exceptionKey;
    }

    public void setExceptionKey(String exceptionKey) {
        this.exceptionKey = exceptionKey;
    }
}

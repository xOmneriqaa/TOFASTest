package com.tofas.yky.events.scheduled;
/* T40127 @ 16.11.2015. */

import com.tofas.core.common.utility.TfLogWriterUtility;
import com.tofas.yky.model.JobRunLog;
import com.tofas.yky.repositories.JobRunLogRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

public abstract class AbstractLoggableScheduledJob {

    @Autowired
    JobRunLogRepository jobRunLogRepository;

    @Autowired
    TfLogWriterUtility tfLogWriterUtility;

    private String name;

    private JobRunLog log;

    public AbstractLoggableScheduledJob(String name) {
        this.name = name;
    }

    public final String getName() {
        return name;
    }

    protected final void logStartTime() {
        log = new JobRunLog();
        log.setJobName(getName());
        log.setStartTime(new Timestamp(System.currentTimeMillis()));
        log = jobRunLogRepository.save(log);
    }

    protected final void logEndTime() {
        log.setEndTime(new Timestamp(System.currentTimeMillis()));
        jobRunLogRepository.save(log);
    }


    protected final void addMessage(String message) {
        log.setRunMessages(log.getRunMessages() + " \n\n " + message);
    }

    protected final void logException(Exception e) {
        String exceptionId = tfLogWriterUtility.saveLog("", e).getExceptionId();
        log.setExceptionKey(exceptionId);
    }

    protected boolean canRun() {
        String clusterIndexProp = System.getProperty("com.tofas.clusterIndex");
        return clusterIndexProp != null && clusterIndexProp.equals("1");
    }


}

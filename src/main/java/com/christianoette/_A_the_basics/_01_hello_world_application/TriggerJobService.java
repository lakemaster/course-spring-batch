package com.christianoette._A_the_basics._01_hello_world_application;

import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.stereotype.Component;

@Component
public class TriggerJobService {

    private final JobLauncher jobLauncher;
    private final Job job;

    public TriggerJobService(JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }

    public void runJob() throws JobParametersInvalidException,
            JobExecutionAlreadyRunningException,
            JobRestartException,
            JobInstanceAlreadyCompleteException {

        JobParameters jobParameters = new JobParametersBuilder().addParameter("outputText", new JobParameter("My first spring boot app"))
                .toJobParameters();

        jobLauncher.run(job, jobParameters);

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // cannot run one job with the same parameters twice
        jobParameters = new JobParametersBuilder().addParameter("outputText", new JobParameter("Second run"))
                .toJobParameters();

        jobLauncher.run(job, jobParameters);
    }

}

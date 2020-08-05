package com.bazl.lims.transfer.task;


import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * Created by lizhihua on 2019-06-18.
 */
@Configuration
@ConfigurationProperties(prefix="transfer", ignoreUnknownFields = true)
public class RetrieveAndUpdateNationPersonSampleNoTaskQuartz {


    @Autowired
    TransferNationParamsConfig transferNationParamsConfig;


    // JobDetail
    @Bean
    public JobDetail retrieveAndUpdateNationPersonSampleNoSyncJobDetail() {
        return JobBuilder.newJob(RetrieveAndUpdateNationPersonSampleNoTask.class).withIdentity("retrieveAndUpdateNationPersonSampleNoTask")
                .storeDurably().build();
    }

    // Trigger
    @Bean
    public Trigger retrieveAndUpdateNationPersonSampleNoTrigger() {
        SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(transferNationParamsConfig.getRetrievePersonSampleNoSeconds()).repeatForever();

        return TriggerBuilder.newTrigger().forJob(retrieveAndUpdateNationPersonSampleNoSyncJobDetail())
                .withIdentity("retrieveAndUpdateNationPersonSampleNoTrigger").withSchedule(schedBuilder).build();
    }


}

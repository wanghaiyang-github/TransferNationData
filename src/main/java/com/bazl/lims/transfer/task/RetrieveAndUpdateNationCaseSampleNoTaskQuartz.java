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
public class RetrieveAndUpdateNationCaseSampleNoTaskQuartz {


    @Autowired
    TransferNationParamsConfig transferNationParamsConfig;


    // JobDetail
    @Bean
    public JobDetail retrieveAndUpdateNationCaseSampleNoSyncJobDetail() {
        return JobBuilder.newJob(RetrieveAndUpdateNationCaseSampleNoTask.class).withIdentity("retrieveAndUpdateNationCaseSampleNoTask")
                .storeDurably().build();
    }

    // Trigger
    @Bean
    public Trigger retrieveAndUpdateNationCaseSampleNoTrigger() {
        SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(transferNationParamsConfig.getRetrieveCaseSampleNoSeconds()).repeatForever();

        return TriggerBuilder.newTrigger().forJob(retrieveAndUpdateNationCaseSampleNoSyncJobDetail())
                .withIdentity("retrieveAndUpdateNationCaseSampleNoTrigger").withSchedule(schedBuilder).build();
    }


}

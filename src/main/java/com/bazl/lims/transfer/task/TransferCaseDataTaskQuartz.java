package com.bazl.lims.transfer.task;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * Created by lizhihua on 2019-05-08.
 */
@Configuration
@ConfigurationProperties(prefix="transfer", ignoreUnknownFields = true)
public class TransferCaseDataTaskQuartz {

    @Autowired
    TransferNationParamsConfig transferNationParamsConfig;


    // JobDetail
    @Bean
    public JobDetail transferCaseDataSyncJobDetail() {
        return JobBuilder.newJob(TransferCaseDataTask.class).withIdentity("transferCaseDataTask")
                .storeDurably().build();
    }

    // Trigger
    @Bean
    public Trigger transferCaseDataTrigger() {
        SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(transferNationParamsConfig.getTransferCaseIntervalSeconds()).repeatForever();

        return TriggerBuilder.newTrigger().forJob(transferCaseDataSyncJobDetail())
                .withIdentity("transferCaseDataTrigger").withSchedule(schedBuilder).build();
    }


}

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
public class TransferPersonDataTaskQuartz {

    @Autowired
    TransferNationParamsConfig transferNationParamsConfig;


    // JobDetail
    @Bean
    public JobDetail transferPersonDataSyncJobDetail() {
        return JobBuilder.newJob(TransferPersonDataTask.class).withIdentity("transferPersonDataTask")
                .storeDurably().build();
    }

    // Trigger
    @Bean
    public Trigger transferPersonDataTrigger() {
        SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(transferNationParamsConfig.getTransferPersonIntervalSeconds()).repeatForever();

        return TriggerBuilder.newTrigger().forJob(transferPersonDataSyncJobDetail())
                .withIdentity("transferPersonDataTrigger").withSchedule(schedBuilder).build();
    }


}

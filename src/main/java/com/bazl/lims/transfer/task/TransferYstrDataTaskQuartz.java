package com.bazl.lims.transfer.task;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * Created by wanghaiyang on 2019-06-23.
 */
@Configuration
@ConfigurationProperties(prefix="transfer", ignoreUnknownFields = true)
public class TransferYstrDataTaskQuartz {

    @Autowired
    TransferNationParamsConfig transferNationParamsConfig;


    // JobDetail
    @Bean
    public JobDetail transferYstrDataSyncJobDetail() {
        return JobBuilder.newJob(TransferYstrDataTask.class).withIdentity("TransferYstrDataTask")
                .storeDurably().build();
    }

    // Trigger
    @Bean
    public Trigger transferYstrDataTrigger() {
        SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(transferNationParamsConfig.getTransferYstrIntervalSeconds()).repeatForever();

        return TriggerBuilder.newTrigger().forJob(transferYstrDataSyncJobDetail())
                .withIdentity("transferYstrDataTrigger").withSchedule(schedBuilder).build();
    }


}

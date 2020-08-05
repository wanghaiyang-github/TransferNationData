package com.bazl.lims.transfer.task;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author
 * Created by wanghaiyang on 2019-07-10.
 */
@Configuration
@ConfigurationProperties(prefix="transfer", ignoreUnknownFields = true)
public class TransferNotInstreSampleDataTaskQuartz {

    @Autowired
    TransferNationParamsConfig transferNationParamsConfig;


    // JobDetail
    @Bean
    public JobDetail transferNotInstoreSampleDataSyncJobDetail() {
        return JobBuilder.newJob(TransferNotInstoreSampleDataTask.class).withIdentity("TransferNotInstoreSampleDataTask")
                .storeDurably().build();
    }

    // Trigger
    @Bean
    public Trigger transferNotInstoreSampleDataTrigger() {
        SimpleScheduleBuilder schedBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(transferNationParamsConfig.getTransferNotInstoreIntervalSeconds()).repeatForever();

        return TriggerBuilder.newTrigger().forJob(transferNotInstoreSampleDataSyncJobDetail())
                .withIdentity("transferNotInstoreSampleDataTrigger").withSchedule(schedBuilder).build();
    }


}

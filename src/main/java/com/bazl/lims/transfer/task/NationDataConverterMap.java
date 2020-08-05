package com.bazl.lims.transfer.task;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author lizhihua on 2020-05-13.
 */
@Configuration
@ConfigurationProperties(prefix="convertmaps", ignoreUnknownFields = true)
public class NationDataConverterMap {

    /**
     * 老版本lims案件性质
     */
    private Map<String, String> oldLimsCasePropertyMap;


    /**
     * 新版本lims案件性质
     */
    private Map<String, String> newLimsCasePropertyMap;

    public Map<String, String> getOldLimsCasePropertyMap() {
        return oldLimsCasePropertyMap;
    }

    public void setOldLimsCasePropertyMap(Map<String, String> oldLimsCasePropertyMap) {
        this.oldLimsCasePropertyMap = oldLimsCasePropertyMap;
    }

    public Map<String, String> getNewLimsCasePropertyMap() {
        return newLimsCasePropertyMap;
    }

    public void setNewLimsCasePropertyMap(Map<String, String> newLimsCasePropertyMap) {
        this.newLimsCasePropertyMap = newLimsCasePropertyMap;
    }
}

package com.bazl.lims.transfer.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanghaiyang
 * @date 2019/7/1.
 */
public class TransferSpecialMarkerUtil {


    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(TransferSpecialMarkerUtil.class);

    public static String transgerSpecialMarker(String marker){

        if (StringUtils.isNotBlank(marker)) {
            if ("Amel".equalsIgnoreCase(marker)) {
                marker = "AMELOGENIN";
            }else if ("THO1".equalsIgnoreCase(marker)) {
                marker = "TH01";
            }else if ("Penta D".equalsIgnoreCase(marker)) {
                marker = "PentaD";
            }else if ("Penta E".equalsIgnoreCase(marker)) {
                marker = "PentaE";
            }
        }

        return marker;
    }
    /**
     * 解析基因型信息
     *
     * @param geneInfo
     * @return
     */
    public static Map<String, Object> analysisGene(String geneInfo) {
        Map<String, Object> map = null;
        if (StringUtils.isBlank(geneInfo)) {
            return null;
        }

        Map<String, List<String>> result = null;
        try {
            result = (Map) JSON.parse(geneInfo);
        } catch (Exception ex) {
            logger.error("解析基因分型信息错误!", ex);
            return null;
        }
        map = new LinkedHashMap<>();
        for (Map.Entry<String, List<String>> srcEntry : result.entrySet()) {
            map.put(transgerSpecialMarker(srcEntry.getKey()), getAllele(srcEntry.getValue()));
        }

        return map;
    }

    /**
     * 获取基因位点
     *
     * @param strList
     * @return
     */
    public static String getAllele(List<String> strList) {
        String allele = "";

        if (ListUtils.isNotEmptyList(strList)) {
            for (String strGene : strList) {
                allele += strGene + "/";
            }
        }

        if(allele.endsWith("/")){
            allele = allele.substring(0, allele.length()-1);
        }

        return allele;
    }

}

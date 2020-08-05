package com.bazl.lims.transfer.utils;

import java.util.List;

/**
 * Created by lizhihua on 2019-05-09.
 */
public class ListUtils {

    public static boolean isEmptyList(List<? extends Object> list){
        return list == null || list.size() == 0;
    }

    public static boolean isNotEmptyList(List<? extends Object> list){
        return list != null && list.size() > 0;
    }

}

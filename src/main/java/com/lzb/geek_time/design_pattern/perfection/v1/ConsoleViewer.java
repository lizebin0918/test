package com.lzb.geek_time.design_pattern.perfection.v1;

import com.google.gson.Gson;
import com.lzb.geek_time.design_pattern.perfection.v2.RequestStat;

import java.util.Map;

/**
 * Created by WenTaoKing on 2020/2/7
 * description:
 */
public class ConsoleViewer implements StatViewer {

    @Override
    public void output(Map<String, RequestStat> requestStats, long startTimeInMillis, long endTimeInMills) {
        System.out.println("Time Span: [" + startTimeInMillis + ", " + endTimeInMills + "]");
        Gson gson = new Gson();
        System.out.println(gson.toJson(requestStats));
    }
}

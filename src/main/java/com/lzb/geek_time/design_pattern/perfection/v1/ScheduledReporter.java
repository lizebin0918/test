package com.lzb.geek_time.design_pattern.perfection.v1;


import com.lzb.geek_time.design_pattern.perfection.v2.MetricsStorage;
import com.lzb.geek_time.design_pattern.perfection.v2.RequestInfo;
import com.lzb.geek_time.design_pattern.perfection.v2.RequestStat;

import java.util.List;
import java.util.Map;

/**
 * Created by WenTaoKing on 2020/2/11
 * description: 定时report基类
 */
public abstract class ScheduledReporter {
    protected MetricsStorage metricsStorage;
    protected Aggregator aggregator;
    protected StatViewer viewer;

    public ScheduledReporter(MetricsStorage metricsStorage, Aggregator aggregator, StatViewer viewer) {
        this.metricsStorage = metricsStorage;
        this.aggregator = aggregator;
        this.viewer = viewer;
    }

    protected void doStateAndReporter(long startTimeInMillis, long endTimeInMillis){
        long durationInMillis = endTimeInMillis - startTimeInMillis;
        Map<String, List<RequestInfo>> requestInfo = metricsStorage.
                getAllRequestInfoByDuration(startTimeInMillis,endTimeInMillis);
        Map<String, RequestStat> requestStats = aggregator.aggregate(requestInfo,durationInMillis);
        viewer.output(requestStats,startTimeInMillis,endTimeInMillis);
    }
}

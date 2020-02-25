package com.yukens.datasource.thread;

import java.util.concurrent.*;

import static com.yukens.datasource.constant.Constant.MONITOR_WORK_CORE_SIZE;

/**
 * 调度该线程池，主要异步处理一些额外的工作
 */
public class ThunderThreadPool extends ScheduledThreadPoolExecutor {


    public ThunderThreadPool(int corePoolSize) {
        super(corePoolSize);
    }

    public static ThunderThreadPool create() {
       return new ThunderThreadPool(MONITOR_WORK_CORE_SIZE);
    }

}

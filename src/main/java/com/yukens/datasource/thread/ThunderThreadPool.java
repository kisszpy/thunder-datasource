package com.yukens.datasource.thread;

import java.util.concurrent.*;

public class ThunderThreadPool extends ScheduledThreadPoolExecutor {


    public ThunderThreadPool(int corePoolSize) {
        super(corePoolSize);
    }

    public static ThunderThreadPool create() {
       return new ThunderThreadPool(5);
    }

}

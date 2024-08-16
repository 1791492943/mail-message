package com.message.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class TaskUtils {

    public static final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    public static final ExecutorService executorService = Executors.newFixedThreadPool(1);

}

package com.lzb.util;

import java.util.Objects;

import lombok.NonNull;

/**
 * <br/>
 * Created on : 2023-05-08 10:35
 * @author lizebin
 */
public class PriorityRunnable implements Comparable<PriorityRunnable> {

    /**
     * 优先级
     */
    private final int priority;

    /**
     * 任务
     */
    private final Runnable runnable;

    private PriorityRunnable(Runnable runnable, int priority) {
        this.priority = priority;
        this.runnable = runnable;
    }

    public static PriorityRunnable create(Runnable runnable) {
        return new PriorityRunnable(runnable, 0);
    }

    public static PriorityRunnable create(Runnable runnable, int priority) {
        return new PriorityRunnable(runnable, priority);
    }

    public void run() {
        runnable.run();
    }

    /**
     * 从大到小
     * @param o the object to be compared.
     * @return
     */
    @Override
    public int compareTo(@NonNull PriorityRunnable o) {
        return -(this.priority - o.priority);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PriorityRunnable)) return false;
        PriorityRunnable that = (PriorityRunnable) o;
        return priority == that.priority && runnable.equals(that.runnable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(priority, runnable);
    }
}
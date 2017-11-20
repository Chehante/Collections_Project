package com.itmo.collections.ServerClient.simple;

import java.io.*;
import java.util.Date;

/**
 * Created by xmitya on 27.11.16.
 */
public class LaunchStat implements Serializable {
    private int launchCount;

    private Date lastLaunch;

    public void update() {
        launchCount++;

        lastLaunch = new Date();
    }

    public boolean isFirstLaunch() {
        return launchCount == 0 && lastLaunch == null;
    }

    @Override
    public String toString() {
        return "LaunchStat{" +
                "launchCount=" + launchCount +
                ", lastLaunch=" + lastLaunch +
                '}';
    }
}

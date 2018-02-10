/*
 * FileName:	PhoneUtil.java
 * Copyright:	炫彩互动网络科技有限公司
 * Author: 		Kyson
 * Description:	<文件描述>
 * History:		2014-8-18 1.00 初始版本
 */
package com.android.lab.baloonperformer.memoryCleanUtils;

import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.os.Build;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * @author Kyson
 */
public class PhoneMemoryUtil {
    /**
     * Free phone memory，Clean up the cache ,<Function brief description>
     *
     * @param context
     */
    public static void releaseMemory(Context context) {
        ActivityManager activityManger = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> list = activityManger
                .getRunningAppProcesses();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                ActivityManager.RunningAppProcessInfo apinfo = list.get(i);
                String[] pkgList = apinfo.pkgList;
                if (apinfo.importance > ActivityManager.RunningAppProcessInfo.IMPORTANCE_SERVICE
                        && !apinfo.processName.equals("com.tt.realeasememory")) {
                    for (int j = 0; j < pkgList.length; j++) {
                        if (Build.VERSION.SDK_INT >= 8) {
                            activityManger.killBackgroundProcesses(pkgList[j]);
                        } else {
                            activityManger.restartPackage(pkgList[j]);
                        }
                    }
                }
            }
        }
    }

    /**
     * Get the percentage of used memory <Feature Description>
     *
     * @return
     */
    public static int getMemoryRatio(Context context) {
        long availMemory = getAvailMemory(context);
        long totalMemory = getTotalMemory(context);
        int momeryProgress = (int) ((totalMemory - availMemory) * 100 / totalMemory);
        if (momeryProgress < 0) {
            momeryProgress = 0;
        } else if (momeryProgress > 100) {
            momeryProgress = 100;
        }
        return momeryProgress;
    }

    /**
     * Get the current available memory size
     *
     * @param context
     * @return
     */
    public static long getAvailMemory(Context context) {// Get the current available memory size of android
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        MemoryInfo mi = new MemoryInfo();
        am.getMemoryInfo(mi);
        return mi.availMem; //Will get the size of memory normalization
    }

    /**
     * Get the phone system memory size
     *
     * @param context
     * @return
     */
    public static long getTotalMemory(Context context) {
        String str1 = "/proc/meminfo";//System memory information file
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(
                    localFileReader, 8192);
            str2 = localBufferedReader.readLine();//Read meminfo first line, the total system memory size
            arrayOfString = str2.split("\\s+");
            initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;//Get the total system memory, the unit is KB, multiplied by 1024 converted to Byte
            localBufferedReader.close();
        } catch (IOException e) {
        }
        return initial_memory;// Byte Converted to KB or MB, memory size normalized
    }

}

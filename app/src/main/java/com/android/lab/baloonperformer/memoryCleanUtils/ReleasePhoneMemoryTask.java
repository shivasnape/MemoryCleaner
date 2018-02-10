/*
 * FileName:	ReleasePhoneMemoryTask.java
 * Copyright:	炫彩互动网络科技有限公司
 * Author: 		Kyson
 * Description:	<文件描述>
 * History:		2014-8-18 1.00 初始版本
 */
package com.android.lab.baloonperformer.memoryCleanUtils;


import android.content.Context;
import android.os.AsyncTask;

/**
 * 释放手机内存<Br>
 *
 * @author Kyson
 */
public abstract class ReleasePhoneMemoryTask extends
        AsyncTask<Void, Void, Long> {
    private Context mContext;

    public ReleasePhoneMemoryTask(Context context) {
        this.mContext = context;
    }

    /**
     *
     Return the memory has been freed
     */
    @Override
    protected Long doInBackground(Void... params) {
        // Clean up before memory
        long beforeMemory = PhoneMemoryUtil.getAvailMemory(mContext);
        PhoneMemoryUtil.releaseMemory(mContext);
        // After cleaning memory
        long afterMemory = PhoneMemoryUtil.getAvailMemory(mContext);
        long relaseMemory = afterMemory - beforeMemory;
        if (relaseMemory < 0) {
            relaseMemory = 0;
        }
        return relaseMemory;
    }

    @Override
    protected abstract void onPostExecute(Long result);

}

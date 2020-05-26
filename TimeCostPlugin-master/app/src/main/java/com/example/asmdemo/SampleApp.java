package com.example.asmdemo;

import android.app.Application;
import android.util.Log;

/**
 * @author SteelCabbage
 * @date 2018/11/05
 */

public class SampleApp extends Application {

    private static final String TAG = SampleApp.class.getSimpleName();

    private static SampleApp sSampleApp;

    @Override
    public void onCreate() {
        super.onCreate();
        sSampleApp = this;




        // 如果必要，可以关闭SDK数据上报
//        ApexAnalytics.getInstance().setShouldReport(false);
    }


// 测试环境

    public static SampleApp getInstance() {
        return sSampleApp;
    }

}
package com.example.mytestmd;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.HashMap;

/**
 * Created by zjy on 2019-05-05
 */
public class TimeManager {
    private static HashMap<String, Long> startTimeMap = new HashMap<>();
    private static HashMap<String, Long> endTimeMap = new HashMap<>();

    public static void addStartTime(String key, long time) {
        startTimeMap.put(key, time);
    }

    public static void addEndTime(String key, long time) {
        endTimeMap.put(key, time);
    }

    public static void calcuteTime(String key) {
        long startTime = startTimeMap.get(key);
        long endTime = endTimeMap.get(key);
        System.out.println(key + "======time:" + (endTime - startTime));
    }
    public static String sfafda(View view) {
        if (null == view) {
            System.out.println("onClick() -> view is null!");

//            return false;
        }
        System.out.println("onClick() -> view !");
        return "x";
    }
    public static void onClick1(View view) {
        if (null == view) {
            System.out.println("onClick() -> view is null!");

//            return false;
        }
        System.out.println("onClick() -> view !");
//        return false;
    }
    public static boolean onClick2(View view) {
        if (null == view) {
            System.out.println("onClick() -> view is null!");

            return false;
        }
        System.out.println("onClick() -> view !");
        return false;
    }
    public static void testbbtc(String key ) {

        System.out.println(key + "11111111111111======time:");
    }

    public static boolean abcd() {

        System.out.println("22222222222======time:");
        return false;
    }

//    onPageFinished(Landroid/webkit/WebView;Ljava/lang/String;)V

    public static boolean onPageFinished(WebView view, String url) {
        if (null == view) {
            System.out.println("onPageFinished() -> view is null!");
            return false;
        }
       final WebView webView = view;
//        webView.addJavascriptInterface(new JsInterface12(), "android");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//安卓4.4之后使用api 19
            webView.postDelayed((new Runnable() {
//                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    webView.evaluateJavascript("javascript:mycallJS()", new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            //将button显示的文字改成JS返回的字符串
                            System.out.println("evaluateJavascript");

                        }
                    });
                }
            }), 2000);
        }else{
            webView.postDelayed(new Runnable() {
                @Override
                public void run() {

                    // 注意调用的JS方法名要对应上
                    // 调用javascript的callJS()方法
                    webView.loadUrl("javascript:mycallJS()");
                }
            },2000);
        }

        return false;

    }
    public static class JsInterface12 {
        public JsInterface12() {
        }
        //在js中调用window.test.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void myshowInfoFromJs(String name) {
            System.out.println("dsfsdsdd");
//            Toast.makeText(mContext, name+"myshowInfoFromJs", Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public  void mygetInfoFromJs(int a, int b){
            int c = a+b;
            System.out.println("dsfsdsdd");

//            Toast.makeText(mContext, ""+c+"/myshowInfoFromJs", Toast.LENGTH_SHORT).show();
        }
    }

//    public static void onPageFinished() {
//
//        System.out.println("onPageFinished() -> view !");
//
//
//    }
}

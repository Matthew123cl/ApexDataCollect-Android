package com.example.asmdemo;

import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;


public class MyWebChromeClient extends WebChromeClient {

//
//    @Override
//    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
////            if (!ActivityUtils.activityIsAlive(mContext)) {//页面关闭后，直接返回
////                try {
////                    result.cancel();
////                } catch (Exception ignored) {
////                }
////                return true;
////            }
////            if (mJsApi != null && mJsApi.hijackJsPrompt(message)) {
////                result.confirm();
////                return true;
////            }
//
//            return super.onJsPrompt(view, url, message, defaultValue, result);
//    }
////    public boolean hijackJsPrompt(String message) {
////        if (TextUtils.isEmpty(message)) {
////            return false;
////        }
////
////        boolean handle = message.startsWith(YIXIN_JSBRIDGE);
////
////        if (handle) {
////            call(message);
////        }
////
////        return handle;
////    }

}

package com.example.asmdemo;

import android.graphics.Bitmap;
import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


public class MyWebViewClient extends WebViewClient {
//    @Override
//    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//        return super.shouldOverrideUrlLoading(view, request);
//    }

    @Override
    public void onPageFinished(WebView view, String url) {
//        String js = "var newscript = document.createElement(\"script\");";
//        js += "newscript.src=\"file:///android_asset/jsStr.js\";";
//        js += "document.body.appendChild(newscript);";
//        view.loadUrl("javascript:" + js);
        super.onPageFinished(view, url);


    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
//        String jsStr = "";
//        try {
//            InputStream in = SampleApp.getInstance().getAssets().open("jsStr.js");
//            byte buff[] = new byte[1024];
//            ByteArrayOutputStream fromFile = new ByteArrayOutputStream();
//            do {
//                int numRead = in.read(buff);
//                if (numRead <= 0) {
//                    break;
//                }
//                fromFile.write(buff, 0, numRead);
//            } while (true);
//            jsStr = fromFile.toString();
//            in.close();
//            fromFile.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//
//        }
////                String js = "var newscript = document.createElement(\"script\");";
////        js += "newscript.src=\"file:///android_asset/jsStr.js\";";
////        js += "document.body.appendChild(newscript);";
////        view.loadUrl("javascript:" + js);
//        if (Build.VERSION.SDK_INT >= 19) {
//            view.evaluateJavascript(jsStr, new ValueCallback<String>() {
//                @Override
//                public void onReceiveValue(String value) {//js与native交互的回调函数
//                }
//            });
//        }
        super.onPageStarted(view, url, favicon);
    }
}

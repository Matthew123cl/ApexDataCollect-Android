package com.example.asmdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class TestWebView extends WebView {
    public TestWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestWebView(Context context) {
        super(context);

    }


    public  TestWebView setviewweb(Context context){
        //设置WebView支持JavaScript
        this.getSettings().setJavaScriptEnabled(true);
        this.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        this.getSettings().setLoadWithOverviewMode(true);




        String url = "file:///android_asset/testwebview12.html";
//        String url = "http://127.0.0.1:8020/test/index.html";

        this.loadUrl(url);
        this.setWebViewClient(new MyWebViewClient());
//                this.setWebViewClient(new WebViewClient());




//在js中调用本地java方法
        this.addJavascriptInterface(new JsInterface(context), "android");
        this.loadUrl(url);

        return  this;
    }

    public class JsInterface {
        private Context mContext;
        public JsInterface(Context context) {
            this.mContext = context;
        }
        //在js中调用window.test.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void showInfoFromJs(String name) {
            Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public  void getInfoFromJs(int a, int b){
            int c = a+b;
            Toast.makeText(mContext, ""+c, Toast.LENGTH_SHORT).show();
        }
    }

}

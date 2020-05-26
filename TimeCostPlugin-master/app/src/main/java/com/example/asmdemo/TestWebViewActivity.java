package com.example.asmdemo;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asmdemo.web.RmWebview;
import com.example.asmdemo.web.WebTools;
import com.example.mytestmd.TimeManager;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class TestWebViewActivity extends AppCompatActivity {
    WebView webView;
    TextView textView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testwebview);
        textView =  (TextView)findViewById(R.id.textviewid);
        webView = (WebView)findViewById(R.id.webid);
        String url = "file:///android_asset/testwebview.html";
        webView.loadUrl(url);
  //设置可以访问文件

        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.addJavascriptInterface(new TimeManager.JsInterface12(), "androidxx");



    }

    @Override
    protected void onStart() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                View contentView = getWindow().getDecorView().findViewById(android.R.id.content);
//                ArrayList<WebView> childViewIndexesWebView = findrootViewbyWebview(contentView);
//                childViewIndexesWebView.get(0).setWebViewClient(new RmWebview(childViewIndexesWebView.get(0).getWebViewClient()));
//        webViewInjectionJs(childViewIndexesWebView);
//                new WebTools().webViewInjectionJs(childViewIndexesWebView);
            }
        });


        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }
    public void webViewInjectionJs(ArrayList<WebView> childViewIndexesWebView){

        for (final WebView webView : childViewIndexesWebView){
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                return;
            }
            if(webView!=null) {
                WebViewClient webViewClient= webView.getWebViewClient();
                webView.getSettings().setJavaScriptEnabled(true);
                webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                webView.getSettings().setLoadWithOverviewMode(true);
                webView.addJavascriptInterface(new JsInterface12(TestWebViewActivity.this), "android");

                //如果有子类
                if(isHaveChildWebViewClient(webViewClient,"android.webkit.WebViewClient")&&isHaveMethod(webViewClient,"onPageFinished","android.webkit.WebViewClient")){
                    System.out.println("xxxxxxxxxxxxxxxxxxx");
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//安卓4.4之后使用api 19
                        webView.postDelayed((new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                            @Override
                            public void run() {
                                webView.evaluateJavascript("javascript:mycallJS(123)", new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String value) {
                                        //将button显示的文字改成JS返回的字符串
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
                                webView.loadUrl("javascript:mycallJS(123345)");
                            }
                        },2000);
                    }
                }

            }
        }
    }
    public boolean isHaveChildWebViewClient(Object webViewClient,String superClassPath){
        String s=webViewClient.getClass().getName();
        if(webViewClient==null)
            return false;
        if(webViewClient.getClass().getName().equals(superClassPath))
            return false;

        return true;
    }

    public boolean isHaveMethod(Object webView,String methodName,String superClassPath){
        if(webView==null)
            return false;
        try {
            Method method = webView.getClass().getMethod(methodName,WebView.class,String.class);
            if(method.getDeclaringClass().getName().equals(superClassPath))
                return false;
            else
                return true;

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            return false;
        }
    }


    public class JsInterface12 {
        private Context mContext;
        public JsInterface12(Context context) {
            this.mContext = context;
        }
        //在js中调用window.test.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void myshowInfoFromJs(String name) {
            Toast.makeText(mContext, name+"myshowInfoFromJs", Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public  void mygetInfoFromJs(int a, int b){
            int c = a+b;
            Toast.makeText(mContext, ""+c+"/myshowInfoFromJs", Toast.LENGTH_SHORT).show();
        }
    }

    public static ArrayList<WebView> findrootViewbyWebview(View contentView) {
        if (null == contentView) {
            return null;
        }

        View parentView = contentView;

        if (!(parentView instanceof ViewGroup)) {
            return null;
        }
        ArrayList<ViewGroup> olderchildViewGroup = new ArrayList<>();
        olderchildViewGroup.add((ViewGroup)parentView);


        ArrayList<WebView> childViewIndexesWebView= new ArrayList<>();
        findrootviewbyWebview(childViewIndexesWebView,null,olderchildViewGroup);

        return childViewIndexesWebView;


    }

    private static ArrayList<WebView>  findrootviewbyWebview(ArrayList<WebView> childViewIndexesWebView,ArrayList<ViewGroup> childViewGroup,ArrayList<ViewGroup> olderchildViewGroup) {

        for (int j = 0; j < olderchildViewGroup.size(); j++) {
            int childCount = olderchildViewGroup.get(j).getChildCount();
            View  parentView = olderchildViewGroup.get(j);
//            int childCount = ((ViewGroup) parentView).getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = ((ViewGroup) parentView).getChildAt(i);
                if (null == childAt) {
                    return null;
                }

                if ((childAt instanceof WebView)) {
                    if(null == childViewIndexesWebView)
                        childViewIndexesWebView = new ArrayList<>();
                    childViewIndexesWebView.add((WebView) childAt);
                }
                //新的添加数据
                if ((childAt instanceof ViewGroup)) {
                    if(childViewGroup==null)
                        childViewGroup = new ArrayList<>();
                    childViewGroup.add((ViewGroup)childAt);
                }
            }

        }
        if(childViewGroup==null||childViewGroup.size()==0){
            if(null!=olderchildViewGroup)
                olderchildViewGroup.clear();
            olderchildViewGroup = null;
            childViewGroup = null;
            return  childViewIndexesWebView;
        }else {
            olderchildViewGroup.clear();
            findrootviewbyWebview(childViewIndexesWebView,olderchildViewGroup,childViewGroup);
        }
        return childViewIndexesWebView;
    }


    public  void tests(View view){
        webView.post((new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                webView.evaluateJavascript("javascript:callJS()",new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
//                //将button显示的文字改成JS返回的字符串
                        textView.setText(value);
                    }
                });
            }
        }));
    }
//    @Override
//    protected void onResume() {
//
//
////        testWebView.evaluateJavascript(new ValueCallback<String>() {
////            @Override
////            public void onReceiveValue(String s) {
////                //将button显示的文字改成JS返回的字符串
////                buttonLeft.setText(s);
////            }
////        }, "javascript:callJS()");
////
////    }
//
//                super.onResume();
//
//    }
}

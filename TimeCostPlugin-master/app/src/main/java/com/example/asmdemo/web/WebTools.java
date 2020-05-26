package com.example.asmdemo.web;


import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.asmdemo.MyWebViewClient;

import java.lang.reflect.Method;
import java.util.ArrayList;

public class WebTools {
    private boolean isloader =false;
    private int count = 0;

    public WebTools(){

    }

    public boolean isJsBindFinish(){
        if(isloader&&count<3)
            return  false;
        else
            return true ;
    }

    public void webViewInjectionJs(ArrayList<WebView> childViewIndexesWebView){

        for (final WebView webView : childViewIndexesWebView){
            if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                return;
            }
            if(webView!=null) {
                if(!webView.getSettings().getJavaScriptEnabled()){
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
                    webView.getSettings().setLoadWithOverviewMode(true);
                }
                WebViewClient webViewClient= webView.getWebViewClient();
                webView.addJavascriptInterface(new JsInterface12(), "android");
                jsBindFinish(webView,webViewClient);


            }
        }

    }
    public void jsBindFinish(final WebView webView ,WebViewClient webViewClient){
        //如果有子类
//        if(isHaveChildWebViewClient(webViewClient,"android.webkit.WebViewClient")&&isHaveMethod(webViewClient,"onPageFinished","android.webkit.WebViewClient")){
        if(isHaveChildWebViewClient(webViewClient,"android.webkit.WebViewClient")&&isHaveMethod(webViewClient,
                "onPageFinished","android.webkit.WebViewClient")){
            System.out.println("？");
            webView.setWebViewClient(new RmWebview(webViewClient));

        }else  if(isHaveChildWebViewClient(webViewClient,"android.webkit.WebViewClient")&&!isHaveMethod(webViewClient,
                "onPageFinished","android.webkit.WebViewClient")){
            webView.setWebViewClient(new RmWebview(webViewClient));
            System.out.println("？");

        }
        else{


//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//安卓4.4之后使用api 19
//                webView.post((new Runnable() {
//                    @Override
//                    public void run() {
//                        webView.evaluateJavascript("javascript:mycallJS(123)", new ValueCallback<String>() {
//                            @Override
//                            public void onReceiveValue(String value) {
//                                int i;
//                                System.out.println("xxxxxxxxxxxxxxxxxxx");
//
//                                //将button显示的文字改成JS返回的字符串
//                            }
//                        });
//                    }
//                }));
//            }else{
//                webView.post(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        // 注意调用的JS方法名要对应上
//                        // 调用javascript的callJS()方法
//                        webView.loadUrl("javascript:mycallJS(123345)");
//                    }
//                });
//            }
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

        //在js中调用window.test.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void myshowInfoFromJs(String name) {
            isloader =true;
//            Toast.makeText(mContext, name+"myshowInfoFromJs", Toast.LENGTH_SHORT).show();
        }
        @JavascriptInterface
        public  void mygetInfoFromJs(int a, int b){
            isloader =true;
            int c = a+b;
//            Toast.makeText(mContext, ""+c+"/myshowInfoFromJs", Toast.LENGTH_SHORT).show();
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



}


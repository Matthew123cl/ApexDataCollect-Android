//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.example.asmdemo.web;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.SafeBrowsingResponse;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class RmWebview extends WebViewClient {
private WebViewClient webViewClient;
    public RmWebview(WebViewClient webViewClient) {
        this.webViewClient = webViewClient;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if(null!=webViewClient)
            webViewClient.shouldOverrideUrlLoading(view,url);
        return super.shouldOverrideUrlLoading(view, url);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if(null!=webViewClient)
            webViewClient.shouldOverrideUrlLoading(view,request);
        return super.shouldOverrideUrlLoading(view, request);
    }


    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        if(null!=webViewClient)
            webViewClient.onPageStarted(view,url,favicon);
        super.onPageStarted(view, url, favicon);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        if(null!=webViewClient)
            webViewClient.onPageFinished(view,url);
        super.onPageFinished(view, url);
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        if(null!=webViewClient)
            webViewClient.onLoadResource(view,url);
        super.onLoadResource(view, url);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onPageCommitVisible(WebView view, String url) {
        if(null!=webViewClient)
            webViewClient.onPageCommitVisible(view,url);
        super.onPageCommitVisible(view, url);
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        if(null!=webViewClient)
            webViewClient.shouldOverrideUrlLoading(view,url);
        return super.shouldInterceptRequest(view, url);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        if(null!=webViewClient)
            webViewClient.shouldOverrideUrlLoading(view,request);
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
        if(null!=webViewClient)
            webViewClient.onTooManyRedirects(view,cancelMsg,continueMsg);
        super.onTooManyRedirects(view, cancelMsg, continueMsg);
    }


    @Deprecated
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        if(null!=webViewClient)
            webViewClient.onReceivedError(view,errorCode,description,failingUrl);
        super.onReceivedError(view,errorCode,description,failingUrl);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        if(null!=webViewClient)
            webViewClient.onReceivedError(view,request,error);
        super.onReceivedError(view, request, error);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        if(null!=webViewClient)
            webViewClient.onReceivedHttpError(view,request,errorResponse);
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onFormResubmission(WebView view, Message dontResend, Message resend) {
        if(null!=webViewClient)
            webViewClient.onFormResubmission(view,dontResend,resend);
        super.onFormResubmission(view, dontResend, resend);
    }

    @Override
    public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
        if(null!=webViewClient)
            webViewClient.doUpdateVisitedHistory(view,url,isReload);
        super.doUpdateVisitedHistory(view, url, isReload);
    }

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        if(null!=webViewClient)
            webViewClient.onReceivedSslError(view,handler,error);
        super.onReceivedSslError(view, handler, error);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        if(null!=webViewClient)
            webViewClient.onReceivedClientCertRequest(view,request);
        super.onReceivedClientCertRequest(view, request);
    }

    @Override
    public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
        if(null!=webViewClient)
            webViewClient.shouldOverrideKeyEvent(view,event);
        return super.shouldOverrideKeyEvent(view, event);
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        if(null!=webViewClient)
            webViewClient.onUnhandledKeyEvent(view,event);
        super.onUnhandledKeyEvent(view, event);
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        if(null!=webViewClient)
            webViewClient.onScaleChanged(view,oldScale,newScale);
        super.onScaleChanged(view, oldScale, newScale);
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, @Nullable String account, String args) {
        if(null!=webViewClient)
            webViewClient.onReceivedLoginRequest(view,realm,account,account);
        super.onReceivedLoginRequest(view, realm, account, args);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onRenderProcessGone(WebView view, RenderProcessGoneDetail detail) {
        if(null!=webViewClient)
            webViewClient.onRenderProcessGone(view,detail);
        return super.onRenderProcessGone(view, detail);
    }

    @RequiresApi(api = Build.VERSION_CODES.O_MR1)
    @Override
    public void onSafeBrowsingHit(WebView view, WebResourceRequest request, int threatType, SafeBrowsingResponse callback) {
        if(null!=webViewClient)
            webViewClient.onSafeBrowsingHit(view,request,threatType,callback);
        super.onSafeBrowsingHit(view, request, threatType, callback);
    }

}

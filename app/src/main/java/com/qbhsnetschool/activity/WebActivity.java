package com.qbhsnetschool.activity;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qbhsnetschool.R;

public class WebActivity extends BaseActivity{

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        mWebView = (WebView) findViewById(R.id.webView);
        //mWebView.getSettings().setUserAgentString(mWebView.getSettings().getUserAgentString() + " " + HttpHelper.getUserAgent(appContext));
        mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.addJavascriptInterface(new appMethod(context), "appMethod");
        mWebView.setWebViewClient(new MyResourceClient());
        mWebView.setWebChromeClient(new MyUIClient());
//        mWebView.setInitialScale(50);
        mWebView.getSettings().setUseWideViewPort(true);
//        mWebView.getSettings().setLayoutAlgorithm(XWalkSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setDefaultTextEncodingName("UTF -8");//设置默认为utf-8
        mWebView.getSettings().setLoadWithOverviewMode(true);
        syncWebViewCookies();
        mWebView.loadUrl("http://192.168.0.127:8080/exam/m_exam?exam=24");
    }

    public void syncWebViewCookies() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(getApplicationContext());
        }
        CookieManager cookieManager = CookieManager.getInstance();
        if (cookieManager.hasCookies()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                cookieManager.removeSessionCookies(null);
            } else {
                cookieManager.removeSessionCookie();
            }
        }
        cookieManager.setAcceptCookie(true);
        cookieManager.setCookie("http://192.168.0.127:8080/exam/m_exam?exam=24", "csrftoken=r5L42rIenlwLdBq9FsU13bmcbNr6StnZLE9WX8j9qeMEzF6LGeoqripjVUXNoDps");
        cookieManager.setCookie("http://192.168.0.127:8080/exam/m_exam?exam=24", "sessionid=2ytvtmnoscshnb61g8jy2izlukvaf9ra");

//        if (webCookie != null && webCookie.size() > 0) {
//            for (int i = 0; i < AppContext.webCookie.size(); i++) {
//                for (String key : AppContext.webCookie.get(i).keySet()) {
//                    cookieManager.setCookie(UrlHelper.getHttpHeader(), AppContext.webCookie.get(i).get(key));
//                }
//            }
//            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
//                CookieSyncManager.getInstance().sync();
//            }
//        }
    }

    private class MyResourceClient extends WebViewClient {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, final WebResourceRequest request) {
            String scheme = request.getUrl().getScheme();
            if (scheme.equals("http") || scheme.equals("https")) {
                String url = request.getUrl().toString();
                return shouldOverrideUrlLoading(view, url);
            } else {
                return super.shouldOverrideUrlLoading(view, request);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            onReceivedError(view, error.getErrorCode(), error.getDescription().toString(), request.getUrl().toString());
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        }

        @Override
        public void onReceivedSslError(final WebView view, final SslErrorHandler handler, SslError error) {
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            System.out.println("----------------------" + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    }

    private class MyUIClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }

        @Override
        public void onProgressChanged(WebView view, int progressInPercent) {
            super.onProgressChanged(view, progressInPercent);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }
}

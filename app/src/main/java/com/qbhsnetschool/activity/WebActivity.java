package com.qbhsnetschool.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qbhsnetschool.R;
import com.qbhsnetschool.js.AppInterface;
import com.qbhsnetschool.protocol.UrlHelper;
import com.qbhsnetschool.uitls.StringUtils;

public class WebActivity extends BaseActivity{

    private WebView mWebView;
    private String url;
    private String title;
    private TextView page_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBaseContentView(R.layout.activity_web, false, R.color.status_bar_bg_color, false);
        initIntent();
        page_title = (TextView) findViewById(R.id.page_title);
        page_title.setText(title);
        LinearLayout page_back = (LinearLayout) findViewById(R.id.page_back);
        page_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mWebView = (WebView) findViewById(R.id.webView);
        //mWebView.getSettings().setUserAgentString(mWebView.getSettings().getUserAgentString() + " " + HttpHelper.getUserAgent(appContext));
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.addJavascriptInterface(new AppInterface(this), "jsObj");
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
        mWebView.loadUrl(url);
    }

    private void initIntent() {
        Intent intent = getIntent();
        url = intent.getStringExtra("url");
        title = intent.getStringExtra("title");
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
        cookieManager.setCookie(UrlHelper.BaseUrl.BASE_HTTP_URL, "app_client=android");
        //cookieManager.setCookie("http://www.hualuogengshuxue.com/courses/wating", "csrftoken=9B6BiaHXZQCspEumh4Pwbd1vyc9rF80i6zm6qIzywvCGgsyZZBUAH6kAylaSit31");
        //cookieManager.setCookie("http://www.hualuogengshuxue.com/courses/wating", "sessionid=dgqwccdidqc576c3jctxk31nthrsakm6");

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
            if (!StringUtils.isEmpty(title)){
                initTitle(title);
            }
        }
    }

    private void initTitle(String title) {
        page_title.setText(title);
    }
}

package org.eclipse.paho.android.service.sample;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by song on 2017. 9. 11..
 */

public class MissingSpot extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.missingspot);
        // localhost
        // String url = "http://10.0.2.2/";
        // my hotspot
        String url = "http://jhy753.dothome.co.kr";

        WebView webView = (WebView) findViewById(R.id.web1);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl(url);
    }
}

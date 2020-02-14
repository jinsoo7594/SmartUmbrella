package org.eclipse.paho.android.service.sample;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by Song on 2017. 6. 15..
 */
public class Navigation extends Activity {
    WebView webView;//웹뷰
    WebSettings mWebSettings;//웹뷰 셋팅

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("Navigation","onCreate");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation);

       // String url = "http://jhy753.dothome.co.kr/smartUmbrella/test13.html";
        String url = "http://jhy753.dothome.co.kr/smartUmbrella/test15.html";
        Log.d("Navigation","String url");

        //웹뷰 셋팅
        webView = (WebView) findViewById(R.id.web1); //레이어와 연결
        webView.setWebViewClient(new WebViewClient()); // 클릭시 새창 안뜨게.
        mWebSettings = webView.getSettings(); // 세부 세팅 등록
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setLoadWithOverviewMode(true);
        mWebSettings.setNeedInitialFocus(true);
        webView.loadUrl(url);

        Log.d("Navigation","onCreate End");

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("Navigation","onCreateOptionsMenu");
        //menu.add(0, 0, 0, "Connect");
        //menu.add(0, 1, 0, "Disconnect");
        menu.add(0, 0, 0, "Go Back");
        Log.d("Navigation","Go Back");

        return super.onCreateOptionsMenu(menu);
    }
    // 나중에 0번 버튼은 삭제

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {

            case 0: {
                finish();
                break;
            }
        }
        return super.onMenuItemSelected(featureId, item);
    }

    class Browser extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return super.shouldOverrideUrlLoading(view, url);
        }

    }

}

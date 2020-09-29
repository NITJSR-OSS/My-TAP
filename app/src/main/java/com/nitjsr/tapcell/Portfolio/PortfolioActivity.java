package com.nitjsr.tapcell.Portfolio;

import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.nitjsr.tapcell.R;

public class PortfolioActivity extends Activity {

    WebView webView;
    ProgressDialog progressDialog;
    TextView toolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portfolio);

        Toolbar toolbar = findViewById(R.id.toolbar);
        webView=(WebView)findViewById(R.id.webView);
        toolbarTitle=(TextView)findViewById(R.id.toolbar_title);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        webView.setWebViewClient(new MyWebViewClient());

        String url= "http://www.nitjsr.ac.in/tap/portfolio/";

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
//        webView.getSettings().setLoadWithOverviewMode(true);
        webView.loadUrl(url);
    }

    private class MyWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressDialog = new ProgressDialog(PortfolioActivity.this);
            progressDialog.setMessage("Please wait ...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(progressDialog!=null){
                progressDialog.dismiss();
            }
        }
    }
}
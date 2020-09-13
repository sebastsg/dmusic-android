package com.sgundersen.dmusic.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.sgundersen.dmusic.Cache;
import com.sgundersen.dmusic.JavaScriptInterface;
import com.sgundersen.dmusic.MainActivity;
import com.sgundersen.dmusic.R;

public class MainFragment extends Fragment {

    // todo: store in configuration file, or allow app user to edit the url.
    public static final String websiteUrl = "http://dmusic.sgundersen.com";

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initializeWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initializeWebView() {
        View view = getView();
        if (view != null) {
            WebView webView = getView().findViewById(R.id.webmain);
            WebSettings settings = webView.getSettings();
            if (settings != null) {
                settings.setJavaScriptEnabled(true);
            }
            MainActivity activity = (MainActivity) getActivity();
            Cache cache = new Cache(activity);
            webView.setWebViewClient(new MainWebViewClient(cache));
            webView.addJavascriptInterface(new JavaScriptInterface(cache), "android");
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
            webView.loadUrl(websiteUrl);
        }
    }

}

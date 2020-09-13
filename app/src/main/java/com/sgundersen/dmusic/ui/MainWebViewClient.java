package com.sgundersen.dmusic.ui;

import android.support.annotation.Nullable;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.sgundersen.dmusic.Cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class MainWebViewClient extends WebViewClient {

    private final Cache cache;

    public MainWebViewClient(Cache cache) {
        this.cache = cache;
    }

    @Nullable
    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        List<String> segments = request.getUrl().getPathSegments();
        if (!segments.isEmpty()) {
            String url = request.getUrl().toString().replace(MainFragment.websiteUrl, "");
            String localPath = cache.getFullLocalPathFromRequestPath(url);
            if (Cache.isCached(localPath)) {
                try {
                    // todo: eh, providing the correct mime type could be an idea. For the future, of course.
                    if (segments.get(0).equals("track")) {
                        return new WebResourceResponse("audio/mpeg", "UTF-8", new FileInputStream(new File(localPath)));
                    }
                    if (segments.get(0).equals("img")) {
                        return new WebResourceResponse("image/jpeg", "UTF-8", new FileInputStream(new File(localPath)));
                    }
                } catch (FileNotFoundException ignored) {
                }
            } else{
                cache.cacheFile(url);
            }
        }
        return super.shouldInterceptRequest(view, request);
    }

}

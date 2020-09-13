package com.sgundersen.dmusic;

import android.webkit.JavascriptInterface;

public class JavaScriptInterface {

    private final Cache cache;

    public JavaScriptInterface(Cache cache) {
        this.cache = cache;
    }

    @JavascriptInterface
    public boolean isCached(String url) {
        return Cache.isCached(cache.getFullLocalPathFromRequestPath(url));
    }

    @JavascriptInterface
    public void cacheFile(String url) {
        cache.cacheFile(url);
    }

}

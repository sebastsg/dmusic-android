package com.sgundersen.dmusic;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.webkit.CookieManager;

import com.sgundersen.dmusic.ui.MainFragment;

import java.io.File;

public class Cache {

    private final MainActivity activity;

    public Cache(MainActivity activity) {
        this.activity = activity;
    }

    public static String getUrlAsName(String url) {
        return url.replaceAll("/", "_");
    }

    public static boolean isCached(@Nullable String path) {
        if (path == null) {
            return false;
        }
        File file = new File(path);
        return file.exists();
    }

    @Nullable
    public String getFullLocalPathFromRequestPath(String path) {
        File directory = activity.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
        if (directory != null) {
            return directory.toString() + "/" + getUrlAsName(path);
        } else {
            return null;
        }
    }

    public void downloadFile(String externalUrl, String fileName) {
        DownloadManager downloadManager = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(MainFragment.websiteUrl + externalUrl));
        request.setVisibleInDownloadsUi(false);
        request.setDestinationInExternalFilesDir(activity, Environment.DIRECTORY_DOWNLOADS, fileName);
        String cookie = CookieManager.getInstance().getCookie(MainFragment.websiteUrl);
        request.addRequestHeader("Cookie", cookie);
        downloadManager.enqueue(request);
    }

    public void cacheFile(String url) {
        if (!isCached(getFullLocalPathFromRequestPath(url))) {
            downloadFile(url, getUrlAsName(url));
        }
    }

}

package com.swufe.bluebook.Backstage;

public interface DownloadListener extends Callback {
    void onSuccess(String savePath);

    void onProgress(int progress);
}

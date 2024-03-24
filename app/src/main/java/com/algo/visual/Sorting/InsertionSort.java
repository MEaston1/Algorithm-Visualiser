package com.algo.visual.Sorting;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.algo.visual.MainActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InsertionSort {

    public ExecutorService executorService = Executors.newSingleThreadExecutor();
    public Handler handler = new Handler(Looper.getMainLooper());
    private long lastUpdate = 0; // Track when the last UI update was posted
    private final long UPDATE_THRESHOLD = 100; // Minimum time (ms) between updates

    public void executeInsertionSort() {
        executorService.submit(() -> {
            int size = MainActivity.data.size();
            for(int firstIdx = 1; firstIdx < size; ++firstIdx){
                int key = MainActivity.data.get(firstIdx);
                int secondIdx = firstIdx -1;

                while(secondIdx >= 0 && MainActivity.data.get(secondIdx) > key){
                    MainActivity.data.set(secondIdx + 1, MainActivity.data.get(secondIdx));
                    secondIdx = secondIdx -1;

                    if (SystemClock.uptimeMillis() - lastUpdate > UPDATE_THRESHOLD) {
                        handler.post(() -> MainActivity.drawGraph(MainActivity.mImageView));    // (OnProgressUpdate replacement)
                        lastUpdate = SystemClock.uptimeMillis();
                    }
                    try {
                        Thread.sleep(MainActivity.speed / 10);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
                MainActivity.data.set(secondIdx + 1, key);
            }
            handler.post(() -> {
                // Post execution UI update (onPostExecute replacement)
                MainActivity.sorted = true;
                MainActivity.drawGraph(MainActivity.mImageView);
                MainActivity.mImageView.setClickable(true);
            });
        });
    }
}

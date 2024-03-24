package com.algo.visual.Sorting;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.algo.visual.MainActivity;

public class BubbleSort {

    private ExecutorService executorService = Executors.newSingleThreadExecutor();
    private Handler handler = new Handler(Looper.getMainLooper());
    private long lastUpdate = 0; // Track when the last UI update was posted
    private final long UPDATE_THRESHOLD = 100; // Minimum time (ms) between updates

    public void executeBubbleSort() {
        executorService.submit(() -> {

            int size = MainActivity.data.size();
            for (int firstIdx = 0; firstIdx < size - 1; firstIdx++) {
                for (int secondIdx = 0; secondIdx < size - firstIdx - 1; secondIdx++) {
                    if (MainActivity.data.get(secondIdx) > MainActivity.data.get(secondIdx + 1)) {
                        // performing the swap
                        int temp = MainActivity.data.get(secondIdx);
                        MainActivity.data.set(secondIdx, MainActivity.data.get(secondIdx + 1));
                        MainActivity.data.set(secondIdx + 1, temp);

                        if (SystemClock.uptimeMillis() - lastUpdate > UPDATE_THRESHOLD) {
                            handler.post(() -> MainActivity.drawGraph(MainActivity.mImageView));        // onProgressUpdate replacement
                            lastUpdate = SystemClock.uptimeMillis();
                        }
                        try {
                            Thread.sleep(MainActivity.speed / 10);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
            handler.post(() -> {                            // onPostExecute replacement
                MainActivity.sorted = true;
                MainActivity.drawGraph(MainActivity.mImageView);
                MainActivity.mImageView.setClickable(true);
            });
        });
    }
}
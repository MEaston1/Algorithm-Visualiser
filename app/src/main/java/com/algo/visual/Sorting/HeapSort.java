package com.algo.visual.Sorting;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.algo.visual.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HeapSort {
    public ExecutorService executorService = Executors.newSingleThreadExecutor();
    public Handler handler = new Handler(Looper.getMainLooper());
    private long lastUpdate = 0;
    private final long UPDATE_THRESHOLD = 100;

    public void executeHeapSort(){
        executorService.submit(() -> {
            int size = MainActivity.data.size();

            for(int idx = size / 2 - 1; idx >= 0; idx--){
                maxHeap(MainActivity.data, size, idx);
                updateUI();
            }
            for(int idx = size - 1; idx > 0 ; idx--){
                int temp = MainActivity.data.get(0);
                MainActivity.data.set(0, MainActivity.data.get(idx));
                MainActivity.data.set(idx, temp);
                updateUI();
                maxHeap(MainActivity.data,idx, 0);
            }
            handler.post(() -> {
                // Post execution UI update (onPostExecute replacement)
                MainActivity.sorted = true;
                MainActivity.drawGraph(MainActivity.mImageView);
                MainActivity.mImageView.setClickable(true);
            });
        });
    }

    private void maxHeap(ArrayList<Integer> myArray, int heapSize, int idx){
        int largest = idx;
        int right = 2 * idx + 1;
        int left = 2* idx + 2;

        if(left < heapSize && myArray.get(left) > myArray.get(largest))
            largest = left;
        if(right < heapSize && myArray.get(right) > myArray.get(largest))
            largest = right;

        if(largest != idx){
            int swap = myArray.get(idx);
            myArray.set(idx, myArray.get(largest));
            myArray.set(largest, swap);
            maxHeap(myArray, heapSize, largest);
        }
        updateUI();
    }

    public void updateUI(){
        if (SystemClock.uptimeMillis() - lastUpdate > UPDATE_THRESHOLD) {
            handler.post(() -> MainActivity.drawGraph(MainActivity.mImageView));
            lastUpdate = SystemClock.uptimeMillis();
        }
        try {
            Thread.sleep(MainActivity.speed / 10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

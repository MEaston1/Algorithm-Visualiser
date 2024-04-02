package com.algo.visual.Sorting;

import com.algo.visual.MainActivity;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class QuickSort {
    public ExecutorService executorService = Executors.newSingleThreadExecutor();
    public Handler handler = new Handler(Looper.getMainLooper());
    private long lastUpdate = 0; // Track when the last UI update was posted
    private final long UPDATE_THRESHOLD = 100; // Minimum time (ms) between updates

    public void executeQuickSort(int speed) {
        executorService.submit(() -> {
            sort(MainActivity.data, 0, MainActivity.data.size() - 1);
            handler.post(() -> {                            // onPostExecute replacement
                MainActivity.sorted = true;
                MainActivity.sorting = false;
                MainActivity.speed = speed;
                MainActivity.drawGraph(MainActivity.mImageView);
                MainActivity.mImageView.setClickable(true);
            });
        });
    }
    private int subdivide(ArrayList<Integer> myArray, int low, int high){
        int pivot = myArray.get(high);
        int firstIdx = low -1;
        for(int secondIdx = low; secondIdx < high; secondIdx++){
            if(myArray.get(secondIdx) < pivot){
                firstIdx++;
                int temp = myArray.get(firstIdx);

                myArray.set(firstIdx, myArray.get(secondIdx));
                myArray.set(secondIdx, temp);
            }
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
        int temp = myArray.get(firstIdx+1);
        myArray.set(firstIdx +1, myArray.get(high));
        myArray.set(high, temp);
        if (SystemClock.uptimeMillis() - lastUpdate > UPDATE_THRESHOLD) {
            handler.post(() -> MainActivity.drawGraph(MainActivity.mImageView));        // onProgressUpdate replacement
            lastUpdate = SystemClock.uptimeMillis();
        }
        try {
            Thread.sleep(MainActivity.speed / 10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return firstIdx + 1;
    }
    private void sort(ArrayList<Integer> myArray, int low, int high){
        if(low < high){
            int pivot = subdivide(myArray, low, high);
            if (SystemClock.uptimeMillis() - lastUpdate > UPDATE_THRESHOLD) {
                handler.post(() -> MainActivity.drawGraph(MainActivity.mImageView));        // onProgressUpdate replacement
                lastUpdate = SystemClock.uptimeMillis();
            }
            try {
                Thread.sleep(MainActivity.speed / 10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            sort(myArray, low, pivot - 1);
            sort(myArray, pivot + 1, high);
        }
    }
}

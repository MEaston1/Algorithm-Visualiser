package com.algo.visual.Sorting;

import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import com.algo.visual.MainActivity;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MergeSort {
    public ExecutorService executorService = Executors.newSingleThreadExecutor();
    public Handler handler = new Handler(Looper.getMainLooper());
    private long lastUpdate = 0;
    private final long UPDATE_THRESHOLD = 100;

    public void executeMergeSort() {
        executorService.submit(() -> {
            sort(MainActivity.data, 0 , MainActivity.data.size() - 1);
        });

        handler.post(() -> {
            // Post execution UI update (onPostExecute replacement)
            MainActivity.sorted = true;
            MainActivity.drawGraph(MainActivity.mImageView);
            MainActivity.mImageView.setClickable(true);
        });
    }

    public void merge(ArrayList<Integer> myArray, int left, int middle, int right){
        int sizeOne = middle - left + 1;
        int sizeTwo = right - middle;

        int[] leftArray = new int[sizeOne];
        int[] rightArray = new int[sizeTwo];

        for(int i = 0; i < sizeOne; i++)
            rightArray[i] = myArray.get(left + i);
        for(int j = 0; j < sizeTwo; j++)
            leftArray[j] = myArray.get(middle + 1 + j);

        int firstIdx = 0, secondIdx = 0;
        int mergedArray = left;

        while(firstIdx < sizeOne && secondIdx < sizeTwo){
            if(leftArray[firstIdx] <= rightArray[secondIdx]){
                myArray.set(mergedArray, leftArray[firstIdx]);
                firstIdx++;
            } else{
                myArray.set(mergedArray, rightArray[secondIdx]);
                secondIdx++;
            }
            mergedArray++;
            updateUI();
        }
        while(firstIdx < sizeOne){
            myArray.set(mergedArray, leftArray[firstIdx]);
            mergedArray++;
            updateUI();
        }
        while(secondIdx < sizeTwo){
            myArray.set(mergedArray, rightArray[secondIdx]);
            mergedArray++;
            updateUI();
        }
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

    private void sort(ArrayList<Integer> myArray, int left, int right){
        if(left < right){
            int middle = left + right /2;
            sort(myArray, left, middle);
            sort(myArray, middle+1, right);
            merge(myArray, left, middle, right);
        }
    }
}

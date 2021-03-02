package com.algo.visual.Sorting;

import android.os.AsyncTask;

import com.algo.visual.MainActivity;

import java.util.ArrayList;

public class MergeSort extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void... voids) {
        sort(MainActivity.data, 0 , MainActivity.data.size() - 1);
        return null;
    }

    void merge(ArrayList<Integer> myArray, int left, int middle, int right){
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
            publishProgress();
            try{
                Thread.sleep(MainActivity.speed);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        while(firstIdx < sizeOne){
            myArray.set(mergedArray, leftArray[firstIdx]);
            mergedArray++;
            publishProgress();
            try{
                Thread.sleep(MainActivity.speed);
            } catch (InterruptedException e){
                e.printStackTrace();
            }
        }
        while(secondIdx < sizeTwo){
            myArray.set(mergedArray, rightArray[secondIdx]);
            mergedArray++;
            publishProgress();
            try {
                Thread.sleep(MainActivity.speed);
                }catch (InterruptedException e){
                e.printStackTrace();
            }
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

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        MainActivity.drawGraph(MainActivity.mImageView);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.sorted = true;
        MainActivity.drawGraph(MainActivity.mImageView);
        MainActivity.mImageView.setClickable(true);
    }
}

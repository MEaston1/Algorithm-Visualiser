package com.algo.visual.Sorting;

import android.os.AsyncTask;

import com.algo.visual.MainActivity;

import java.util.ArrayList;

public class HeapSort extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void ... voids){
        int size = MainActivity.data.size();

        for(int idx = size / 2 - 1; idx >= 0; idx--){
            maxHeap(MainActivity.data, size, idx);
            publishProgress();
            try{
                Thread.sleep(MainActivity.speed);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
        for(int idx = size - 1; idx > 0 ; idx--){
            int temp = MainActivity.data.get(0);
            MainActivity.data.set(0, MainActivity.data.get(idx));
            MainActivity.data.set(idx, temp);
            publishProgress();
            try{
                Thread.sleep(MainActivity.speed);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            maxHeap(MainActivity.data,idx, 0);
        }
        return null;
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
    }
    @Override
    protected void onProgressUpdate(Void... values){
    super.onProgressUpdate(values);
    MainActivity.drawGraph(MainActivity.mImageView);
    }
    @Override
    protected void onPostExecute(Void aVoid){
    super.onPostExecute(aVoid);
    MainActivity.sorted = true;
    MainActivity.drawGraph(MainActivity.mImageView);
    MainActivity.mImageView.setClickable(true);
    }
}

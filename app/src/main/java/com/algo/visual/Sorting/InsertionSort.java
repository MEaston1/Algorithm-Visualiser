package com.algo.visual.Sorting;

import android.os.AsyncTask;

import com.algo.visual.MainActivity;

public class InsertionSort extends AsyncTask<Void, Void, Void> {
    @Override
    protected Void doInBackground(Void ... voids){
        int size = MainActivity.data.size();
        for(int firstIdx = 1; firstIdx < size; ++firstIdx){
            int key = MainActivity.data.get(firstIdx);
            int secondIdx = firstIdx -1;

            while(secondIdx >= 0 && MainActivity.data.get(secondIdx) > key){
                MainActivity.data.set(secondIdx + 1, MainActivity.data.get(secondIdx));
                secondIdx = secondIdx -1;
                publishProgress();
                try{
                    Thread.sleep(MainActivity.speed);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
            MainActivity.data.set(secondIdx + 1, key);
        }
        return null;
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

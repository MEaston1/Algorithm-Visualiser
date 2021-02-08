package com.algo.visual.Sorting;

import android.os.AsyncTask;

import com.algo.visual.MainActivity;

public class BubbleSort extends AsyncTask<Void, Void, Void> {

    @Override
    protected Void doInBackground(Void... voids) {
        int size = MainActivity.data.size();
        for(int firstIdx = 0; firstIdx < size -1; firstIdx++){
            for(int secondIdx = 0; secondIdx < size - firstIdx - 1; secondIdx++){
                if(MainActivity.data.get(secondIdx) > MainActivity.data.get(secondIdx + 1)){
                    int temp = MainActivity.data.get(secondIdx);
                    MainActivity.data.set(secondIdx, MainActivity.data.get(secondIdx + 1));
                    MainActivity.data.set(secondIdx + 1, temp);

                    publishProgress();
                    try {
                        Thread.sleep(MainActivity.speed / 10);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
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

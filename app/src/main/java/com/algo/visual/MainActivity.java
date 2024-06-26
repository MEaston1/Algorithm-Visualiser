package com.algo.visual;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.algo.visual.Sorting.BubbleSort;
import com.algo.visual.Sorting.HeapSort;
import com.algo.visual.Sorting.InsertionSort;
import com.algo.visual.Sorting.MergeSort;
import com.algo.visual.Sorting.QuickSort;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "App";
    private static int unsortedColour;
    private static int sortedColor;
    private static Paint paint = new Paint();
    private static Rect[] Rect;

    private static int graphSize;
    public static ImageView mImageView;
    public static TextView mTextView;
    public static TextView mTextViewAlgorithm;
    public static Button mButton;
    public static ArrayList<Integer> data = new ArrayList<>();
    static Random random = new Random();
    private static int maxWidth;
    private static int maxHeight;
    public static int speed;
    static SharedPreferences preferences;
    private static String algorithm;
    public static boolean sorted = false;
    public static boolean sorting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        graphSize = 20; // default bar sizes
        speed = 100;

        unsortedColour = ResourcesCompat.getColor(getResources(), R.color.colorPrimary, null);
        sortedColor = ResourcesCompat.getColor(getResources(), R.color.colorAccent, null);
        paint.setColor(unsortedColour);

        mImageView = findViewById(R.id.algoImage);
        mTextView = findViewById(R.id.mainText);
        mButton = findViewById(R.id.button);
        mTextViewAlgorithm = findViewById(R.id.tv_algorithm);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mButton.setVisibility(view.VISIBLE);
                sorted = false;
                sorting = false;
                initialiseArray();
                drawGraph(mImageView);
            }
        });



        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mImageView.setVisibility(view.VISIBLE);
                mTextView.setVisibility(view.INVISIBLE);
                mButton.setVisibility(view.VISIBLE);
                drawGraph(mImageView);
            }
        });
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sorted == true){
                    sorted = false;
                    initialiseArray();
                    drawGraph(mImageView);
                } else if (sorted == false) {
                    sort(mImageView);
                }
            }
        });
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        algorithm = preferences.getString(AlgorithmSelector.SelectedAlgo, "Bubble Sort");
        mTextViewAlgorithm.setText(algorithm);
        mImageView.setVisibility(View.INVISIBLE);
        mButton.setVisibility(View.INVISIBLE);
        mTextView.setVisibility(View.VISIBLE);
        if (data.size() != 0) {
            initialiseArray();
        }
    }


    static void setSpeed(){                             // for potential updates on changing sorting speed

    }    public static void initialiseArray() {              // creating a random array of integers to use as bar sizes
        data.clear();
        for(int i = 1; i < graphSize; i++){
            int value = random.nextInt(maxHeight);
            data.add(value);
        }
    }
    public static void drawGraph(View view){            // creates the unsorted graph
        int viewWidth = view.getWidth();
        int viewHeight = view.getHeight();
        if(sorted){
            paint.setColor(sortedColor);
        }else{
            paint.setColor(unsortedColour);
        }

        Bitmap graphBitmap = Bitmap.createBitmap(viewWidth, viewHeight, Bitmap.Config.ARGB_8888);
        mImageView.setImageBitmap(graphBitmap);

        maxHeight = viewHeight - 200;
        maxWidth = viewWidth - 200;
        Rect = new Rect[graphSize];
        if(data.size() == 0){
            initialiseArray();
        }
        Canvas canvas = new Canvas(graphBitmap);

        int width = maxWidth / graphSize;
        for(int i = 0; i < data.size(); i++){
            Rect[i] = new Rect();
            Rect[i].set(100 + width * i, (maxHeight + 100) - data.get(i), 100 + width *(i+1), maxHeight + 100);
            canvas.drawRect(Rect[i], paint);
        }
        // Invalidate the view, so that it gets redrawn.
        view.invalidate();
        if (sorted == true && sorting == false) {
            setSpeed();
            mButton.setText(R.string.Reset);
        } else if (sorted == false && sorting == false) {
            mButton.setText(R.string.Click);
        }
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu){      // for a navigation bar menu if implemented
       MenuInflater inflater = new MenuInflater(this);
       inflater.inflate(R.menu.menu, menu);
       return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId() == R.id.AlgorithmSelector){
            /*if(runner!=null){
                runner.cancel(true);
                runner = null;
            }*/
            Intent intent = new Intent(this, AlgorithmSelector.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void sort(View view){                            // for accessing the sorting algorithm to sort the bars in the correct pattern
        int temp = speed;
        if(mButton.getText().toString().equals(getString(R.string.show_final))){
            speed = 0;
            mButton.setVisibility(View.INVISIBLE);
            sorted = true;
            sorting = false;
        }else {
            mImageView.setClickable(false);
            mButton.setText(R.string.show_final);
        }
        sorting = true;

        new QuickSort().executeQuickSort(temp);
    }
}
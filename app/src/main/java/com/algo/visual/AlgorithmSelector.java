package com.algo.visual;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class AlgorithmSelector extends AppCompatActivity {
    public static String SelectedAlgo;
    private static final String TAG = "Algorithm";
    // Listener for changes in SharedPreferences
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new AlgorithmSelectorFragment())
                .commit();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        SelectedAlgo = sharedPreferences.getString("Select_algorithm", "default_value");
        Log.d(TAG, "Selected Algorithm: " + SelectedAlgo);
        // Define the listener
        preferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if ("select_algorithm".equals(key)) {
                    Intent intent = new Intent();
                    SelectedAlgo = sharedPreferences.getString("select_algorithm", "default_value");
                    intent.putExtra("selected_algorithm", SelectedAlgo);
                    Log.d(TAG, "Updated Algorithm: " + SelectedAlgo);
                }
            }
        };

        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}

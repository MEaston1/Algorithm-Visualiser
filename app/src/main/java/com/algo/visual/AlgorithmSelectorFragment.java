package com.algo.visual;

import android.os.Bundle;


import androidx.preference.PreferenceFragmentCompat;

public class AlgorithmSelectorFragment extends PreferenceFragmentCompat {
    private static final String TAG = "Algorithm";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
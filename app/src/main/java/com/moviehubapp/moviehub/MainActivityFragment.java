package com.moviehubapp.moviehub;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.content_main, container, false);


        return rootView;
    }

    // Temporary Images For Reference Grid Layout
    public List Pics() {

        List<Integer> tempPics = new ArrayList<>();

        tempPics.add(R.drawable.sample_2);
        tempPics.add(R.drawable.sample_3);
        tempPics.add(R.drawable.sample_4);
        tempPics.add(R.drawable.sample_5);
        tempPics.add(R.drawable.sample_6);
        tempPics.add(R.drawable.sample_7);
        tempPics.add(R.drawable.sample_0);
        tempPics.add(R.drawable.sample_1);
        tempPics.add(R.drawable.sample_2);
        tempPics.add(R.drawable.sample_3);
        tempPics.add(R.drawable.sample_4);
        tempPics.add(R.drawable.sample_5);
        tempPics.add(R.drawable.sample_6);
        tempPics.add(R.drawable.sample_7);
        tempPics.add(R.drawable.sample_0);
        tempPics.add(R.drawable.sample_1);
        tempPics.add(R.drawable.sample_2);
        tempPics.add(R.drawable.sample_3);
        tempPics.add(R.drawable.sample_4);
        tempPics.add(R.drawable.sample_5);
        tempPics.add(R.drawable.sample_6);
        tempPics.add(R.drawable.sample_7);

        return tempPics;
    }
}

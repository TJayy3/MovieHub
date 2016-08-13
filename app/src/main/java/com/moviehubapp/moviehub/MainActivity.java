package com.moviehubapp.moviehub;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        GridView moviesGridView = (GridView) findViewById(R.id.movies_gridview);
        moviesGridView.setAdapter(new ImageArrayAdapter
                (this, R.layout.single_poster_layout, Pics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Temporary Images For Reference Grid Layout
    public List Pics() {

        List<Bitmap> bitMappedPics = new ArrayList<>();
        List<Integer> tempPics = new ArrayList<>();

        bitMappedPics.add(0,BitmapFactory.decodeResource(getResources(),R.drawable.sample_2));
        bitMappedPics.add(1,BitmapFactory.decodeResource(getResources(),R.drawable.sample_3));
        bitMappedPics.add(2,BitmapFactory.decodeResource(getResources(),R.drawable.sample_4));
        bitMappedPics.add(3,BitmapFactory.decodeResource(getResources(),R.drawable.sample_5));
        bitMappedPics.add(4,BitmapFactory.decodeResource(getResources(),R.drawable.sample_6));
        bitMappedPics.add(5,BitmapFactory.decodeResource(getResources(),R.drawable.sample_7));
        bitMappedPics.add(6,BitmapFactory.decodeResource(getResources(),R.drawable.sample_0));
        bitMappedPics.add(7,BitmapFactory.decodeResource(getResources(),R.drawable.sample_1));
        bitMappedPics.add(8,BitmapFactory.decodeResource(getResources(),R.drawable.sample_2));
        bitMappedPics.add(9,BitmapFactory.decodeResource(getResources(),R.drawable.sample_3));
        bitMappedPics.add(10,BitmapFactory.decodeResource(getResources(),R.drawable.sample_4));
        bitMappedPics.add(11,BitmapFactory.decodeResource(getResources(),R.drawable.sample_5));
        bitMappedPics.add(12,BitmapFactory.decodeResource(getResources(),R.drawable.sample_6));
        bitMappedPics.add(13,BitmapFactory.decodeResource(getResources(),R.drawable.sample_7));
        bitMappedPics.add(14,BitmapFactory.decodeResource(getResources(),R.drawable.sample_0));
        bitMappedPics.add(15,BitmapFactory.decodeResource(getResources(),R.drawable.sample_1));
        bitMappedPics.add(16,BitmapFactory.decodeResource(getResources(),R.drawable.sample_2));
        bitMappedPics.add(17,BitmapFactory.decodeResource(getResources(),R.drawable.sample_3));
        bitMappedPics.add(18,BitmapFactory.decodeResource(getResources(),R.drawable.sample_4));
        bitMappedPics.add(19,BitmapFactory.decodeResource(getResources(),R.drawable.sample_5));
        bitMappedPics.add(20,BitmapFactory.decodeResource(getResources(),R.drawable.sample_6));
        bitMappedPics.add(21,BitmapFactory.decodeResource(getResources(),R.drawable.sample_7));


        return bitMappedPics;
    }
}

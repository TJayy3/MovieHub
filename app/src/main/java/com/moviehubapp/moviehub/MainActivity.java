package com.moviehubapp.moviehub;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Context context = this.getBaseContext();

        final TMDbApi tmDbApi = new TMDbApi(new TMDbApi.onTaskCompleted() {
            @Override
            public void onTaskCompleted(List result) {

                Log.v(LOG_TAG, "Background Thread Finished.");

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                Log.v(LOG_TAG, "Creating Adapter.");
                ImageAdapter imageAdapter = new ImageAdapter(context, result);
                recyclerView.setAdapter(imageAdapter);

                GridLayoutManager gridLayout =
                        new GridLayoutManager(context, 2);

                recyclerView.setLayoutManager(gridLayout);
                Log.v(LOG_TAG, "Adapter Finished Creation.");
            }
        });

        tmDbApi.setPopular();
        tmDbApi.execute();
        Log.v(LOG_TAG, "Background Thread Executing.");
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

        // no inspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}

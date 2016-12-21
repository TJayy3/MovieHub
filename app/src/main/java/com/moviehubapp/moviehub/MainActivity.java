package com.moviehubapp.moviehub;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**   Copyright 2015 Wasabeef
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 *    Origin https://github.com/wasabeef/recyclerview-animators
 */

public class MainActivity extends AppCompatActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final Context context = this.getBaseContext();

        PreferenceManager.setDefaultValues(this, R.xml.pref_filter, false);

        final GetIdsAndPosters getIdsAndPosters =
                new GetIdsAndPosters(new GetIdsAndPosters.IdsAndPostersPulled() {
            @Override
            public void IdsAndPostersPulled(List result) {

                Log.v(LOG_TAG, "Background Thread Finished.");

                RecyclerView recyclerView = (RecyclerView)
                        findViewById(R.id.recycler_view_main_movieposters);

                Log.v(LOG_TAG, "Creating Adapter.");
                ImageAdapter imageAdapter =
                        new ImageAdapter(
                                context, result, new ImageAdapter.onPosterClickListener() {
                    @Override
                    public void onItemClick(String movieId) {

                        Intent intent = new Intent(context, DetailActivity.class)
                                .putExtra(Intent.EXTRA_TEXT, movieId);

                        startActivity(intent);
                    }
                });

                ScaleInAnimationAdapter scaleInAnimationAdapter =
                        new ScaleInAnimationAdapter(imageAdapter);

                scaleInAnimationAdapter.setFirstOnly(false);
                recyclerView.setAdapter(scaleInAnimationAdapter);

                GridLayoutManager gridLayout =
                        new GridLayoutManager(context, 2);

                recyclerView.setLayoutManager(gridLayout);
                Log.v(LOG_TAG, "Adapter Finished Creation.");
            }
        });

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        String movieFilter = sharedPref.getString(getString(R.string.pref_list_filtermovies_key),
                getString(R.string.pref_filter_default_value));

        switch (Integer.parseInt(movieFilter)) {
            case 1: getIdsAndPosters.setPopular();
                break;
            case 2: getIdsAndPosters.setNowPlaying();
                break;
            case 3: getIdsAndPosters.setTopRated();
                break;
        }
        getIdsAndPosters.execute();
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

        switch (id) {

            case R.id.action_settings :

                Intent intent = new Intent(getBaseContext(), SettingsActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

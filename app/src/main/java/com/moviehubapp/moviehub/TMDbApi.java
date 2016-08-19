package com.moviehubapp.moviehub;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by TeeJay on 8/16/2016.
 */
public class TMDbApi extends AsyncTask<String, Void, String> {

    private final String LOG_TAG = TMDbApi.class.getSimpleName();

    private String defaultDataToFetch = "now_playing";
    private String dataToFetch = null;

    // Setter
    private void setDataToFetch(String dataToFetch) {
        this.dataToFetch = dataToFetch;
    }


    @Override
    protected String doInBackground(String... strings) {

        HttpURLConnection httpURLConnection = null;
        BufferedReader buffReader = null;

        // Raw JSON Data To Be Pulled From Api
        String pulledJSONData = null;

        if (dataToFetch == null) {

            setDataToFetch(defaultDataToFetch);
            Log.v(LOG_TAG, "Data To Fetch Set To Default. 'Now Playing' ");

        }

        try {

            // Construct Url To Query
            final String BASE_URL = "http://api.themoviedb.org/3";
            final String CATEGORY_MOVIE = "/movie/";
            final String DATA_TO_FETCH = dataToFetch;
            final String MY_API_KEY = "?api_key=";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(CATEGORY_MOVIE)
                    .appendPath(DATA_TO_FETCH)
                    .appendQueryParameter(MY_API_KEY, BuildConfig.THE_TMDb_API_KEY)
                    .build();

            URL urlToApi = new URL(builtUri.toString());
            Log.v(LOG_TAG, "Built URL " + builtUri.toString());

            // Open Connection, And Request Api
            httpURLConnection = (HttpURLConnection) urlToApi.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            Log.v(LOG_TAG, "Connected To Api.");

            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();

            if (inputStream == null) {
                return null;
            }

            buffReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = buffReader.readLine()) != null) {

                // To Make Debugging Easier
                // Can Print Out The Buffer
                stringBuffer.append(line + "\n");
            }

            if (stringBuffer.length() == 0) {
                // Stream Was Empty.
                return null;
            }

            pulledJSONData = stringBuffer.toString();
            Log.v(LOG_TAG, "JSON Data Of " + dataToFetch + " Pulled: " + pulledJSONData);

        } catch (IOException e) {

            e.printStackTrace();
            Log.d(LOG_TAG, "IOException Triggered.");

            // If Error Occurred
            // If Code Didn't Get Data
            return null;

        } finally {

            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
                Log.v(LOG_TAG, "Disconnected The Connection.");
            }

            if (buffReader != null) {

                try {
                    buffReader.close();

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.v(LOG_TAG, "Error Closing BufferedReader.");
                }
            }
        }

        return pulledJSONData;
    }

    // Returns NowPlaying Movies' Ids And Posters
    // In A TreeMap
    protected Map fetchNowPlaying() {

        setDataToFetch("now_playing");
        Log.v(LOG_TAG, "Data To Fetch Set To Now Playing.");

        return parseTMDbJSONData(doInBackground());
    }

    // Returns Popular Movies' Ids And Posters
    // In A TreeMap
    protected Map fetchPopular() {

        setDataToFetch("popular");
        Log.v(LOG_TAG, "Data To Fetch Set To Popular.");

        return parseTMDbJSONData(doInBackground());
    }

    // Returns TopRated Movies' Ids And Posters
    // In A TreeMap
    protected Map fetchTopRated() {

        setDataToFetch("top_rated");
        Log.v(LOG_TAG, "Data To Fetch Set To Top Rated.");

        return parseTMDbJSONData(doInBackground());
    }


    // Pulls Each Movie's Id And Poster
    // Throws Into TreeMap
    protected Map parseTMDbJSONData(String pulledJSONData) {

        // Data To Pull
        final String TMDb_RESULTS = "results";
        final String TMDb_ID = "id";
        final String TMDb_POSTER_PATH = "poster_path";

        // Store Each Id And Poster
        Map<Integer,String> movieIdAndPoster = new TreeMap<>();

        try {

            JSONObject jsonRootObject = new JSONObject(pulledJSONData);
            JSONArray resultsArray = jsonRootObject.optJSONArray(TMDb_RESULTS);

            for (int i = 0; i <= resultsArray.length(); i++) {

                JSONObject singleJSONObject = resultsArray.getJSONObject(i);

                // Add Id Into Keys, Poster Into Values.
                movieIdAndPoster.put(
                        Integer.parseInt(singleJSONObject.optString(TMDb_ID)),
                        singleJSONObject.optString(TMDb_POSTER_PATH));
            }

            return movieIdAndPoster;

        } catch (JSONException e) {

            e.printStackTrace();
            Log.v(LOG_TAG, "JSONException Triggered.");
            return null;
        }
    }

}

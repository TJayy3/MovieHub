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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TeeJay on 8/16/2016.
 */

public class TMDbApi extends AsyncTask<Void,Void,List> {

    private final String LOG_TAG = TMDbApi.class.getSimpleName();

    private String mDefaultDataToFetch = "now_playing";
    private String mDataToFetch = null;
    private Map movieIdsAndPosters = null;
    private List<Movie> movieList = null;
    private onTaskCompleted result = null;

    Movie movie = new Movie();

    public TMDbApi(onTaskCompleted result) {
        this.result = result;
    }

    public void setMovieIdsAndPosters(Map movieIdsAndPosters) {
        this.movieIdsAndPosters = movieIdsAndPosters;
        Log.v(LOG_TAG, "Map Of Ids And Posters " + movieIdsAndPosters.toString());
    }

    public Map getMovieIdsAndPosters()
    {return movieIdsAndPosters;}

    public List getMovieList()
    {return this.movieList;}

    private void setMovieList(List<Movie> movieList) {
        Log.v(LOG_TAG, "setMovieList: " + movieList);
        this.movieList = movieList;
    }

    private void setmDataToFetch(String mDataToFetch)
    {this.mDataToFetch = mDataToFetch;}

    public void setNowPlaying() {

        setmDataToFetch("now_playing");
        Log.v(LOG_TAG, "Data To Fetch Set To Now Playing.");
    }

    public void setPopular() {

        setmDataToFetch("popular");
        Log.v(LOG_TAG, "Data To Fetch Set To Popular.");
    }

    public void setTopRated() {

        setmDataToFetch("top_rated");
        Log.v(LOG_TAG, "Data To Fetch Set To Top Rated.");
    }

    @Override
    protected void onPostExecute(List list) {
        super.onPostExecute(list);
        setMovieList(list);
        result.onTaskCompleted(list);
    }

    @Override
    protected List doInBackground(Void... params) {

        HttpURLConnection httpURLConnection = null;
        BufferedReader buffReader = null;

        // Raw JSONData To Be Pulled From Api
        String pulledJSONData = null;

        if (mDataToFetch == null) {

            setmDataToFetch(mDefaultDataToFetch);
            Log.v(LOG_TAG, "Data To Fetch Set To Default. 'NowPlaying'");
        }

        try {

            // Construct Url To Query
            final String BASE_URL = "https://api.themoviedb.org/3";
            final String CATEGORY_MOVIE = "movie";
            final String DATA_TO_FETCH = mDataToFetch;
            final String MY_API_KEY = "api_key";

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(CATEGORY_MOVIE)
                    .appendPath(DATA_TO_FETCH)
                    .appendQueryParameter(MY_API_KEY, BuildConfig.THE_TMDb_API_KEY)
                    .build();

            URL urlToApi = new URL(builtUri.toString());
            Log.v(LOG_TAG, "BuiltURL " + builtUri.toString());

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

                stringBuffer.append(line + "\n");
            }

            // Stream Was Empty.
            if (stringBuffer.length() == 0) {
                return null;
            }

            pulledJSONData = stringBuffer.toString();
            Log.v(LOG_TAG, "JSONData Of " +
                    "'" + mDataToFetch + "'" +
                    " Pulled: " + pulledJSONData);

        } catch (IOException e) {

            e.printStackTrace();
            Log.d(LOG_TAG, "IOException Triggered.");
            return null;

        } finally {

            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
                Log.v(LOG_TAG,"Disconnected The Connection.");
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

        setMovieIdsAndPosters(parseTMDbJSONData(pulledJSONData));
        List list = movie.convertMapToMovieList(getMovieIdsAndPosters());

        return list;
    }

    // Pulls Each Movie's Id And Poster
    // Throws Into TreeMap
    private Map parseTMDbJSONData(String pulledJSONData) {

        // Data To Pull
        final String TMDb_RESULTS = "results";
        final String TMDb_ID = "id";
        final String TMDb_POSTER_PATH = "poster_path";

        // Store Each Id And Poster
        Map<Integer,String> movieIdAndPoster = new LinkedHashMap<>();

        try {

            JSONObject jsonRootObject = new JSONObject(pulledJSONData);
            JSONArray resultsArray = jsonRootObject.optJSONArray(TMDb_RESULTS);

            for (int i = 0; i < resultsArray.length(); i++) {

                JSONObject singleJSONObject = resultsArray.getJSONObject(i);

                // Add Id Into Keys, Poster Into Values.
                movieIdAndPoster.put(
                        Integer.parseInt(singleJSONObject.optString(TMDb_ID)),
                        // Substring Used To Remove '/' From Path.
                        singleJSONObject.optString(TMDb_POSTER_PATH).substring(1));
            }

            return movieIdAndPoster;

        } catch (JSONException e) {

            e.printStackTrace();
            Log.v(LOG_TAG, "JSONException Triggered.");
            return null;
        }
    }

    public interface onTaskCompleted {
        void onTaskCompleted(List result);
    }
}

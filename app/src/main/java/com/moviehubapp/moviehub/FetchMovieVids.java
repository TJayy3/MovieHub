package com.moviehubapp.moviehub;

import android.net.Uri;
import android.os.Process;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TeeJay on 9/10/2016.
 */
public class FetchMovieVids implements Runnable {

    private final String LOG_TAG = FetchMovieVids.class.getSimpleName();

    private String pulledJSONData;
    private String movieId;

    public FetchMovieVids(String movieId) {
        this.movieId = movieId;
    }

    public String getJSONMovieVids()
    {return pulledJSONData;}

    public void setJSONMovieVids(String pulledJSONData)
    {this.pulledJSONData = pulledJSONData;}

    @Override
    public void run() {

        // Moves Thread To Background
        android.os.Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        HttpURLConnection http = null;
        BufferedReader buffReader = null;

        final String BASE_URL = "http://api.themoviedb.org/3/movie/";
        final String MOVIE_ID = movieId;
        final String MOVIE_VIDS = "videos";
        final String MY_API_KEY = "api_key";

        try {

            Uri movieVidsUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(MOVIE_ID)
                    .appendPath(MOVIE_VIDS)
                    .appendQueryParameter(MY_API_KEY, BuildConfig.THE_TMDb_API_KEY)
                    .build();

            URL movieVidsUrl = new URL(movieVidsUri.toString());
            Log.v(LOG_TAG, "MovieVidsUrl BUILT URL " + movieVidsUrl);

            http = (HttpURLConnection) movieVidsUrl.openConnection();
            http.setRequestMethod("GET");
            http.connect();

            InputStream inputStream = http.getInputStream();
            StringBuilder sBuilder = new StringBuilder();

            if (inputStream == null) {

                Log.v(LOG_TAG, "INPUT STREAM WAS EMPTY");
            }

            buffReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = buffReader.readLine()) != null) {

                sBuilder.append(line + "\n");
            }

            if (sBuilder.length() == 0) {

                Log.v(LOG_TAG, "STRING BUILDER WAS EMPTY");
            }

            pulledJSONData = sBuilder.toString();
            Log.v(LOG_TAG, "MOVIE VIDEOS OF \n"
                    + movieId
                    + "\n MOVIE JSON DATA \n"
                    + pulledJSONData);

            setJSONMovieVids(pulledJSONData);
            Log.v(LOG_TAG, "FETCHMOVIEVIDS FINISHED.");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (http != null) {

                http.disconnect();
            }

            if (buffReader != null) {

                try {

                    buffReader.close();

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }

    public Movie parseMovieVids(Movie movie) {

        String movieVidsJSON = getJSONMovieVids();
        List<MovieVideo> movieVids = new ArrayList();

        final String ARRAY_RESULTS = "results";
        final String VID_KEY = "key";
        final String VID_NAME = "name";
        final String VID_TYPE = "type";

        try {

            JSONObject jsonObject = new JSONObject(movieVidsJSON);
            JSONArray jsonArray = jsonObject.getJSONArray(ARRAY_RESULTS);

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObj = jsonArray.getJSONObject(i);

                MovieVideo movieVideo = new MovieVideo();

                movieVideo.setMovieVidKey(jsonObj.optString(VID_KEY));
                movieVideo.setMovieVidName(jsonObj.optString(VID_NAME));
                movieVideo.setMovieVidType(jsonObj.optString(VID_TYPE));

                movieVids.add(i,movieVideo);
            }

            movie.setMovieVids(movieVids);

            return movie;

        } catch (JSONException e) {

            e.printStackTrace();
            Log.v(LOG_TAG, "JSON EXCEPTION TRIGGERED.");
            return null;
        }
    }

}

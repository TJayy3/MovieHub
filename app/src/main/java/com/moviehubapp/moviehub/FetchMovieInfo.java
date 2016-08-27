package com.moviehubapp.moviehub;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by TeeJay on 8/26/2016.
 */
public class FetchMovieInfo extends AsyncTask<Void,Void,Movie> {

    private static final String LOG_TAG = FetchMovieInfo.class.getSimpleName();

    MovieInfoPulled result = null;
    private String movieId;
    private String pulledJSONData;

    public FetchMovieInfo(MovieInfoPulled result) {
        this.result = result;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    @Override
    protected void onPostExecute(Movie movie) {
        super.onPostExecute(movie);
        result.movieInfoPulled(movie);
    }

    @Override
    protected Movie doInBackground(Void... Void) {

        HttpURLConnection httpURLConnection = null;
        BufferedReader buffReader = null;

        final String BASE_URL = "http://api.themoviedb.org/3/movie/";
        final String MOVIE_ID = getMovieId();
        final String MY_API_KEY = "api_key";

        try {

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(MOVIE_ID)
                    .appendQueryParameter(MY_API_KEY, BuildConfig.THE_TMDb_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());
            Log.v(LOG_TAG, "BuiltURL: " + url.toString());

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();
            Log.v(LOG_TAG, "Connected To Api.");

            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuffer stringBuffer = new StringBuffer();

            if (inputStream == null) {

                Log.v(LOG_TAG, "InputStream Null.");
                return null;
            }

            buffReader = new BufferedReader(new InputStreamReader(inputStream));

            String line;

            while ((line = buffReader.readLine()) != null) {

                stringBuffer.append(line + "\n");
            }

            if (stringBuffer.length() == 0) {

                return null;
            }

            pulledJSONData = stringBuffer.toString();
            Log.v(LOG_TAG, "JSON Data Of MOVIE "
                    + MOVIE_ID + "Pulled: \n" + pulledJSONData);

        } catch (IOException e) {

            e.printStackTrace();
            Log.v(LOG_TAG, "IO Exception Triggered.");

        } finally {

            if (httpURLConnection != null) {

                httpURLConnection.disconnect();
                Log.v(LOG_TAG, "Disconnected.");
            }

            if (buffReader != null) {

                try {

                    buffReader.close();

                } catch (IOException e) {

                    Log.v(LOG_TAG, "BufferReader Didn't Close.");
                    e.printStackTrace();
                }
            }
        }

        return parseJSONDataToMovie(pulledJSONData);
    }

    public Movie parseJSONDataToMovie(String pulledJSONData) {

        Movie movie = new Movie();

        final String MOVIE_DESCRIPTION = "overview";
        final String MOVIE_TITLE = "original_title";
        final String MOVIE_RELEASE_DATE = "release_date";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_RUNTIME = "runtime";
        final String MOVIE_RATING = "vote_average";

        try {

            JSONObject jsonRootObject = new JSONObject(pulledJSONData);

            // Parse Json
            movie.setmMovieTitle(
                    jsonRootObject.optString(MOVIE_TITLE));
            movie.setmMovieDescription(
                    jsonRootObject.optString(MOVIE_DESCRIPTION));
            movie.setmMovieReleaseDate(
                    jsonRootObject.optString(MOVIE_RELEASE_DATE));
            movie.setmMovieRuntime(
                    jsonRootObject.optInt(MOVIE_RUNTIME));
            movie.setmMovieRating(
                    jsonRootObject.optInt(MOVIE_RATING));
            movie.setmMovieId(
                    Integer.parseInt(movieId));
            movie.setmMoviePosterBitmap(
                    movie.getBitmapImageFromUrl(
                            movie.posterPathToURL(
                                    jsonRootObject.optString(MOVIE_POSTER_PATH).substring(1))));

        } catch (JSONException e) {

            Log.v(LOG_TAG, "JSON Exception Triggered.");
            e.printStackTrace();
        }

        return movie;
    }

    public interface MovieInfoPulled {
        void movieInfoPulled(Movie result);
    }
}

package com.moviehubapp.moviehub;

import android.net.Uri;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FetchMovieInfo extends AsyncTask<Void,Void,Movie> {

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

        FetchMovieVids fetchMovieVids = new FetchMovieVids(getMovieId());
        fetchMovieVids.run();

        try {

            Uri movieInfoUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(MOVIE_ID)
                    .appendQueryParameter(MY_API_KEY, BuildConfig.THE_TMDb_API_KEY)
                    .build();

            URL movieInfoUrl = new URL(movieInfoUri.toString());

            httpURLConnection = (HttpURLConnection) movieInfoUrl.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

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

            if (stringBuffer.length() == 0) {

                return null;
            }

            pulledJSONData = stringBuffer.toString();

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            if (httpURLConnection != null) {

                httpURLConnection.disconnect();
            }

            if (buffReader != null) {

                try {

                    buffReader.close();

                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }

        Movie movie = new Movie();

        return fetchMovieVids.parseMovieVids(parseMovieInfo(pulledJSONData, movie));
    }

    public Movie parseMovieInfo(String pulledJSONData, Movie movie) {

        final String MOVIE_DESCRIPTION = "overview";
        final String MOVIE_TITLE = "title";
        final String MOVIE_RELEASE_DATE = "release_date";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_RUNTIME = "runtime";
        final String MOVIE_RATING = "vote_average";
        final String MOVIE_BACKDROP_PATH = "backdrop_path";

        try {

            JSONObject jsonRootObject = new JSONObject(pulledJSONData);

            // Parse Json
            movie.setmMovieTitle(
                    jsonRootObject.optString(MOVIE_TITLE));
            movie.setmMovieDescription(
                    jsonRootObject.optString(MOVIE_DESCRIPTION));
            movie.setmMovieReleaseDate(
                    movie.dateFormat("MMMM yyyy", "yyyy-MM-dd",
                            jsonRootObject.optString(MOVIE_RELEASE_DATE)));
            movie.setmRuntimeHour(
                    movie.hourFormat(
                            jsonRootObject.optString(MOVIE_RUNTIME)));
            movie.setmRunTimeMin(
                    movie.minFormat(
                            jsonRootObject.optString(MOVIE_RUNTIME)));
            movie.setmMovieRating(
                    jsonRootObject.optDouble(MOVIE_RATING));
            movie.setmMovieId(
                    Integer.parseInt(movieId));
            movie.setmMoviePosterBitmap(
                    movie.getBitmapImageFromUrl(
                            movie.pathToURL(
                                    jsonRootObject.optString(MOVIE_POSTER_PATH).substring(1))));
            movie.setmMovieBackDropBitmap(
                    movie.getBitmapImageFromUrl(
                            movie.pathToURL(
                                    jsonRootObject.optString(MOVIE_BACKDROP_PATH).substring(1))));

        } catch (JSONException e) {

            e.printStackTrace();
        }

        return movie;
    }

    public interface MovieInfoPulled {
        void movieInfoPulled(Movie result);
    }
}

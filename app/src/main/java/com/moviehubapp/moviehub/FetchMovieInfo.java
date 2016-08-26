package com.moviehubapp.moviehub;

import android.os.AsyncTask;

/**
 * Created by TeeJay on 8/26/2016.
 */
public class FetchMovieInfo extends AsyncTask<String,Void,Movie> {

    MovieInfoPulled result = null;

    public FetchMovieInfo(MovieInfoPulled result) {
        this.result = result;
    }

    @Override
    protected void onPostExecute(Movie movie) {
        super.onPostExecute(movie);
        result.movieInfoPulled(movie);
    }


    @Override
    protected Movie doInBackground(String... strings) {
        return null;
    }

    public interface MovieInfoPulled {
        void movieInfoPulled(Movie result);
    }
}

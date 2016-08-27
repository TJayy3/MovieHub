package com.moviehubapp.moviehub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        String movieId = intent.getStringExtra(Intent.EXTRA_TEXT);

        FetchMovieInfo fetchMovieInfo =
                new FetchMovieInfo(new FetchMovieInfo.MovieInfoPulled() {
                    @Override
                    public void movieInfoPulled(Movie result) {

                        DetailViewHolder dVHolder = new DetailViewHolder(rootView);
                        dVHolder.set(result);
                    }
                });

        fetchMovieInfo.setMovieId(movieId);
        fetchMovieInfo.execute();

        return rootView;
    }
}

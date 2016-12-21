package com.moviehubapp.moviehub;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        final View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        final Context mContext = rootView.getContext();

        String movieId = intent.getStringExtra(Intent.EXTRA_TEXT);

        FetchMovieInfo fetchMovieInfo =
                new FetchMovieInfo(new FetchMovieInfo.MovieInfoPulled() {
                    @Override
                    public void movieInfoPulled(Movie result) {

                        DetailViewHolder dVHolder = new DetailViewHolder(rootView);
                        dVHolder.set(result);

                        RecyclerView movieRecyclerView = (RecyclerView)
                                rootView.findViewById(R.id.recycler_view_detail_movie_videos);
                        movieRecyclerView.setHasFixedSize(true);

                        LinearLayoutManager linearLayoutManager =
                                new LinearLayoutManager(mContext);
                        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);

                        movieRecyclerView.setLayoutManager(linearLayoutManager);

                        MovieVideosAdapter movieVideosAdapter =
                                new MovieVideosAdapter(
                                        mContext, result.getMovieVids());

                        movieRecyclerView.setAdapter(movieVideosAdapter);
                    }
                });

        fetchMovieInfo.setMovieId(movieId);
        fetchMovieInfo.execute();

        return rootView;
    }
}

package com.moviehubapp.moviehub;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by TeeJay on 8/26/2016.
 */
public class DetailViewHolder extends RecyclerView.ViewHolder {

    private ImageView moviePoster, movieBackDrop;
    private TextView movieTitle, movieReleaseDate, movieRating,
                     movieDescription, movieRuntimeHour, movieRuntimeMin;

    public DetailViewHolder(View itemView) {
        super(itemView);

        moviePoster = (ImageView)
                itemView.findViewById(R.id.imageview_detail_movie_poster);
        movieTitle = (TextView)
                itemView.findViewById(R.id.textview_detail_movie_movietitle);
        movieReleaseDate = (TextView)
                itemView.findViewById(R.id.textview_detail_movie_releasedate);
        movieRating = (TextView)
                itemView.findViewById(R.id.textview_detail_movie_rating);
        movieDescription = (TextView)
                itemView.findViewById(R.id.textview_detail_movie_synopsis_info);
        movieRuntimeHour = (TextView)
                itemView.findViewById(R.id.textview_detail_movie_runtime_hour);
        movieRuntimeMin = (TextView)
                itemView.findViewById(R.id.textview_detail_movie_runtime_min);
        movieBackDrop = (ImageView)
                itemView.findViewById(R.id.imageview_detail_movie_backdrop);
    }

    public void set(Movie movie) {

        moviePoster.setImageBitmap(movie.getmMoviePosterBitmap());
        movieTitle.setText(movie.getmMovieTitle());
        movieReleaseDate.setText(movie.getmMovieReleaseDate());
        movieRating.setText(String.valueOf(movie.getmMovieRating()));
        movieDescription.setText(movie.getmMovieDescription());
        movieRuntimeHour.setText(String.valueOf(movie.getmRuntimeHour()));
        movieRuntimeMin.setText(String.valueOf(movie.getmRunTimeMin()));
        movieBackDrop.setImageBitmap(movie.getmMovieBackDropBitmap());
    }
}

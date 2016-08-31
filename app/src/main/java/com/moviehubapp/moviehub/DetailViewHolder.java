package com.moviehubapp.moviehub;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by TeeJay on 8/26/2016.
 */
public class DetailViewHolder extends RecyclerView.ViewHolder{

    private ImageView poster;
    private TextView movieTitle;
    private TextView movieReleaseDate;
    private TextView movieRating;
    private TextView movieDescription;
    private TextView movieRuntime;

    public DetailViewHolder(View itemView) {
        super(itemView);

        poster = (ImageView)
                itemView.findViewById(R.id.imageview_detail_movie_poster);
        movieTitle = (TextView)
                itemView.findViewById(R.id.textview_detail_movie_movietitle);
        movieReleaseDate = (TextView)
                itemView.findViewById(R.id.textview_detail_movie_releasedate);
        movieRating = (TextView)
                itemView.findViewById(R.id.textview_detail_movie_rating);
        movieDescription = (TextView)
                itemView.findViewById(R.id.textview_detail_movie_synopsis_info);
        movieRuntime = (TextView)
                itemView.findViewById(R.id.textview_detail_movie_runtime);
    }

    public void set(Movie movie) {

        poster.setImageBitmap(movie.getmMoviePosterBitmap());
        movieTitle.setText(movie.getmMovieTitle());
        movieReleaseDate.setText(movie.getmMovieReleaseDate());
        movieRating.setText(String.valueOf(movie.getmMovieRating()));
        movieDescription.setText(movie.getmMovieDescription());
        movieRuntime.setText(String.valueOf(movie.getmMovieRuntime()));
    }
}

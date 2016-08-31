package com.moviehubapp.moviehub;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by TeeJay on 8/10/2016.
 */
public class ImageAdapter extends
        RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private final String LOG_TAG = ImageAdapter.class.getSimpleName();

    private Context mContext;
    private List<Movie> data;
    private onPosterClickListener posterClicked;

    public ImageAdapter(Context mContext, List data,
                        onPosterClickListener posterClicked) {

        this.mContext = mContext;
        this.data = data;
        this.posterClicked = posterClicked;
    }

    @Override
    public void onBindViewHolder(ImageAdapter.ViewHolder viewHolder, int position) {

        Movie movie = data.get(position);

        viewHolder.bind(movie, posterClicked);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public ImageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View singlePosterLayout = inflater
                .inflate(R.layout.main_single_poster_layout, parent, false);

        ViewHolder viewHolder = new ViewHolder(singlePosterLayout);

        return viewHolder;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView poster;
        private TextView id;

        public ViewHolder(View itemView) {
            super(itemView);

            poster = (ImageView) itemView.findViewById(R.id.imageview_single_poster);
            id = (TextView) itemView.findViewById(R.id.textview_movie_id);
        }

        public void bind(final Movie movie,
                         final onPosterClickListener posterClicked) {

            poster.setImageBitmap(movie.getmMoviePosterBitmap());
            id.setText(String.valueOf(movie.getmMovieId()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    posterClicked.onItemClick(id.getText().toString());
                }
            });
        }
    }

    public interface onPosterClickListener {
        void onItemClick(String movieId);
    }
}

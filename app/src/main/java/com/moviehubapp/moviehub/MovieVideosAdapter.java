package com.moviehubapp.moviehub;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;

import java.util.List;

/**
 * Created by TeeJay on 9/17/2016.
 */
public class MovieVideosAdapter extends
        RecyclerView.Adapter<MovieVideosAdapter.ViewHolder> {

    private static final String LOG_TAG = MovieVideosAdapter.class.getSimpleName();

    private Context mContext;
    private List<MovieVideo> movieVids;

    public MovieVideosAdapter(Context mContext, List<MovieVideo> movieVids) {

        this.mContext = mContext;
        this.movieVids = movieVids;
    }

    public Context getContext() {return mContext;}

    @Override
    public MovieVideosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);

        View movieVideoLayout = inflater
                .inflate(R.layout.detail_movie_videos_layout, parent, false);

        return new ViewHolder(movieVideoLayout, mContext);
    }

    @Override
    public void onBindViewHolder(MovieVideosAdapter.ViewHolder holder, int position) {

        MovieVideo movieVideo = movieVids.get(position);

        holder.bind(movieVideo);
    }

    @Override
    public int getItemCount() {return movieVids.size();}

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView movieVidKey;
        private TextView movieVidName;
        private TextView movieVidType;
        private YouTubeThumbnailView movieVidThumbnail;
        private Context theContext;

        public ViewHolder(View itemView, Context theContext) {
            super(itemView);

            this.theContext = theContext;

            movieVidKey = (TextView)
                    itemView.findViewById(R.id.textview_detail_movie_movievideo_key);
            movieVidName = (TextView)
                    itemView.findViewById(R.id.textview_detail_movie_movievideo_name);
            movieVidType = (TextView)
                    itemView.findViewById(R.id.textview_detail_movie_movievideo_type);
            movieVidThumbnail = (YouTubeThumbnailView)
                    itemView.findViewById(R.id.youtubethumbnailview_thumbnail);

            movieVidThumbnail.setOnClickListener(this);
        }

        public void bind(final MovieVideo movieVideo) {

            final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener =
                    new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {

                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView,
                                                      String s) {

                            youTubeThumbnailView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView,
                                                     YouTubeThumbnailLoader.ErrorReason
                                                             errorReason) {

                            // Error Occurred
                        }
                    };

            movieVidThumbnail.initialize(BuildConfig.YOUTUBE_PLAYER_API_KEY,
                    new YouTubeThumbnailView.OnInitializedListener() {

                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView,
                                                    YouTubeThumbnailLoader youTubeThumbnailLoader) {

                    youTubeThumbnailLoader.setVideo(movieVideo.getMovieVidKey());
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView,
                                                    YouTubeInitializationResult youTubeInitializationResult) {

                    // Error Occurred
                }
            });

            movieVidKey.setText(movieVideo.getMovieVidKey());
            movieVidName.setText(movieVideo.getMovieVidName());
            movieVidType.setText(movieVideo.getMovieVidType());
        }

        @Override
        public void onClick(View v) {

            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=" + movieVidKey.getText()));

            PackageManager packageManager = theContext.getPackageManager();
            List<ApplicationInfo> packages;

            packages = packageManager.getInstalledApplications(0);

            boolean hasYTApp = false;

            for (ApplicationInfo packageInfo: packages) {

                if (packageInfo.packageName.equals("com.google.android.youtube")) {

                    hasYTApp = true;
                }
            }

            if (hasYTApp) {

                intent.setPackage("com.google.android.youtube");
                theContext.startActivity(intent);

            } else {

                theContext.startActivity(intent);
            }

        }
    }

}

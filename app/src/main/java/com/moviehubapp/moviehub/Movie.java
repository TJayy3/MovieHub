package com.moviehubapp.moviehub;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by TeeJay on 8/21/2016.
 */
public class Movie {

    private final String LOG_TAG = Movie.class.getSimpleName();

    private int mMovieId, mMovieRuntime, mMovieRating;
    private String mMovieDescription, mMovieTitle, mMovieReleaseDate;
    private Bitmap mMoviePosterBitmap;

    public Movie() {

    }

    public void setmMovieId(int mMovieId)
    {this.mMovieId = mMovieId;}

    public int getmMovieId()
    {return this.mMovieId;}

    public void setmMoviePosterBitmap(Bitmap mMoviePosterBitmap)
    {this.mMoviePosterBitmap = mMoviePosterBitmap;}

    public Bitmap getmMoviePosterBitmap()
    {return mMoviePosterBitmap;}

    public String getmMovieTitle()
    {return mMovieTitle;}

    public void setmMovieTitle(String mMovieTitle)
    {this.mMovieTitle = mMovieTitle;}

    public String getmMovieDescription()
    {return mMovieDescription;}

    public void setmMovieDescription(String mMovieDescription)
    {this.mMovieDescription = mMovieDescription;}

    public String getmMovieReleaseDate()
    {return mMovieReleaseDate;}

    public void setmMovieReleaseDate(String mMovieReleaseDate)
    {this.mMovieReleaseDate = mMovieReleaseDate;}

    public int getmMovieRuntime()
    {return mMovieRuntime;}

    public void setmMovieRuntime(int mMovieRuntime)
    {this.mMovieRuntime = mMovieRuntime;}

    public int getmMovieRating()
    {return mMovieRating;}

    public void setmMovieRating(int mMovieRating)
    {this.mMovieRating = mMovieRating;}


    // Takes Map Of Id And Poster Pairs
    // Creates A Movie Object Of Each Pair
    // Then Sets Fields To Define Itself
    public List<Movie> convertMapToMovieList(Map mapOfMovies) {

        List<Movie> listOfMovies = new ArrayList<>();

        Set mapSet = mapOfMovies.entrySet();
        Iterator mapIterator = mapSet.iterator();

        int index = 0;

        while (mapIterator.hasNext()) {

            Map.Entry mapEntry = (Map.Entry) mapIterator.next();

            Movie movie = new Movie();

            // Set Id With Key In Map
            movie.setmMovieId(Integer.parseInt(mapEntry.getKey().toString()));
            Log.v(LOG_TAG, "PosterId: " + movie.getmMovieId());

            // Set Bitmap For Poster
            movie.setmMoviePosterBitmap(
                    getBitmapImageFromUrl(
                            movie.posterPathToURL(mapEntry.getValue().toString())));

            // Add To List
            listOfMovies.add(index++,movie);
        }

        return listOfMovies;
    }


    // Pulls Out A Bitmap
    // From URL Passed In
    public Bitmap getBitmapImageFromUrl(URL url) {

        Bitmap bitMap;

        try {

            URL posterPathUrl = new URL(url.toString());
            URLConnection urlConnection = posterPathUrl.openConnection();
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            if (inputStream == null) {

                Log.v(LOG_TAG, "InputStream Empty.");
                return null;
            }

            BufferedInputStream buffIS =
                    new BufferedInputStream(inputStream);

            bitMap = BitmapFactory.decodeStream(buffIS);

            inputStream.close();
            buffIS.close();

            return bitMap;

        } catch (IOException e) {

            e.printStackTrace();
            Log.v(LOG_TAG, "IOException Triggered.");
            return null;
        }
    }


    // Creates URL With
    // Specified Poster Path
    // Sets Url To Field
    public URL posterPathToURL(String posterPath) {

        try {

            final String BASE_URL = "http://image.tmdb.org/t/p/";
            final String POSTER_SIZE = "w500";
            final String POSTER_PATH = posterPath;

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(POSTER_SIZE)
                    .appendPath(POSTER_PATH)
                    .build();

            URL posterUrl = new URL(builtUri.toString());
            Log.v(LOG_TAG, "Built Poster URL: " + posterUrl);

            return posterUrl;

        } catch (IOException e) {

            e.printStackTrace();
            Log.v(LOG_TAG, "IOException Triggered.");
            return null;
        }
    }
}
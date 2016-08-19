package com.moviehubapp.moviehub;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by TeeJay on 8/18/2016.
 */
public class Movies extends TMDbApi {

    private final String LOG_TAG = Movies.class.getSimpleName();

    public Movies() {}


    public List getTopRated() {

        return putPairsIntoList(super.fetchTopRated());
    }

    public List getPopular() {

        return putPairsIntoList(super.fetchPopular());
    }

    public List getNowPlaying() {

        return putPairsIntoList(super.fetchNowPlaying());
    }


    private List putPairsIntoList(Map pairs) {

        Map<Integer,String> singleMovie = new HashMap<>();

        List<Map> listOfMovies = new ArrayList<>();

        Log.v(LOG_TAG, "Creating List Of Pairs");

        for (int i = 0; i < pairs.keySet().toArray().length; i++) {

            singleMovie.put(

                    // Put Id Into Key
                    Integer.parseInt(
                            pairs.keySet().toArray()[i].toString()) ,

                    // Put Poster Into Value
                    pairs.get(
                            Integer.parseInt(
                                    pairs.keySet().toArray()[i].toString()))
                            .toString());

            listOfMovies.add(i,singleMovie);
        }

        Log.v(LOG_TAG, "Returning List Of Pairs");
        return listOfMovies;
    }
}

package com.moviehubapp.moviehub;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TeeJay on 8/10/2016.
 */
public class ImageArrayAdapter extends ArrayAdapter {

    private Context context;
    private int layoutResID;
    private List data = new ArrayList();

    public ImageArrayAdapter(Context context, int layoutResID, List data) {
        super(context, layoutResID, data);

        this.context = context;
        this.data = data;
        this.layoutResID = layoutResID;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ImageView imageView;

        if (view == null) {

            LayoutInflater inflater =  ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layoutResID, viewGroup, false);
            imageView = (ImageView) view;

        } else {

            imageView = (ImageView) view;
        }

        // TEMP DATA
        List<Bitmap> bitMappedPics = new ArrayList<>();

        bitMappedPics.add(0, BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sampleposter));
        bitMappedPics.add(1, BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sampleposter));
        bitMappedPics.add(2, BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sampleposter));
        bitMappedPics.add(3, BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sampleposter));
        bitMappedPics.add(4, BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sampleposter));
        bitMappedPics.add(5, BitmapFactory.decodeResource(getContext().getResources(), R.drawable.sampleposter));


        for (Bitmap bit : bitMappedPics) {

            imageView.setImageBitmap(bit);

        }



        return imageView;
    }

}

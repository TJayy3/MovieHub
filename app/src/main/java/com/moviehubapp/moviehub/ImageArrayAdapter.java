package com.moviehubapp.moviehub;

import android.app.Activity;
import android.content.Context;
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
    // Ignore This
    private List data = new ArrayList();

    public ImageArrayAdapter(Context context, int layoutResID, List data) {
        super(context, layoutResID, data);

        this.context = context;
        this.data = data;
        this.layoutResID = layoutResID;

    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder holder;

        if (view == null) {

            // Inflate New View
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            view = inflater.inflate(layoutResID, viewGroup, false);

            // Hold View's Reference So
            // You Don't Have To Look
            // For It Again
            holder = new ViewHolder();
            holder.poster = (ImageView) view.findViewById(R.id.single_poster_imageview);

            // Tag Holder To View
            view.setTag(holder);

        } else {

            // If View Is Already Created
            // Get View's Tag
            holder = (ViewHolder) view.getTag();
        }

        // Load Images To Be Displayed
        holder.poster.setImageBitmap(BitmapFactory.decodeResource(
                getContext().getResources(), R.drawable.sampleposter));


        return view;
    }

    // Holds View
    static class ViewHolder {

        ImageView poster;

    }
}

package com.example.zeus.movieapp;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanvi on 26-03-2016.
 */
public class GenreArrayAdapter extends ArrayAdapter<GenreDetail>
{
    Context context;
    ArrayList<GenreDetail> genredetail ;

    public GenreArrayAdapter(Context context,ArrayList<GenreDetail> objects) {
        super(context,0, objects);
        this.context=context;
       this.genredetail=objects;

    }

   public static class ViewHolder
    {

       public TextView Genrename;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.genre_layout, null);
            ViewHolder vh = new ViewHolder();
            vh.Genrename = (TextView) convertView.findViewById(R.id.textViewGenreName);
            convertView.setTag(vh);
        }
       GenreDetail currentGenre = genredetail.get(position);

        ViewHolder vh = (ViewHolder) convertView.getTag();
        vh.Genrename.setText(currentGenre.getGenre_name());




        return convertView;
    }



}

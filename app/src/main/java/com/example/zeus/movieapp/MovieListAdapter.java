package com.example.zeus.movieapp;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zeus.movieapp.Models.Movie;

import java.util.ArrayList;

/**
 * Created by Zeus on 27-Mar-16.
 */
public class MovieListAdapter extends ArrayAdapter<Movie> {

    Context context;
    ArrayList<Movie> movieArrayList;

    public MovieListAdapter(Context context, ArrayList<Movie> objects) {
        super(context,0, objects);
        this.context=context;
        movieArrayList=objects;
    }
    public static class MovieListViewHolder{
        TextView moviePopularityTextView;
        TextView movieTitletextView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=View.inflate(context,R.layout.movie_list_item,null);
            MovieListViewHolder mvh=new MovieListViewHolder();
            mvh.moviePopularityTextView =(TextView) convertView.findViewById(R.id.movieListItemPopularityTextView);
            mvh.movieTitletextView=(TextView) convertView.findViewById(R.id.movieListItemTitleTextView);

            convertView.setTag(mvh);
        }
        MovieListViewHolder mvh=
                (MovieListViewHolder) convertView.getTag();
        Movie curMovie=movieArrayList.get(position);
        mvh.moviePopularityTextView.setText((mvh.moviePopularityTextView.getText()+
                String.valueOf(curMovie.moviePopularity)).toUpperCase());
        mvh.movieTitletextView.setText(curMovie.movieName.toUpperCase());


        return  convertView;
    }
}

package com.dhanifudin.popularmovie2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhanifudin.popularmovie2.R;
import com.dhanifudin.popularmovie2.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by dhanifudin on 7/4/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private ArrayList<Movie> movies;

    private final MovieAdapterOnClickHandler clickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_movie_list, viewGroup, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.bind(movie);
    }

    @Override
    public int getItemCount() {
        return (movies != null) ? movies.size() : 0;
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        @BindView(R.id.image_movie) ImageView movieImage;
        @BindView(R.id.text_title) TextView titleText;

        MovieViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            context = view.getContext();
            view.setOnClickListener(this);
        }

        void bind(Movie movie) {
            Picasso.with(context)
                    .load(movie.getPosterUrl())
                    .placeholder(R.drawable.placeholder)
                    .into(movieImage);
            titleText.setText(movie.getTitle());
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie movie = movies.get(position);
            clickHandler.onClick(movie);
        }
    }
}

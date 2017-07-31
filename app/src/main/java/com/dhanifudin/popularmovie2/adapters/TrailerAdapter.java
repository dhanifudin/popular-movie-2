package com.dhanifudin.popularmovie2.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dhanifudin.popularmovie2.R;
import com.dhanifudin.popularmovie2.model.Trailer;
import com.squareup.picasso.Picasso;

/**
 * Created by dhanifudin on 7/31/17.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private Trailer[] trailers;

    private final TrailerAdapterOnClickHandler clickHandler;

    public interface TrailerAdapterOnClickHandler {
        void onClick(Trailer trailer);
    }

    public TrailerAdapter(TrailerAdapterOnClickHandler clickHandler) {
        this.clickHandler = clickHandler;
    }

    public void setTrailers(Trailer[] trailers) {
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_trailer_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Trailer trailer = trailers[position];
        holder.bind(trailer);
    }

    @Override
    public int getItemCount() {
        return (trailers != null) ? trailers.length : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private Context context;
        ImageView trailerImage;
        TextView trailerName;
        Trailer trailer;

        public ViewHolder(View view) {
            super(view);
            this.context = view.getContext();
            this.trailerImage = (ImageView) view.findViewById(R.id.trailer_image);
            this.trailerName = (TextView)  view.findViewById(R.id.trailer_name_text);
            view.setOnClickListener(this);
        }

        void bind(Trailer trailer) {
            if (trailer != null) {
                this.trailer = trailer;
                Picasso.with(context)
                        .load(trailer.getThumbnailMovie())
                        .into(trailerImage);
                trailerName.setText(trailer.getName());
            } else {
                trailerImage.setVisibility(View.GONE);
                trailerName.setVisibility(View.GONE);
            }
        }

        @Override
        public void onClick(View v) {
            if (trailer != null) {
                context.startActivity(
                        new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getTrailerMovie()))
                );
            }
        }
    }
}

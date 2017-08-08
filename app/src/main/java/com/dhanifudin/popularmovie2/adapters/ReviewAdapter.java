package com.dhanifudin.popularmovie2.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dhanifudin.popularmovie2.R;
import com.dhanifudin.popularmovie2.model.Review;

import java.util.List;

/**
 * Created by dhanifudin on 7/31/17.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<Review> reviews;

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_review_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.bind(review);
    }

    @Override
    public int getItemCount() {
        return (reviews != null) ? reviews.size() : 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView authorText;
        TextView reviewText;

        public ViewHolder(View view) {
            super(view);
            this.authorText = (TextView) view.findViewById(R.id.author_text);
            this.reviewText = (TextView) view.findViewById(R.id.review_text);
        }

        void bind(Review review) {
            if (review != null) {
                this.authorText.setText(review.getAuthor());
                this.reviewText.setText(review.getContent());
            }
        }
    }
}

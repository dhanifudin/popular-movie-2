package com.dhanifudin.popularmovie2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dhanifudin on 7/4/17.
 */

public class Movie implements Parcelable {
    private String id;
    private String title;
    private String originalTitle;
    private String overview;
    private String originalLanguage;
    private String releaseDate;
    private int voteCount;
    private float voteAverage;
    private String posterPath;
    private String backdropPath;

    public Movie() {}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getPosterUrl() {
        return "http://image.tmdb.org/t/p/w185" + posterPath;
    }
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getBackdropUrl() {
        return "http://image.tmdb.org/t/p/w342" + backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.title);
        dest.writeString(this.originalTitle);
        dest.writeString(this.overview);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.releaseDate);
        dest.writeInt(this.voteCount);
        dest.writeFloat(this.voteAverage);
        dest.writeString(this.posterPath);
        dest.writeString(this.backdropPath);
    }

    protected Movie(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.originalTitle = in.readString();
        this.overview = in.readString();
        this.originalLanguage = in.readString();
        this.releaseDate = in.readString();
        this.voteCount = in.readInt();
        this.voteAverage = in.readFloat();
        this.posterPath = in.readString();
        this.backdropPath = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

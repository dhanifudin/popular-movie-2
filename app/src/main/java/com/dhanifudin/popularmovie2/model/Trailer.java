package com.dhanifudin.popularmovie2.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dhanifudin on 7/30/17.
 */

public class Trailer implements Parcelable {
    private String id;
    private String key;
    private String name;
    private String site;
    private String type;

    public Trailer() {
    }

    public Trailer(String id, String key, String name, String site, String type) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.site = site;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getThumbnailMovie() {
        return String.format("https://img.youtube.com/vi/%s/default.jpg", key);
    }

    public String getTrailerMovie() {
        return String.format("https://www.youtube.com/watch?v=%s", key);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeString(this.type);
    }

    protected Trailer(Parcel in) {
        this.id = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel source) {
            return new Trailer(source);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };
}

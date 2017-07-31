package com.dhanifudin.popularmovie2.utilities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;

import com.dhanifudin.popularmovie2.Constants;

import java.net.URL;

/**
 * Created by dhanifudin on 7/31/17.
 */

public class MovieTaskLoader extends AsyncTaskLoader<String> {
    private Bundle bundle;
    private String data;

    public MovieTaskLoader(Context context, Bundle bundle) {
        super(context);
        this.bundle = bundle;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (data != null) {
            deliverResult(data);
        } else {
            forceLoad();
        }
    }

    @Override
    public String loadInBackground() {
        String url = bundle.getString(Constants.URL);
        if (TextUtils.isEmpty(url)) return  null;

        String result = null;
        try {
            result = NetworkUtils.getResponseFromHttpUrl(new URL(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void deliverResult(String data) {
        super.deliverResult(data);
        this.data = data;
    }
}

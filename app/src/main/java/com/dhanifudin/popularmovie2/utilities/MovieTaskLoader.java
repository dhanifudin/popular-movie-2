package com.dhanifudin.popularmovie2.utilities;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;

/**
 * Created by dhanifudin on 7/31/17.
 */

public class MovieTaskLoader extends AsyncTaskLoader<JSONArray> {
    private String url;
    private JSONArray data;

    public MovieTaskLoader(Context context, String url) {
        super(context);
        this.url = url;
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
    public JSONArray loadInBackground() {
        JSONArray result = null;
        try {
            String resultString = NetworkUtils.getResponseFromHttpUrl(new URL(url));
            JSONObject resultJson = new JSONObject(resultString);
            result = resultJson.getJSONArray("results");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void deliverResult(JSONArray data) {
        super.deliverResult(data);
        this.data = data;
    }
}

package com.example.prate.planetarygeologynews;

import android.content.Context;
import android.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    private String mUrl;

    public ArticleLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
        Log.i("startLoading", "forceLoad");
    }

    @Override
    public List<Article> loadInBackground() {
        Log.i("loadInBackground", "loadInBackground");
        if (mUrl == null) {
            return null;
        }

        List<Article> articles = QueryUtils.fetchArticleData(mUrl);

        Log.i("QueryUtils", "fetchArticleData");

        return articles;

    }
}
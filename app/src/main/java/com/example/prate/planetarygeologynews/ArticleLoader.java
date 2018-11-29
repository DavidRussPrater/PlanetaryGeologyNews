package com.example.prate.planetarygeologynews;

import android.content.Context;
import android.content.AsyncTaskLoader;


import java.util.List;

public class ArticleLoader extends AsyncTaskLoader<List<Article>> {

    // Query URL
    private String mUrl;

    // Construct a new{@link ArticleLoader}
    public ArticleLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    //This is performed on a background thread
    @Override
    public List<Article> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        return QueryUtils.fetchArticleData(mUrl);


    }
}
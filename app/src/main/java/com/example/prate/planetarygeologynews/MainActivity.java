package com.example.prate.planetarygeologynews;

import android.app.LoaderManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;


public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Article>>  {

    private static final String LOG_TAG = MainActivity.class.getName();
    private static final int ARTICLE_LOADER_ID = 1;

    /**
     * URL to fetch data about planetary geology news articles from the Guardian
     */

    private static final String GUARDIAN_URL =
            "https://content.guardianapis.com/search?show-tags=contributor&section=science&page-size=15&q=NASA&api-key=9c7bd5cd-f13d-4e2d-b684-153f6f57fa01";

    private  ArticleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Create a list of words
        ArrayList<Article> articles = new ArrayList<>();

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = findViewById(R.id.list);

        // Create an {@link WordAdapter}, whose data source is a list of {@link Word}s. The
        // adapter knows how to create list items for each item in the list.
        mAdapter = new ArticleAdapter(this, new ArrayList<Article>());


        // Make the {@link ListView} use the {@link WordAdapter} we created above, so that the
        // {@link ListView} will display list items for each {@link Word} in the list.
        listView.setAdapter(mAdapter);

        /* Set a click listener on the ListView */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                /* Find the current article that was clicked on */
                Article currentArticle = mAdapter.getItem(position);

                /* Convert String URL into a URI object*/
                Uri articleUri = Uri.parse(currentArticle.getUrl());

                /* Create a new intent to view the article URI */
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, articleUri);

                /* Send the intent to launch a new activity */
                startActivity(websiteIntent);
            }
        });


        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();

        // Initialize the loader. Pass in the int ID constant defined above and pass in null for
        // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
        // because this activity implements the LoaderCallbacks interface).
        loaderManager.initLoader(ARTICLE_LOADER_ID, null, this);
        Log.i("initLoader", "Initialize Loader");

    }



    @Override
    public Loader<List<Article>> onCreateLoader(int i, Bundle bundle){
        // Create a new loader for the given URL

        Log.i("OnCreatLoader", GUARDIAN_URL);
        return new ArticleLoader(this, GUARDIAN_URL);

    }


    @Override
    public void onLoadFinished(Loader<List<Article>> loader, List<Article> articles) {
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (articles != null && !articles.isEmpty()) {
            mAdapter.addAll(articles);
        }

        Log.i("onLoadFinished", "LoadFinished");
    }

    @Override
    public void onLoaderReset(Loader<List<Article>> loader) {
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
        Log.i("onLoaderReset", "OnLoaderReset");
    }
}

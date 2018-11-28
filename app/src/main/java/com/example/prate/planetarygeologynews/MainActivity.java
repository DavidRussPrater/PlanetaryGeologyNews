package com.example.prate.planetarygeologynews;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getName();

    /**
     * URL to fetch data about planetary geology news articles from the Guardian
     */

    private static final String GUARDIAN_URL =
            "https://content.guardianapis.com/search?show-tags=contributor&section=science&page-size=15&q=Planetary%20Geology%20&api-key=9c7bd5cd-f13d-4e2d-b684-153f6f57fa01";

    private  ArticleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start AsyncTask to fetch the news articles data
        //ArticleAsyncTask
        ArticleAsyncTask task = new ArticleAsyncTask();
        task.execute(GUARDIAN_URL);

        // Create a list of words
        ArrayList<Article> articles = new ArrayList<Article>();

        // Find the {@link ListView} object in the view hierarchy of the {@link Activity}.
        // There should be a {@link ListView} with the view ID called list, which is declared in the
        // word_list.xml layout file.
        ListView listView = (ListView) findViewById(R.id.list);

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

    }


    private class ArticleAsyncTask extends AsyncTask<String, Void, List<Article>> {

        @Override
        protected List<Article> doInBackground(String... urls) {
            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Article> result = QueryUtils.fetchArticleData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Article> data) {
            // Clear the adapter of previous earthquake data
            mAdapter.clear();

            // If there is a valid list of Articles, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }

        }


    }
}

package com.example.prate.planetarygeologynews;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {


    private static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }

    /**
     * Query the Guardian dataset and return a list of Article objects.
     */
    public static List<Article> fetchArticleData(String requestUrl) {


        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response and return a list of NewsStories
        return extractFeatureFromJson(jsonResponse);

    }


    /**
     * Returns new URL object from the given String URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the article JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();


    }

    /**
     * Return a list of Article objects that has been built up from
     * parsing the given JSON response.
     */
    private static List<Article> extractFeatureFromJson(String articleJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(articleJSON)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding news stories to
        List<Article> articles = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(articleJSON);

            //Create the JSONObject with the key "response"
            JSONObject responseJSONObject = baseJsonResponse.getJSONObject("response");

            // Extract the JSONArray associated with the key called "results",
            // which represents a list of news stories.
            JSONArray articleArray = responseJSONObject.getJSONArray("results");

            // For each article in the articleArray, create an Article object
            for (int i = 0; i < articleArray.length(); i++) {

                // Get a single article at position i within the list of news stories
                JSONObject currentArticle = articleArray.getJSONObject(i);

                // Extract the value for the key called "webTitle"
                String articleTitle = currentArticle.getString("webTitle");

                //Extract the JSONArray with the key "tag"
                JSONArray tagsArray = currentArticle.getJSONArray("tags");

                //Declare String variable to hold author name
                String author = null;

                // Extract the JSONArray associated with the key called "tags",
                // and set the name of the author to "webTitle"
                if (tagsArray.length() > 0) {
                    JSONObject contributorTag = (JSONObject) tagsArray.get(0);
                    author = contributorTag.getString("webTitle");

                }

                // Extract the value for the key called "webPublicationDate"
                String unformattedDate = currentArticle.getString("webPublicationDate");
                String date = unformattedDate.substring(0, 10);

                // Extract the value for the key called "sectionName"
                String section = currentArticle.getString("sectionName");

                // Extract the value for the key called "url"
                String url = currentArticle.getString("webUrl");

                // Create a new Article object with the title, section name, date,
                // and url from the JSON response.
                Article article = new Article(articleTitle, author, date, section, url);

                // Add the new Article to the list of article.
                articles.add(article);
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the article JSON results", e);
        }

        // Return the list of articles
        return articles;
    }


}

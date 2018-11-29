package com.example.prate.planetarygeologynews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<Article> {


    public ArticleAdapter(Context context, ArrayList<Article> articles) {
        super(context, 0, articles);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);

        }

        // Get the {@link Tour} oject located at this position in the list
        Article currentArticle = getItem(position);

        // Set the title of the articles text to the articleTextView
        TextView articleTitleTextView = listItemView.findViewById(R.id.article_title_text_view);
        articleTitleTextView.setText(currentArticle.getArticleTitle());
        articleTitleTextView.setSelected(true);

        //Set the author of the article to the authorTextView
        TextView authorTextView = listItemView.findViewById(R.id.author_text_view);
        authorTextView.setText(currentArticle.getAuthor());
        // If the authorTextView is empty set it to "No Author Referenced"
        if (authorTextView.getText().toString().matches("")) {
            authorTextView.setText(R.string.no_author);
        }

        // Set the date of publication to the dateTextView
        TextView dateTextView = listItemView.findViewById(R.id.date_text_view);
        dateTextView.setText(currentArticle.getDate());

        // Set the articles section to the sectionTextView
        TextView sectionTextView = listItemView.findViewById(R.id.section_text_view);
        sectionTextView.setText(currentArticle.getSection());

        // Return the whole list item layout (containing 2 TextViews and an ImageView) so that it can be shown in
        // the ListView.
        return listItemView;

    }


}

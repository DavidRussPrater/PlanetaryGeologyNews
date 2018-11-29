package com.example.prate.planetarygeologynews;


/**
 * An {@link Article} object contains information related to a single news article.
 */
public class Article {

    // News article title
    private String mArticleTitle;

    // News article author
    private String mAuthor;

    // News article date
    private String mDate;

    // News article section
    private String mSection;

    // News article URL
    private String mUrl;

    /**
     * Constructs a new {@link Article} object
     *
     * @param articleTitle is the articles title
     * @param author is the articles author
     * @param date is the articles  date
     * @param section is the articles section
     * @param url is the URL associated with Guardian website
     */
    public Article(String articleTitle, String author, String date, String section, String url){

        mArticleTitle = articleTitle;
        mAuthor = author;
        mDate = date;
        mSection = section;
        mUrl = url;

    }

    // Returns the articles title
    public String getArticleTitle(){
        return mArticleTitle;
    }

    // Returns the articles author
    public String getAuthor(){
        return mAuthor;
    }

    // Returns the articles date
    public String getDate (){
        return mDate;
    }

    // Returns the articles section
    public String getSection(){
        return mSection;
    }

    // Returns the articles Guardian URL
    public String getUrl() {
        return mUrl;
    }

}

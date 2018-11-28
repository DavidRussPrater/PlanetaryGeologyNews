package com.example.prate.planetarygeologynews;

public class Article {

    private String mArticleTitle;

    private String mAuthor;

    private String mDate;

    private String mSection;

    private String mUrl;

    public Article(String articleTitle, String author, String date, String section, String url){

        mArticleTitle = articleTitle;
        mAuthor = author;
        mDate = date;
        mSection = section;
        mUrl = url;

    }

    public String getArticleTitle(){
        return mArticleTitle;
    }

    public String getAuthor(){
        return mAuthor;
    }

    public String getDate (){
        return mDate;
    }

    public String getSection(){
        return mSection;
    }

    public String getUrl() {
        return mUrl;
    }

}

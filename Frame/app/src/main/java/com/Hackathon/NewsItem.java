package com.Hackathon;

public class NewsItem {
    private String newsID;
    private String newsTitle;
    private String newsContent;
    private String newsUrl;
    private String newsDate;

    public NewsItem(String newsID, String newsTitle,
                    String newsContent, String newsUrl, String newsDate) {
        this.newsID = newsID;
        this.newsTitle = newsTitle;
        this.newsUrl = newsUrl;
        this.newsDate = newsDate;
        this.newsContent = newsContent;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public String getNewsContent() {
        return newsContent;
    }

    public String getNewsID() {
        return newsID;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public void setNewsID(String newsID) {
        this.newsID = newsID;
    }

    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }
}


package pers.lyrichu.projects.Crawler.util;


public class HotQuery {
    private String text; // hotquery text
    private String url; // hotquery url
    private String tag; // hotquery tag
    private int viewCount; // query view count
    private int rank; // query rank

    public HotQuery() { }

    public HotQuery(String text,String url,String tag,int viewCount,int rank) {
        this.text = text;
        this.url = url;
        this.tag = tag;
        this.viewCount = viewCount;
        this.rank = rank;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}




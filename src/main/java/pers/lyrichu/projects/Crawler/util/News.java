package pers.lyrichu.projects.Crawler.util;

public class News {
    private String title; // 新闻标题
    private String imgurl; // 新闻图片url
    private String url; // 新闻链接
    private String type; // 新闻类型
    private String tag; // 新闻标签
    private String source; // 新闻来源
    private int rank; // 新闻来源

    public News() {}

    public News(String title,String imgurl,String url,String type,String tag,String source,int rank) {
        setTitle(title);
        setImgurl(imgurl);
        setUrl(url);
        setType(type);
        setTag(tag);
        setSource(source);
        setRank(rank);
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}

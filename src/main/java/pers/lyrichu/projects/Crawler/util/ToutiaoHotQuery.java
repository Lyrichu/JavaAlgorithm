package pers.lyrichu.projects.Crawler.util;

public class ToutiaoHotQuery extends HotQuery {
    public ToutiaoHotQuery(String text,String url,String tag,int rank) {
        super.setText(text);
        super.setUrl(url);
        super.setTag(tag);
        super.setRank(rank);
    }
}

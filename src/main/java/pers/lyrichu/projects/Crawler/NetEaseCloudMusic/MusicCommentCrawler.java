package pers.lyrichu.projects.Crawler.NetEaseCloudMusic;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 抓取网易云音乐热门评论
 * reference:https://www.cnblogs.com/LexMoon/p/javaWy.html
 */
public class MusicCommentCrawler {
    private static Logger LOGGER = LoggerFactory.getLogger(MusicCommentCrawler.class);

    private static final String MUSIC_COMMENT_BASE_URL =  "http://music.163.com/weapi/v1/resource/comments/R_SO_4_409649818?csrf_token=";
    private static final String HEADER = "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/60.0.3112.101 Safari/537.36";
    private static final String PARAMS =
            "RlBC7U1bfy/boPwg9ag7/a7AjkQOgsIfd+vsUjoMY2tyQCPFgnNoxHeCY+ZuHYqtM1zF8DWIBwJWbsCOQ6ZYxBiPE3bk+CI1U6Htoc4P9REBePlaiuzU4M3rDAxtMfNN3y0eimeq3LVo28UoarXs2VMWkCqoTXSi5zgKEKbxB7CmlBJAP9pn1aC+e3+VOTr0";
    private static final String ENC_SEC_KEY =
            "76a0d8ff9f6914d4f59be6b3e1f5d1fc3998317195464f00ee704149bc6672c587cd4a37471e3a777cb283a971d6b9205ce4a7187e682bdaefc0f225fb9ed1319f612243096823ddec88b6d6ea18f3fec883d2489d5a1d81cb5dbd0602981e" +
                    "7b49db5543b3d9edb48950e113f3627db3ac61cbc71d811889d68ff95d0eba04e9";



    public static void main(String[] args) {
        List<String> commentList = getMusicCommentList();
        System.out.println(commentList.size());
        for (String comment:commentList) {
            System.out.println(comment);
        }
    }



    private static List<String> getMusicCommentList() {
        List<String> resList = new ArrayList<>();
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(MUSIC_COMMENT_BASE_URL);
            post.setHeader("User-Agent",HEADER);
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("params",PARAMS));
            nameValuePairs.add(new BasicNameValuePair("encSecKey",ENC_SEC_KEY));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String entityStr = EntityUtils.toString(httpEntity,"utf-8");
            System.out.println(entityStr);
            // 正则匹配提取comment
            String p = "content(.*?)\"}";
            Pattern pattern = Pattern.compile(p);
            Matcher matcher = pattern.matcher(entityStr);
            while (matcher.find()) {
                String comment = matcher.group();
                if (!resList.contains(comment)) {
                    resList.add(comment);
                }
            }
        } catch (Exception e) {
            LOGGER.error("getMusicCommentList error:{}",e);
        }
        return resList;
    }
}
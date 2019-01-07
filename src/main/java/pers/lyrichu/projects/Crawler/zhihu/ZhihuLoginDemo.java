package pers.lyrichu.projects.Crawler.zhihu;

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

/*
 * 模拟登录知乎,demo
 */
public class ZhihuLoginDemo {
    private static Logger LOGGER = LoggerFactory.getLogger(ZhihuLoginDemo.class);
    private static final String ZHIHU_LOGIN_BY_PHONE_URL = "https://www.zhihu.com/login/phone_num";
    private static final String PHONE_NUMBER = "15890956765";
    private static final String PASSWD = "1325200471";
    public static void main(String[] args) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost post = new HttpPost(ZHIHU_LOGIN_BY_PHONE_URL);
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(
                    new BasicNameValuePair("_xsrf",
                            "66653239623962342d396237632d346233332d396331362d333434386438326438616139")
            );
            nameValuePairs.add(new BasicNameValuePair("phone_num",PHONE_NUMBER));
            nameValuePairs.add(new BasicNameValuePair("password",PASSWD));
            nameValuePairs.add(new BasicNameValuePair("captcha_type", "cn"));
            post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            CloseableHttpResponse response = httpClient.execute(post);
            HttpEntity httpEntity = response.getEntity();
            String s = EntityUtils.toString(httpEntity);
            System.out.println(s);
        } catch (Exception e) {
            LOGGER.error("ZhihuLoginDemo error:{}",e);
        }

    }
}
package pers.lyrichu.projects.Crawler.MyOwnCrawler.page;


import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*
 * 发送请求,返回响应,并把响应封装成Page 类
 */
public class RequestAndResponseTool {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestAndResponseTool.class);
    private static final int MAX_TIMEOUT = 5000; // 最大超时(ms)

    public static Page sendRequestAndGetResponse(String url) {
        Page page = null;
        HttpClient httpClient = new HttpClient();
        // 设置超时
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(MAX_TIMEOUT);
        // 生成get请求
        GetMethod getMethod = new GetMethod(url);
        // 设置get请求超时
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT,MAX_TIMEOUT);
        // 设置请求重试处理
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,new DefaultHttpMethodRetryHandler());
        // 执行get 请求
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            // 判断状态码
            if (statusCode != HttpStatus.SC_OK) {
                LOGGER.error("Execute method failed:{}",getMethod.getStatusLine());
            }
            // 处理http response
            byte[] responseBody = getMethod.getResponseBody();
            // 得到当前的返回类型
            String contentType = getMethod.getResponseHeader("Content-Type").getValue();
            // 封装成Page
            page = new Page(responseBody,url,contentType);
        } catch (Exception e) {
            LOGGER.error("sendRequestAndGetResponse error:{}",e);
        } finally {
            // 释放http 连接
            getMethod.releaseConnection();
        }
        return page;
    }
}
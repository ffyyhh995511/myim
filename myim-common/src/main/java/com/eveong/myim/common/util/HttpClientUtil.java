package com.eveong.myim.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * http 工具类
 *
 * @version 1.0
 * @author:fangyunhe
 * @time:2018年4月9日 下午8:11:45
 */
@Slf4j
public class HttpClientUtil {

    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(15000).setConnectTimeout(15000)
            .setConnectionRequestTimeout(15000).build();


    /**
     * 发送 post请求
     *
     * @param httpUrl
     * @return
     * @author:fangyunhe
     * @time:2018年4月9日 下午8:12:44
     */
    public static String sendHttpPost(String httpUrl) {
        // 创建httpPost
        HttpPost httpPost = new HttpPost(httpUrl);
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 posts请求 参数(格式:key1=value1&key2=value2)
     *
     * @param httpUrl
     * @param params
     * @return
     * @author:fangyunhe
     * @time:2018年4月9日 下午8:13:33
     */
    public static String sendHttpPost(String httpUrl, String params) {
        HttpPost httpPost = new HttpPost(httpUrl);
        // 设置参数
        StringEntity stringEntity = new StringEntity(params, "UTF-8");
        stringEntity.setContentType("application/x-www-form-urlencoded");
        httpPost.setEntity(stringEntity);
        return sendHttpPost(httpPost);
    }

    /**
     * 发送 post请求
     *
     * @param httpUrl
     * @param maps
     * @return
     * @throws UnsupportedEncodingException
     * @author:fangyunhe
     * @time:2018年4月9日 下午8:13:59
     */
    public static String sendHttpPost(String httpUrl, Map<String, String> maps) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(httpUrl);
        // 创建参数队列
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        Set<Map.Entry<String, String>> entries = maps.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
        return sendHttpPost(httpPost);
    }

    /**
     * 发送Post请求
     *
     * @param httpPost
     * @return
     * @author:fangyunhe
     * @time:2018年4月9日 下午8:14:17
     */
    public static String sendHttpPost(HttpPost httpPost) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpPost.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpPost);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return responseContent;
    }

    /**
     * 发送 get请求
     *
     * @param httpUrl
     * @return
     * @author:fangyunhe
     * @time:2018年4月9日 下午8:14:45
     */
    public static String sendHttpGet(String httpUrl) {
        HttpGet httpGet = new HttpGet(httpUrl);
        return sendHttpGet(httpGet);
    }

    /**
     * 发送Get请求
     *
     * @param httpGet
     * @return
     * @author:fangyunhe
     * @time:2018年4月9日 下午8:15:03
     */
    public static String sendHttpGet(HttpGet httpGet) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            // 创建默认的httpClient实例.
            httpClient = HttpClients.createDefault();
            httpGet.setConfig(requestConfig);
            // 执行请求
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                // 关闭连接,释放资源
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
        return responseContent;
    }

}
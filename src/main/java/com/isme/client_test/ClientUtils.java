package com.isme.client_test;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.swing.text.AbstractDocument;
import java.io.IOException;
import java.util.List;

/**
 * 模拟登陆
 * Created by linjunjie on 2015/12/2 (linjunjie@raycloud.com).
 */
public class ClientUtils {

    private static CloseableHttpClient httpClient = HttpClients.createDefault();
    private static HttpClientContext httpClientContext = new HttpClientContext();
    public static String sendGet(String url){
        CloseableHttpResponse response = null;
        String content = null;
        HttpGet httpGet = new HttpGet(url);
        try {
            response = httpClient.execute(httpGet,httpClientContext);
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content;
    }

    public static String sendPost(String url, List<NameValuePair> nvps) {
        CloseableHttpResponse response = null;
        String content = null;
        try {
            //　HttpClient中的post请求包装类
            HttpPost post = new HttpPost(url);
            // nvps是包装请求参数的list
            if (nvps != null) {
                post.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            }
            // 执行请求用execute方法，content用来帮我们附带上额外信息
            response = httpClient.execute(post, httpClientContext);
            System.out.println(response.toString());
            String set_cookie = response.getFirstHeader("Set-Cookie").getValue();
            System.out.println(set_cookie);
            // 得到相应实体、包括响应头以及相应内容
            HttpEntity entity = response.getEntity();
            // 得到response的内容
            content = EntityUtils.toString(entity);
            //　关闭输入流
            EntityUtils.consume(entity);
            return set_cookie.substring(0,set_cookie.indexOf(";"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content;
    }
}

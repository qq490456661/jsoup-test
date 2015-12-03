package com.isme.client_test;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 模拟登陆"最代码"网站
 * 因为这个网站不需要输入验证码就可以登陆，借此来测试一下。
 * 返回 登陆的set_Cookie信息
 * Created by linjunjie on 2015/12/2 (linjunjie@raycloud.com).
 */
public class Client {


    public static void main(String args[]){
        String target = "http://www.zuidaima.com/user/login.htm";
//        Connection conn = Jsoup.connect(target);
//        conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
//
//        String content = com.isme.client_test.ClientUtils.sendGet(target);
//        Document document = Jsoup.parse(content);
//        Element pwd = document.getElementById("account");
//        Element account = document.getElementById("password");

        //这里是设置表单中用户名的id和密码的id
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("account","490456661@qq.com"));
        nvps.add(new BasicNameValuePair("password","qqlinjunjie"));
        /**
         *  target : 有登陆form的页面地址
         *  nvps : 账号、密码表单id, 不同网站还需要传其他的东西 ,有验证码的还得更复杂
         *  set_Cookie : [返回] 有cookie信息，说明我们登陆成功。
         */
        String set_Cookie = ClientUtils.sendPost(target,nvps);
        /**
         *  以下是访问需要登陆权限的其他页面，我们需要把上面获得的cookie绑定在请求头。
         */
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet("http://www.zuidaima.com/user/mention.htm");
        httpGet.setHeader("Cookie",set_Cookie);
        try {
            HttpResponse resp = httpClient.execute(httpGet);
            System.out.println(resp);
            HttpEntity entity = resp.getEntity();
            System.out.println(EntityUtils.toString(entity));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

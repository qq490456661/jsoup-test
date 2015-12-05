package com.isme.train;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 模拟登陆
 * Created by linjunjie on 2015/12/4 (linjunjie@raycloud.com).
 */
public class Client {

    public static void main(String []args) throws Exception{
        //dolphin.3.141592.in
        //String server = "dolphin.3.141592.in";
        //port 20498
        //int port = 20498;
        //scheme https
        //String scheme = "http";
        //获取设置了接受任意证书的HttpClient（封装）
        HttpClient httpclient = HttpClientHelper.getHttpClient();
        //请求地址
        HttpGet httpGet= new HttpGet("https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand&"+Math.random());
        //模拟浏览器去爬，不然会被封IP
        httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:42.0) Gecko/20100101 Firefox/42.0");
        httpGet.setHeader("Accept-Charset", "gzip, deflate");
        //httpGet.setHeader("Content-Type","text/html;charset=utf-8");
        //post打的时候加下面这句
        //httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
        //httpGet.setHeader("Date",new Date().toString());
        //httpGet.setHeader("Server","Apache-Coyote/1.1");
        //httpGet.setHeader("Set-Cookie","JSESSIONID=0A01D7316092EC90DBAE477F4F8A54C74AA0D205D9; Path=/otnBIGipServerotn=836174090.24610.0000; path=/");
        httpGet.setHeader("Host","kyfw.12306.cn");
        //httpGet.setHeader("Keep-Alive", "300");
        httpGet.setHeader("Connection", "Keep-Alive");
        //httpGet.setHeader("Cache-Control", "no-cache");
        httpGet.setHeader("Referer", "https://kyfw.12306.cn/otn/login/init");

        HttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        //取出entity内容
        InputStream is = entity.getContent();
        //写入文件
        OutputStream os = new FileOutputStream(new File("D:\\111.jpg"));
        byte [] bytes = new byte[1024*1024];
        int len = 0;
        while((len = is.read(bytes))>0){
            os.write(bytes,0,len);
        }
        //结束
    }


}





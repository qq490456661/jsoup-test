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
        String server = "dolphin.3.141592.in";
        //port 20498
        int port = 20498;
        //scheme https
        String scheme = "http";
        //获取设置了接受任意证书的HttpClient（封装）
        HttpClient httpclient = HttpClientHelper.getHttpClient();
        //请求地址
        HttpGet httpGet= new HttpGet("https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand&"+Math.random());
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





package com.isme.train;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

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
        //HttpGet httpGet= new HttpGet("https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand&"+Math.random());
        HttpGet httpGet= new HttpGet("https://kyfw.12306.cn/otn/confirmPassenger/initDc");
        //模拟浏览器去爬，不然会被封IP
        httpGet.setHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        //httpGet.setHeader("Accept-Encoding", "gzip, deflate, sdch");
        httpGet.setHeader("Accept-Charset", "gzip, deflate");
        httpGet.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");
        httpGet.setHeader("Connection", "Keep-Alive");
        //--- 只用改动cookie ---//
        httpGet.setHeader("Cookie","__NRF=AAA1127483C7CD9078DA49DDF65D8C01; JSESSIONID=0A01D49B98A11C4C8E972B24555519518D6E917658; BIGipServerotn=2614362378.38945.0000; current_captcha_type=Z");
        //--- 只用改动cookie---//
        httpGet.setHeader("Referer", "https://kyfw.12306.cn/otn/login/init");
        httpGet.setHeader("Host","kyfw.12306.cn");
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
        httpGet.setHeader("Upgrade-Insecure-Requests", "1");
        //httpGet.setHeader("Content-Type","text/html;charset=utf-8");
        //post打的时候加下面这句
        //httpGet.setHeader("Content-Type", "application/x-www-form-urlencoded");
        //httpGet.setHeader("Date",new Date().toString());
        //httpGet.setHeader("Server","Apache-Coyote/1.1");
        //httpGet.setHeader("Set-Cookie","JSESSIONID=0A01D733FCE6D8D797FE3DC190C5FA313613E69FE4; __NRF=0DFBDC0E5910C3DDAB832CCB72CAF628; _jc_save_fromStation=%u6E58%u6F6D%2CXTQ; _jc_save_toStation=%u676D%u5DDE%2CHZH; _jc_save_fromDate=2015-11-12; _jc_save_wfdc_flag=dc; BIGipServerotn=869728522.64545.0000; current_captcha_type=Z");
        //httpGet.setHeader("Keep-Alive", "300");
        //httpGet.setHeader("Cache-Control", "no-cache");

        HttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        Document document = Jsoup.parse(entity.getContent(),"UTF-8","https://kyfw.12306.cn/");
        //处理a标签
        Elements hrefs = document.select("[href]");
        for(Element e : hrefs){
            e.attr("href",e.attr("abs:href"));
            System.out.println("处理后："+e);
        }
        Elements srcs = document.select("[src]");
        for(Element e : srcs){
            e.attr("src",e.attr("abs:src"));
            System.out.println("处理后："+e);
        }

        //System.out.println(document.html());
        //System.out.println(EntityUtils.toString(entity));
        //取出entity内容
//        InputStream is = entity.getContent();
//        //写入文件
          OutputStream os = new FileOutputStream(new File("D:\\111.html"));
//        byte [] bytes = new byte[1024*1024];
//        int len = 0;
//        while((len = is.read(bytes))>0){
//            os.write(bytes,0,len);
//        }

        byte[] bes = document.html().getBytes();
        os.write(bes);
        os.close();
        //结束
    }


}





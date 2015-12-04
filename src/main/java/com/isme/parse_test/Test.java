package com.isme.parse_test;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


/**
 * jsoup抓取Zero动漫网的分页（第11页）
 * 返回动漫的json信息
 * response:[
 *      {
 *          "title" : "" ,//动漫名字
 *          "type"  : "" ,//分类
 *          "clickNum"  : "" //点击热度
 *      },
 *      {...}
 * ]
 * Created by linjunjie on 2015/11/26 (linjunjie@raycloud.com).
 */
public class Test {

    public static void main(String[]args){
        String target = "http://www.izero.tv/list/kehuan/11";
        Document document = null;
        try {
            //建立连接
            document = Jsoup.connect(target)
                    .timeout(30000)
                    .get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 容器
        String str = "";
        //选择<div class="oh">的内容
        Elements elements = document.body().select("div.oh");
        System.out.println("数量" + elements.size());
        if(elements.size()==0){
            return ; //结束爬取
        }
        // 获取<head>中内容
        String temp = document.head().toString();
        try {
            str += temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        //截取<meta>中编码
        int st = temp.indexOf("charset=");
        st+=8;
        int et = temp.indexOf("\"", st);
        String encode = temp.substring(st,et);
        System.out.println(encode);

        //需要对链接进行处理
        Elements a_links = elements.select("a");
        for(Element e : a_links){
            e.attr("href",e.attr("abs:href"));
        }
        //拼接成json
        StringBuilder json = new StringBuilder("[");
        Elements li_nodes = elements.select("ul").get(0).select("li");
        String[] dataType = new String[]{"","title","type","clickNum"};
        if(li_nodes!=null){
            for(int k=0;k<li_nodes.size();k++){
                Element e = li_nodes.get(k);
                json.append("{");
                Elements es = e.children();
                e.children();
                if(es!=null){
                    for(int i=1;i<es.size();i++){
                        String t = es.get(i).text();
                        String[] srr = t.split("[:：]");
                        if(srr.length>1){
                            t = srr[1];
                        }else{
                            t = srr[0];
                        }
                        json.append("\""+dataType[i]+"\":\""+t+"\"");
                        if(i<es.size()-1){
                            json.append(",");
                        }
                    }
                    json.append("}");
                    if(k<li_nodes.size()-1){
                        json.append(",\r\n");
                    }
                }
            }
        }
        json.append("]");
        System.out.println(json);
        str += elements.toString();
        //输出到文件中
        try {
            OutputStream os = new FileOutputStream(new File("D://1.html"));
            os.write(str.getBytes(encode));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

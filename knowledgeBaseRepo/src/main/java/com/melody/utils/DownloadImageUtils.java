package com.melody.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * Created by xiaobai on 16-8-10.
 */
public class DownloadImageUtils {

    public static String downloadImageFromHtml(String html) {
        Properties pron = new Properties();
        try {
            pron.load(JSOUPUtils.class.getResourceAsStream("/info.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String imagePath = pron.getProperty("imageSavepath");
        String requestRootPath = pron.getProperty("RequestRootPath");
        Document doc = Jsoup.parse(html);
        //没有标签的时候使用img选择
        Elements imgElements = doc.select(".small img").size() == 0 ? doc.select("img") : doc.select(".small img");
        //还没有说明文章中不含有图片  直接返回数据
        if (imgElements.size() == 0) {
            return html;
        }
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String today = format.format(date);
        String sourceDir = imagePath + "/" + today + "/";
        String replacePath=requestRootPath+"/upload/image/"+today+"/";
        List<String> imageUrls = new ArrayList<String>();
        for (Element e : imgElements) {
            if (e.attr("src").contains("http") && !e.attr("src").contains("rar")) {
                imageUrls.add(e.attr("src"));
            }
        }
        wgetImg(imageUrls, sourceDir);
        html = html.replaceAll("http\\:\\/\\/webdoc\\.lenovo\\.com\\.cn\\/lenovowsi\\/new_cskb\\/uploadfile\\/", replacePath);
        return html;
    }

    public static void wgetImg(List<String> urls, String path) {
        Runtime run = Runtime.getRuntime();
        for (String url : urls) {
            try {
                run.exec("wget -nc -P " + path + " " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        System.out.println(format.format(date));
    }

}

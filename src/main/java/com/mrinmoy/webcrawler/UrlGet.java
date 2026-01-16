package com.mrinmoy.webcrawler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class UrlGet {
    public Set<String> fetchLinks(String targetUrl){
        Set<String> urls = new HashSet<String>();
        Document htmlPage = null;
        try{
            htmlPage = Jsoup.connect(targetUrl).timeout(10000).get();
        }catch(IOException e){
            System.out.println("Error");
            throw new RuntimeException(e);
        }
        Elements anchorTags = htmlPage.select("a[href]");
        for(Element link : anchorTags){
            String url = link.absUrl("href");
            if(!url.isEmpty()){
                try{
                    new URL(url);
                    urls.add(url);
                }catch(Exception e){
                    System.out.println("Invalid Url : "+url);
                }
            }
        }
        return urls;
    }
}

package com.mrinmoy.webcrawler;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;


public class UrlStore {
    private ConcurrentHashMap<String,Boolean> urlMap;
    public UrlStore(){
        urlMap = new ConcurrentHashMap<String,Boolean>();
    }
    public boolean addUrl(String url){
        return urlMap.putIfAbsent(url, true) == null;
    }
    public ArrayList<String> getAllUrls(){
        ArrayList<String> urls = new ArrayList<String>();
        urls.addAll(urlMap.keySet());
        return urls;
    }
}

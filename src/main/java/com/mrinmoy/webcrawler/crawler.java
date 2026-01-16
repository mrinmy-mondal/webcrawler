package com.mrinmoy.webcrawler;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.Phaser;

public class crawler implements Runnable{
    private final UrlStore urlStore;
    private final UrlGet urlGet;
    private final Phaser phaser;
    private final String url;
    private final URL urlAddr;
    crawler(UrlStore urlStore, UrlGet urlGet, Phaser phaser,String url) throws MalformedURLException {
        this.urlStore = urlStore;
        this.urlGet = urlGet;
        this.phaser = phaser;
        this.url = url;
        this.urlAddr = new URL(url);
    }
    @Override
    public void run(){
        try{
            if(this.url == null || this.url.isEmpty())return;
            System.out.println(this.url);
            Set<String> urls = urlGet.fetchLinks(url);
            for(String link : urls){
                URL newUrl = new URL(link);
                if(this.urlAddr.getHost().equals(newUrl.getHost()) && urlStore.addUrl(link)){
                    phaser.register();
                    WebCrawler.SubmitTask(this.urlStore,phaser,link);
                }
            }
        }catch(Exception e){
            System.out.println("Error Occured");
        }finally{
            phaser.arriveAndDeregister();
        }
    }
}

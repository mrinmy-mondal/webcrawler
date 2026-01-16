package com.mrinmoy.webcrawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;

public class WebCrawler {
    private static ExecutorService threadPool;
    public static void main(String[] args){
        int MAX_THREADS = 5;
        String url = "";
        if(args.length == 0){
            System.out.println("Error : no inputs defined");
            return;
        }
        url = args[0];
        if(args.length == 2)MAX_THREADS = Integer.parseInt(args[1]);

        System.out.println("Inital Url : " + url);
        try{
            Phaser p = new Phaser(1);
            threadPool = Executors.newFixedThreadPool(MAX_THREADS);
            UrlStore s = new UrlStore();
            s.addUrl(url);
            SubmitTask(s,p,url);
            p.awaitAdvance(p.getPhase());
        }catch (Exception e){
            System.out.println("Error Occured");
        }
        threadPool.shutdown();
    }
    public static void SubmitTask(UrlStore s, Phaser p,String link){
        try{
            threadPool.submit(new crawler(s,new UrlGet(),p,link));
        }catch(Exception e){
            System.out.println("Error Occured");
        }
    }

}

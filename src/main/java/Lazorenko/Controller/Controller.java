package Lazorenko.Controller;

import Lazorenko.Logger.LoggerToFile;
import Lazorenko.Model.Model;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.TreeSet;

/**
 * Created by andriylazorenko on 30.06.15.
 */
public class Controller {
    LoggerToFile loggerToFile = LoggerToFile.getInstance();
    private static final String att = "rel";
    private String uri =null;
    private String dest=null;
    private String choice=null;
    public static final String destinationByDefault = "/home/andriylazorenko/Music";
    BufferedReader br;

    public Controller() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void run(){
        getData();
        download();
    }

    //Asking for URL for download
    public void getURL(){
        System.out.println("Insert an URL: ");
        try {
            uri = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            loggerToFile.getLogger().error(e.getMessage());
        }
    }

    //Asking for path of download
    public void getPath(){
        System.out.println("Insert download path. Press 'd' for default path");
        try {
            dest = br.readLine();
            if (dest.equals("d")||dest.equals("D")){
                dest=destinationByDefault;
            }
            //May want to add additional destination checks later on
        } catch (IOException e) {
            e.printStackTrace();
            loggerToFile.getLogger().error(e.getMessage());
        }
    }

    //Asking for format of downloaded content - loaded from prop file. Easily edited.
    public void getFormat(){
        System.out.println("Select format of downloaded data:");
        Model model = Model.getInstance();
        for (Object o : new TreeSet<>(model.getProperties().keySet())) {
            System.out.println(o.toString()+". "+model.getProperties().getProperty(o.toString()));
        }
        try {
            int choice = Integer.parseInt(br.readLine());
            this.choice = model.getProperties().getProperty(Integer.toString(choice));
        } catch (IOException e) {
            e.printStackTrace();
            loggerToFile.getLogger().error(e.getMessage());
            getFormat();
        }

    }
    public void getData(){
        getURL();
        getPath();
        getFormat();
    }

    public void download(){
        Document document = null;
        try {
            document = Jsoup.connect(uri).get();
        } catch (IOException e) {
            loggerToFile.getLogger().error(e.getMessage());
            e.printStackTrace();
        } catch (IllegalArgumentException e){
            loggerToFile.getLogger().error(e.getMessage());
            e.printStackTrace();
            //TODO
            //If incorrect URL - return on previous step.
        }
        Elements elements = document.getElementsByAttribute(att);

        for (Element e : elements) {
            if (e.text().contains(choice)) {
                String downloadURL = uri.substring(0, uri.lastIndexOf("/"));
                downloadURL = downloadURL + e.attr("href");
                downloadURL = downloadURL.replace("get", "load");
                String finalDestination = dest + "/" + e.text();
                System.out.println(downloadURL);
                downloadOne(downloadURL, finalDestination);
            }
        }
    }

    public void downloadOne(String url, String dest){
        try (OutputStream os = new FileOutputStream(dest)){
            URL downloadUrl = new URI(url).toURL();
            InputStream is = downloadUrl.openStream();

            byte[] buff = new byte[1000000];
            int count = 0;
            while((count = is.read(buff)) != -1){
                os.write(buff,0,count);
                os.flush();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            loggerToFile.getLogger().error(e.getMessage());
        } catch (URISyntaxException e) {
            e.printStackTrace();
            loggerToFile.getLogger().error(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            loggerToFile.getLogger().error(e.getMessage());
        }

    }

    //Method for logic still under construction
//    public static String parsePage(String url) throws URISyntaxException, IOException {
//        URL uri = new URI(url).toURL();
//        InputStream is = uri.openStream();
//        BufferedReader br1 = new BufferedReader(new InputStreamReader(is));
//        String forRet = null;
//        String buff = br1.readLine();
//        while (!buff.equals(null)) {
//            forRet += buff;
//        }
//        return forRet;
//    }
}

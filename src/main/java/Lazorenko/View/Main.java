package Lazorenko.View;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Master on 23-May-15.
 */
public class Main {
    public static final String destination = "/home/andriylazorenko/music";
    public static final String att = "rel";
    public static void main(String[] args) throws IOException, URISyntaxException, ParserConfigurationException, SAXException {
        System.out.println("Insert an URL: ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String URL = br.readLine();
        Document document = Jsoup.connect(URL).get();
        Elements elements = document.getElementsByAttribute(att);

        for (Element e : elements) {
            if (e.text().contains(".mp3")) {
                String downloadURL = URL.substring(0, URL.lastIndexOf("/"));
                downloadURL = downloadURL + e.attr("href");
                downloadURL = downloadURL.replace("get", "load");
                String finalDestination = destination + "\\" + e.text();
                System.out.println(downloadURL);
                download(downloadURL, finalDestination);
            }
        }
    }


        public static void download(String url, String dest){
            try (OutputStream os = new FileOutputStream(dest)){

                URL uri = new URI(url).toURL();
                InputStream is = uri.openStream();

                byte[] buff = new byte[1000000];
                int count = 0;
                while((count = is.read(buff)) != -1){
                    os.write(buff,0,count);
                    os.flush();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    public static String parsePage(String url) throws URISyntaxException, IOException {
        URL uri = new URI(url).toURL();
        InputStream is = uri.openStream();
        BufferedReader br1 = new BufferedReader(new InputStreamReader(is));
        String forRet = null;
        String buff = br1.readLine();
        while (!buff.equals(null)) {
            forRet += buff;
        }
        return forRet;
    }
}



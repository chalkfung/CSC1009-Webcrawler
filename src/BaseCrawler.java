import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class BaseCrawler {
    private HashSet<String> links;

    public BaseCrawler()
    {
        this.links = new HashSet<String>();
    }

    public void getPageLinks(String url)
    {
        if(!links.contains(url))
        {
            try
            {
                if(links.add(url))
                    System.out.println(url);
                Document document = Jsoup.connect(url).get();
                Elements linksOnPage = document.select("a[href]");
                for (Element page : linksOnPage)
                    getPageLinks(page.attr("abs:href"));

            }catch (IOException e) {
                System.err.println("For '" + url + "': " + e.getMessage());
            }
        }
    }

    public static void main(String [] args)
    {
        BaseCrawler tmp = new BaseCrawler();
        tmp.getPageLinks("https://sites.google.com/view/loopohkok/home");
    }
}

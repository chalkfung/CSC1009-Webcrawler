package stonks_intellect;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element ;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.HashSet;

import stonks_intellect.core.IBaseCrawler;

/**
 * BaseCrawler
 */
public class BaseCrawler implements IBaseCrawler
{
    private HashSet<String> links;

    public BaseCrawler()
    {
        this.links = new HashSet<String>();
    }

    public void getPageLinks(String url)
    {
        if (!links.contains(url))
        {
            try
            {
                if (links.add(url))
                    System.out.println(url);
                Document document = Jsoup.connect(url).get();
                Elements linksOnPage = document.select("a[href]");
                for (Element page : linksOnPage)
                    getPageLinks(page.attr("abs:href"));

            }
            catch (IOException e)
            {
                System.err.println("For '" + url + "': " + e.getMessage());
            }
        }
    }
}
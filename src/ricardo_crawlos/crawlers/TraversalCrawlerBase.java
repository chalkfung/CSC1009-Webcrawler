package ricardo_crawlos.crawlers;

import java.io.IOException;
import java.util.HashSet;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ricardo_crawlos.core.ICrawler;

/**
 * BaseCrawler
 */
public abstract class TraversalCrawlerBase implements ICrawler
{
    protected HashSet<String> links;

    

    public TraversalCrawlerBase()
    {
        this.links = new HashSet<String>();
    }

    protected abstract boolean canTraverse(String url);

    public String[] getTraversableLinks(Document document)
    {
        Elements linksOnPage = document.select("a[href]");
        return linksOnPage.stream().map(x -> x.attr("abs:href")).filter(this::canTraverse).toArray(String[]::new);
    }

    /**
     * Note: recursive
     *
     * @param url The url to the page to look for more urls
     */
    public void traverse(String url)
    {
        if (links.add(url)) // if can add to the hashmap, it is already unique
        {
            System.out.println("Traversing: " + url);
            try
            {
                Document document = Jsoup.connect(url).get();
                Elements linksOnPage = document.select("a[href]");

                for (Element page : linksOnPage)
                {
                    String traverseUrl = page.attr("abs:href");
                    if (canTraverse(traverseUrl))
                    {
                        traverse(traverseUrl);
                    }
                }
            }
            catch (IOException e)
            {
                System.err.println("For '" + url + "': " + e.getMessage());
            }
        }
    }

    @Override
    public String[] getTraversedLinks()
    {
        return links.toArray(new String[links.size()]);
    }

    @Override
    public String toString()
    {
        return "Links traversed: " + links.size() + "\nAll links:\n\t" + String.join("\n\t", getTraversedLinks());
    }
}
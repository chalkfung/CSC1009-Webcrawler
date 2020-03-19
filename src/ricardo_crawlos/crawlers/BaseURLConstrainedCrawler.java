package ricardo_crawlos.crawlers;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ricardo_crawlos.crawlers.TraversalCrawlerBase;

/**
 * CrawlerRunner
 */
public class BaseURLConstrainedCrawler extends TraversalCrawlerBase
{
    protected String baseUrl;
    protected Document baseDocument;

    public BaseURLConstrainedCrawler(String theBaseUrl)
    {
        baseUrl = theBaseUrl;
    }

    @Override
    public String[] getTraversableLinks()
    {
        try
        {
            baseDocument = Jsoup.connect(baseUrl).get();
        }
        catch (IOException e)
        {
            System.err.println("For '" + this.baseUrl + "': " + e.getMessage());
        }

        return getTraversableLinks(baseDocument);
    }

    @Override
    protected boolean canTraverse(String url)
    {
        return url.startsWith(baseUrl);
    }

    @Override
    public void traverse(String url)
    {
        if (url.contains("#"))
        {
            url = url.split("#")[0];
        }
        super.traverse(url);
    }

    @Override
    public void run()
    {
        traverse(baseUrl);
    }
}

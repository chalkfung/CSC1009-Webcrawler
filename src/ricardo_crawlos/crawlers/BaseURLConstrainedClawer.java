package ricardo_crawlos.crawlers;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ricardo_crawlos.crawlers.TraversalCrawlerBase;

/**
 * CrawlerRunner
 */
public class BaseURLConstrainedClawer extends TraversalCrawlerBase
{
    protected String baseUrl;
    protected Document baseDocument;

    public BaseURLConstrainedClawer(String theBaseUrl)
    {
        baseUrl = theBaseUrl;

        try
        {
            baseDocument = Jsoup.connect(baseUrl).get();
        }
        catch (IOException e)
        {
            System.err.println("For '" + this.baseUrl + "': " + e.getMessage());
        }
    }

    @Override
    public String[] getTraversableLinks()
    {
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
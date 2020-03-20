package ricardo_crawlos.crawlers;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * BaseURLConstrainedCrawler
 * Recursively crawls the site pages but constrained to its base url
 */
public class BaseURLConstrainedCrawler extends TraversalCrawlerBase
{
    protected String baseUrl;
    protected Document baseDocument;

    public BaseURLConstrainedCrawler(String theBaseUrl)
    {
        baseUrl = theBaseUrl;
    }

    /**
     * Get all the constrained links from the base page to crawl recursively
     * @return Array of urls in String
     */
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

    /**
     * @param url The url to check if it is candidate to crawl recursively
     */
    @Override
    protected boolean canTraverse(String url)
    {
        return url.startsWith(baseUrl);
    }

    /**
     * Traverse the crawling pages
     * @param url The url to the page to look for more urls
     */
    @Override
    public void traverse(String url)
    {
        // Remove id jump markers
        if (url.contains("#"))
        {
            url = url.split("#")[0];
        }
        super.traverse(url);
    }

    /**
     * Starts the traversal process
     */
    @Override
    public void run()
    {
        traverse(baseUrl);
    }
}

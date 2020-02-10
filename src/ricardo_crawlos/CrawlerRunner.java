package ricardo_crawlos;

import ricardo_crawlos.base.*;

/**
 * CrawlerRunner
 */
public class CrawlerRunner extends BaseCrawler
{
    String baseUrl;
    public CrawlerRunner(String theBaseUrl)
    {
        baseUrl = theBaseUrl;
    }

    public void run()
    {
        traverse(baseUrl);
        System.out.println(this);
    }
}
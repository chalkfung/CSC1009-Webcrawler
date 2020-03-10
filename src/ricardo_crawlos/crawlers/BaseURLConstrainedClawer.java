package ricardo_crawlos.crawlers;

import ricardo_crawlos.crawlers.TraversalCrawlerBase;

/**
 * CrawlerRunner
 */
public class BaseURLConstrainedClawer extends TraversalCrawlerBase
{
    String baseUrl;

    public BaseURLConstrainedClawer(String theBaseUrl)
    {
        baseUrl = theBaseUrl;
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
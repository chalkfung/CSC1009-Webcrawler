package ricardo_crawlos.crawlers;

import org.jsoup.nodes.Document;

import ricardo_crawlos.core.IExtractableCrawler;
import ricardo_crawlos.core.IWebsite;

public class GameInfoCrawler implements IExtractableCrawler
{
    @Override
    public IWebsite getWebsiteInfo()
    {
        return
    }

    @Override
    public String getDomain()
    {
        return null;
    }

    @Override
    public String getExtractionName()
    {
        return null;
    }

    @Override
    public void run()
    {

    }

    @Override
    public String[] getTraversableLinks()
    {
        return new String[0];
    }

    @Override
    public String[] getTraversedLinks()
    {
        return new String[0];
    }

    @Override
    public Document[] getTraversalResults()
    {
        return new Document[0];
    }

    @Override
    public String extractFrom(String source)
    {
        return null;
    }
}

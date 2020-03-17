package ricardo_crawlos.crawlers;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ricardo_crawlos.core.IExtractableCrawler;
import ricardo_crawlos.core.IWebsite;

public class MetacriticCriticReviewsCrawler implements IExtractableCrawler
{
    protected String gamePath;
    protected String link;
    protected Document loadedDocument;

    public MetacriticCriticReviewsCrawler(String theGamePath)
    {
        gamePath = theGamePath;
        link = "https://www.metacritic.com/" + gamePath + "/critic-reviews";
    }

    @Override
    public IWebsite getWebsiteInfo()
    {
        return Websites.getMetacritic();
    }

    @Override
    public String getDomain()
    {
        return "metacritic";
    }

    @Override
    public String getExtractionName()
    {
        return "critic-reviews";
    }

    @Override
    public void run()
    {
        try
        {
            loadedDocument = Jsoup.connect(link).get();
        }
        catch (IOException e)
        {
            System.err.println("For Metacritic critic reviews '" + link + "': " + e.getMessage());
        }
    }

    @Override
    public String[] getTraversableLinks()
    {
        return new String[]{ link };
    }

    @Override
    public String[] getTraversedLinks()
    {
        return getTraversableLinks();
    }

    @Override
    public Document[] getTraversalResults()
    {
        return new Document[]{ loadedDocument };
    }

    @Override
    public String extractFrom(String html)
    {
        return Jsoup.parse(html).select("ol.reviews.critic_reviews").outerHtml();
    }
}

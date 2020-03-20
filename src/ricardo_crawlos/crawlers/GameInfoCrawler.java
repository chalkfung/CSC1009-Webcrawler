package ricardo_crawlos.crawlers;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ricardo_crawlos.core.IExtractableCrawler;
import ricardo_crawlos.core.IWebsite;

/**
 * Gets the information of a game by scrapping from Gamespot
 */
public class GameInfoCrawler implements IExtractableCrawler
{
    private final String gamePath;
    private Document document;

    public GameInfoCrawler(String theGamePath)
    {
        gamePath = theGamePath;
    }

    @Override
    public IWebsite getWebsiteInfo()
    {
        return Websites.getGamespot();
    }

    @Override
    public String getDomain()
    {
        return getWebsiteInfo().getDomain();
    }

    @Override
    public String getExtractionName()
    {
        return "game-info";
    }

    /**
     * Load the HTML document
     */
    @Override
    public void run()
    {
        try
        {
            document = Jsoup.connect(getWebsiteInfo().getBaseUrl() + gamePath).get();
        }
        catch (IOException e)
        {
            System.out.println("Game info crawling error: " + e.getMessage());
        }
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
        return new Document[]{ document };
    }

    @Override
    public String extractFrom(String source)
    {
        System.out.println("Extracting raw gameinfo for: " + gamePath);
        return Jsoup.parse(source).select("div#object-stats-wrap").outerHtml();
    }
}

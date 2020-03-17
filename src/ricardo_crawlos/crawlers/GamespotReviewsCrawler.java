package ricardo_crawlos.crawlers;

import org.jsoup.Jsoup;

import ricardo_crawlos.core.IPaginatedCrawler;
import ricardo_crawlos.core.IWebsite;

/**
 * GameSpotReviewCrawler
 */
public class GamespotReviewsCrawler extends BaseURLConstrainedCrawler implements IPaginatedCrawler
{
    protected String gamePath;

    private static String getUrl(String gamePath, String subPathQuery)
    {
        return "https://www.gamespot.com/" + gamePath + "/reviews" + subPathQuery;
    }

    public GamespotReviewsCrawler(String theGamePath)
    {
        super(getUrl(theGamePath, "/"));
        this.gamePath = theGamePath;
        this.links.add(this.baseUrl);
    }

    @Override
    public void run()
    {
        traverse(this.baseUrl + "?page=1");
    }

    @Override
    public String getDomain()
    {
        return "gamespot";
    }

    @Override
    public String getExtractionName()
    {
        return "user-reviews";
    }

    @Override
    public String fromSubpage(String subPath)
    {
        return getUrl(gamePath, "/" + subPath);
    }

    @Override
    public int getPageCount()
    {
        return this.links.size() - 1;
    }

    @Override
    public IWebsite getWebsiteInfo()
    {
        return Websites.getGamespot();
    }

    @Override
    public String extractFrom(String html)
    {
        return Jsoup.parse(html).select("li.userReview-list__item").outerHtml();
    }
}
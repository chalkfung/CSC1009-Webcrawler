package ricardo_crawlos.crawlers;

import ricardo_crawlos.core.IPaginatedCrawler;
import ricardo_crawlos.core.IWebsite;

public class MetacriticUserReviewsCrawler extends BaseURLConstrainedCrawler implements IPaginatedCrawler
{
    protected String gamePath;

    private static String getUrl(String gamePath, String subPath)
    {
        return "https://www.metacritic.com/" + gamePath + "/user-reviews/" + subPath;
    }

    public MetacriticUserReviewsCrawler(String theGamePath)
    {
        super(getUrl(theGamePath, ""));
        this.gamePath = theGamePath;
        this.links.add(this.baseUrl);
    }

    @Override
    public void run()
    {
        traverse(this.baseUrl + "?sort-by=date&num_items=100&page=0");
    }

    @Override
    public IWebsite getWebsiteInfo()
    {
        Websites.getMetacritic();
    }

    @Override
    public String getDomain()
    {
        return "metacritic";
    }

    @Override
    public String getPathName()
    {
        return "user-reviews";
    }

    @Override
    public String fromSubpage(String subPath)
    {
        return getUrl(gamePath, subPath);
    }

    @Override
    public String extractFrom(String source)
    {
        return null;
    }
}

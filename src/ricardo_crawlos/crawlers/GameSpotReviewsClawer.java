package ricardo_crawlos.crawlers;

import ricardo_crawlos.core.IDomainSegmentClawer;

/**
 * GameSpotReviewClawer
 */
public class GameSpotReviewsClawer extends BaseURLConstrainedClawer implements IDomainSegmentClawer
{
    protected String gamePath;

    @Override
    public String getDomain()
    {
        return "gamespot";
    }

    @Override
    public String getSegmentName()
    {
        return "user-reviews";
    }

    @Override
    public String fromSubpage(String subPath)
    {
        return getUrl(gamePath, subPath);
    }

    private static String getUrl(String gamePath, String subPath)
    {
        return "https://www.gamespot.com/" + gamePath + "/reviews/" + subPath;
    }

    public GameSpotReviewsClawer(String theGamePath)
    {
        super(getUrl(theGamePath, ""));
        this.gamePath = theGamePath;
        this.links.add(this.baseUrl);
    }

    @Override
    public void run()
    {
        traverse(this.baseUrl + "?page=1");
    }
}
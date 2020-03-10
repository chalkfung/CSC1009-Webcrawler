package ricardo_crawlos.crawlers;

/**
 * GameSpotReviewClawer
 */
public class GameSpotReviewsClawer extends BaseURLConstrainedClawer
{
    protected String gamePath;

    public GameSpotReviewsClawer(String theGamePath)
    {
        super("https://www.gamespot.com/" + theGamePath + "/reviews/");
        this.gamePath = theGamePath;
        this.links.add(this.baseUrl);
    }

    @Override
    public void run()
    {
        traverse(this.baseUrl + "?page=1");
    }
}
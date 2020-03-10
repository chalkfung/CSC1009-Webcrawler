package ricardo_crawlos.crawlers;

/**
 * GameSpotReviewClawer
 */
public class GameSpotReviewClawer extends BaseURLConstrainedClawer
{
    public GameSpotReviewClawer(String theGamePath)
    {
        super("https://www.gamespot.com/" + theGamePath);
    }
}
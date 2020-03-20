package ricardo_crawlos.extractors;

import java.text.SimpleDateFormat;
import java.util.Date;

import ricardo_crawlos.core.IReviewsExtractor;
import ricardo_crawlos.core.IWebsite;
import ricardo_crawlos.crawlers.Websites;

/**
 * The base extractor to extract reviews from metacritic
 */
public abstract class MetacriticReviewsExtractorBase implements IReviewsExtractor
{
    protected int gameId;
    private SimpleDateFormat dateParser = new SimpleDateFormat("MMM dd, yyyy");

    public MetacriticReviewsExtractorBase(int theGameId)
    {
        super();
        gameId = theGameId;
    }

    @Override
    public IWebsite getWebsiteInfo()
    {
        return Websites.getMetacritic();
    }

    protected Date parseDate(String dateText)
    {
        try
        {
            return dateParser.parse(dateText);
        }
        catch (Exception e)
        {
            return new Date(0);
        }
    }
}

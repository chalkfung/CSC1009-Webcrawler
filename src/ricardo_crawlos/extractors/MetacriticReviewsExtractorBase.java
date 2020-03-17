package ricardo_crawlos.extractors;

import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ricardo_crawlos.core.IReview;
import ricardo_crawlos.core.IReviewsExtractor;
import ricardo_crawlos.core.IWebsite;
import ricardo_crawlos.crawlers.Websites;
import ricardo_crawlos.models.ReviewBase;
import ricardo_crawlos.models.UserReview;

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

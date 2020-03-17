package ricardo_crawlos.extractors;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import ricardo_crawlos.core.IReview;
import ricardo_crawlos.core.IReviewsExtractor;
import ricardo_crawlos.core.IWebsite;
import ricardo_crawlos.crawlers.Websites;
import ricardo_crawlos.models.UserReview;

public class MetacriticUserReviewsExtractor implements IReviewsExtractor
{
    private int gameId;
    private SimpleDateFormat dateParser = new SimpleDateFormat("MMM dd, yyyy");

    public MetacriticUserReviewsExtractor(int theGameId)
    {
        super();
        gameId = theGameId;
    }

    @Override
    public IWebsite getWebsiteInfo()
    {
        return Websites.getMetacritic();
    }

    @Override
    public IReview[] extractFrom(String html)
    {
        return extractUserReviews(html);
    }

    public UserReview[] extractUserReviews(String html)
    {
        Document document = Jsoup.parse(html);

        return document.select("li.review.user_review").stream().map(element ->
        {
            var author = element.select("div.name > a").text();
            var comment = element.select("div.review_body").text();
            var score = Double.parseDouble(element.select("div.review_grade > div").text());
            var date = parseDate(element.select("div.date").text());
            var helpfulScore = Integer.parseInt(element.select("span.total_ups").text());
            var helpfulCount = Integer.parseInt(element.select("span.total_thumbs").text());

            return new UserReview(gameId, score, comment, date, author, helpfulScore, helpfulCount);
        }).toArray(UserReview[]::new);
    }

    private Date parseDate(String dateText)
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

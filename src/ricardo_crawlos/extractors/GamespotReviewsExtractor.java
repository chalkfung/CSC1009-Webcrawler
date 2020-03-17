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

/**
 * GamespotReviewsExtractor
 */
public class GamespotReviewsExtractor implements IReviewsExtractor
{
    private int gameId;
    private SimpleDateFormat dateParser = new SimpleDateFormat("MMM-dd-yyyy");

    public GamespotReviewsExtractor(int theGameId)
    {
        super();
        gameId = theGameId;
    }

    @Override
    public IWebsite getWebsiteInfo()
    {
        return Websites.getGamespot();
    }

    @Override
    public IReview[] extractFrom(String html)
    {
        return extractUserReviews(html);
    }

    public UserReview[] extractUserReviews(String html)
    {
        Document document = Jsoup.parse(html);

        return document.select("li.userReview-list__item")
                .stream()
                .map(this::parseElement)
                .sorted(Comparator.comparing(ReviewBase::getDateCreated))
                .toArray(UserReview[]::new);
    }

    private UserReview parseElement(Element element)
    {
        var title = element.select("h3.media-title > a").text();
        var reviewComponents = element.getElementsByAttributeValueContaining("class", "userReview-list");
        var comment = reviewComponents.get(2).text();
        var score = Double.parseDouble(element.select("div.media-well--review-user > strong").text());
        var reviewMeta = reviewComponents.get(1);
        var author = reviewMeta.select("a").text();
        var date = parseDate(reviewMeta.text(), author);
        var helpfulFragments = reviewComponents.get(3).text().substring(0, 16).split(" ");
        var helpfulScore = Integer.parseInt(helpfulFragments[0]);
        var helpfulCount = Integer.parseInt(helpfulFragments[2]);

        return new UserReview(gameId, score, title + "\n" + comment, date, author, helpfulScore, helpfulCount);
    }

    private Date parseDate(String metaText, String author)
    {
        try
        {
            var dateFragments = metaText.replace(author, "")
                    .split("Review Date: ")[1]
                    .split("\\|")[0]
                    .replace(",", "")
                    .split(" ");

            return dateParser.parse(String.join("-", dateFragments));
        }
        catch (Exception e)
        {
            return new Date(0);
        }
    }
}
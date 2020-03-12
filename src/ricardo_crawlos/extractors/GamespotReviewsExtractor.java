package ricardo_crawlos.extractors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ricardo_crawlos.core.IReview;
import ricardo_crawlos.core.IReviewsExtractor;
import ricardo_crawlos.core.IWebsite;
import ricardo_crawlos.crawlers.Websites;
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
    public IReview[] extract(String html)
    {
        return extractUserReviews(html);
    }

    public UserReview[] extractUserReviews(String html)
    {
        Document document = Jsoup.parse(html);

        return document.select("li.userReview-list__item").stream().map(element ->
        {
            String title = element.select("h3.media-title > a").text();
            Elements reviewComponents = element.getElementsByAttributeValueContaining("class", "userReview-list");
            String comment = reviewComponents.get(2).text();
            double score = Double.parseDouble(element.select("div.media-well--review-user > strong").text());
            Element reviewMeta = reviewComponents.get(1);
            String author = reviewMeta.select("a").text();
            String[] dateFragments = reviewMeta.text()
                    .replace(author, "")
                    .split("Review Date: ")[1]
                    .split("\\|")[0]
                    .replace(",", "")
                    .split(" ");
            Date date;
            try
            {
                date = dateParser.parse(String.join("-", dateFragments));
            }
            catch (ParseException e)
            {
                date = new Date();
            }
            String[] helpfulFragments = reviewComponents.get(3)
                .text()
                .substring(0, 16)
                .split(" ");
            int helpfulScore = Integer.parseInt(helpfulFragments[0]);
            int helpfulCount = Integer.parseInt(helpfulFragments[2]);

            return new UserReview(gameId, score, title + "\n" + comment, date, author, helpfulScore, helpfulCount);
        }).toArray(UserReview[]::new);
    }
}
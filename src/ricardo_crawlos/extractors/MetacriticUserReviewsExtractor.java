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

public class MetacriticUserReviewsExtractor extends MetacriticReviewsExtractorBase
{
    public MetacriticUserReviewsExtractor(int theGameId)
    {
        super(theGameId);
    }

    @Override
    public IReview[] extractFrom(String html)
    {
        return extractUserReviews(html);
    }

    public UserReview[] extractUserReviews(String html)
    {
        Document document = Jsoup.parse(html);

        return document.select("li.review.user_review")
                .parallelStream()
                .map(this::parseElement)
                .sorted(Comparator.comparing(ReviewBase::getDateCreated))
                .sequential()
                .toArray(UserReview[]::new);
    }

    private UserReview parseElement(Element element)
    {
        var author = element.select("div.name > a").text();
        var comment = element.select("div.review_body").text();

        var toTrimEnd = "Expand";

        if (comment.endsWith(toTrimEnd))
        {
            comment = comment.substring(0, comment.length() - toTrimEnd.length());
        }

        var score = Double.parseDouble(element.select("div.review_grade > div").text());
        var date = parseDate(element.select("div.date").text());
        var helpfulScore = Integer.parseInt(element.select("span.total_ups").text());
        var helpfulCount = Integer.parseInt(element.select("span.total_thumbs").text());

        return new UserReview(gameId, score, comment, date, author, helpfulScore, helpfulCount);
    }
}

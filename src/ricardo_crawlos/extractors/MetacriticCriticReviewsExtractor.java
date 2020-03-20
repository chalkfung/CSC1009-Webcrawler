package ricardo_crawlos.extractors;

import java.util.Comparator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import ricardo_crawlos.core.IReview;
import ricardo_crawlos.models.CriticReview;
import ricardo_crawlos.models.ReviewBase;

/**
 * Extractor to extract Metacritic critic reviews into CriticReview model
 */
public class MetacriticCriticReviewsExtractor extends MetacriticReviewsExtractorBase
{
    public MetacriticCriticReviewsExtractor(int theGameId)
    {
        super(theGameId);
    }

    @Override
    public IReview[] extractFrom(String html)
    {
        return extractUserReviews(html);
    }

    public CriticReview[] extractUserReviews(String html)
    {
        Document document = Jsoup.parse(html);

        return document.select("li.review.critic_review")
                .parallelStream()
                .map(this::parseElement)
                .filter(x -> x.getScore() != -1)
                .sorted(Comparator.comparing(ReviewBase::getDateCreated))
                .sequential()
                .toArray(CriticReview[]::new);
    }

    private CriticReview parseElement(Element element)
    {
        var author = element.select("div.source > a").text();
        var comment = element.select("div.review_body").text();
        var score = parseScore(element.select("div.review_grade > div").text());
        var date = parseDate(element.select("div.date").text());
        var source = element.select("li.review_action.full_review > a").attr("abs:href");

        return new CriticReview(gameId, score, comment, date, source, author);
    }

    private double parseScore(String text)
    {
        try
        {
            return Double.parseDouble(text) / 10;
        }
        catch (NumberFormatException e)
        {
            return -1;
        }
    }
}

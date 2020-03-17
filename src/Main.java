import javax.xml.stream.FactoryConfigurationError;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.jsoup.Jsoup;

import GUI.*;
import ricardo_crawlos.core.IReview;
import ricardo_crawlos.crawlers.GamespotReviewsCrawler;
import ricardo_crawlos.extractors.GamespotReviewsExtractor;
import ricardo_crawlos.models.UserReview;
import ricardo_crawlos.storage.CachedGameSiteCrawler;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;
import ricardo_crawlos.utilities.*;

import java.awt.EventQueue;

public class Main
{
    public static void main(String[] args)
    {
        //testExtraction();

        showWindow();
    }

    private static void testExtraction()
    {
        var crawler = new CachedGameSiteCrawler(new GamespotReviewsCrawler("dota-2"), "dota-2");

        var document = Jsoup.parse(crawler.getOrCacheHTML());

        TextWriter.writeAllText("extractedTest.html", document.html());

        var gamespotDotaReviewsExtractor = new GamespotReviewsExtractor(0);

        var extractedReviews = gamespotDotaReviewsExtractor.extractFrom(document.html());

        var reviewsJson = JsonSerialiser.DefaultInstance().toJson(extractedReviews);

        TextWriter.writeAllText("database/extracted/reviews/dota-2/gamespot_user-reviews.json", reviewsJson);
    }

    public static void showWindow()
    {
        EventQueue.invokeLater(() ->
        {
            try
            {
                MainReviewFrame frame = new MainReviewFrame();
                frame.setVisible(true);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }
}

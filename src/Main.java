import org.jsoup.Jsoup;

import GUI.*;
import ricardo_crawlos.crawlers.MetacriticUserReviewsCrawler;
import ricardo_crawlos.extractors.MetacriticUserReviewsExtractor;
import ricardo_crawlos.storage.CachedGamesiteCrawler;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;

import java.awt.EventQueue;

public class Main
{
    public static void main(String[] args)
    {
        testExtraction();

        // showWindow();
    }

    private static void testExtraction()
    {
        var crawler = new CachedGamesiteCrawler(new MetacriticUserReviewsCrawler("game/pc/dota-2"), "dota-2");
        var document = Jsoup.parse(crawler.getOrCacheHTML());

        TextWriter.writeAllText("metacritic_dota-2_dump.html", document.html());

        var reviewsExtractor = new MetacriticUserReviewsExtractor(0);
        var extractedReviews = reviewsExtractor.extractFrom(document.html());
        var reviewsJson = JsonSerialiser.DefaultInstance().toJson(extractedReviews);
        TextWriter.writeAllText("database/extracted/reviews/dota-2/metacritic_user-reviews.json", reviewsJson);

//        var gamespotDotaReviewsExtractor = new GamespotReviewsExtractor(0);
//        var extractedReviews = gamespotDotaReviewsExtractor.extractFrom(document.html());
//        var reviewsJson = JsonSerialiser.DefaultInstance().toJson(extractedReviews);
//
//        TextWriter.writeAllText("database/extracted/reviews/dota-2/gamespot_user-reviews.json", reviewsJson);
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

import java.awt.*;

import GUI.MainReviewFrame;
import ricardo_crawlos.core.IExtractableCrawler;
import ricardo_crawlos.core.IReviewsExtractor;
import ricardo_crawlos.crawlers.GamespotReviewsCrawler;
import ricardo_crawlos.crawlers.MetacriticUserReviewsCrawler;
import ricardo_crawlos.extractors.GamespotReviewsExtractor;
import ricardo_crawlos.extractors.MetacriticUserReviewsExtractor;
import ricardo_crawlos.storage.CachedGamesiteCrawler;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;

public class Main
{
    public static void main(String[] args)
    {
        showWindow();
        testExtraction("dota-2");
    }

    private static void testExtraction(String referenceName)
    {
        storeReviews(referenceName, "gamespot", new GamespotReviewsCrawler("dota-2"), new GamespotReviewsExtractor(0));
        storeReviews(referenceName, "metacritic", new MetacriticUserReviewsCrawler("game/pc/dota-2"), new MetacriticUserReviewsExtractor(0));
    }

    private static void storeReviews(String referenceName, String siteName, IExtractableCrawler crawler, IReviewsExtractor extractor)
    {
        System.out.println("Extracting: " + siteName + " -> " + referenceName );
        var cachedCrawler = new CachedGamesiteCrawler(crawler, referenceName);
        var extractedReviews = extractor.extractFrom(cachedCrawler.getOrCacheHTML());
        var reviewsJson = JsonSerialiser.DefaultInstance().toJson(extractedReviews);
        TextWriter.writeAllText("database/extracted/reviews/" + referenceName + "/" + siteName + "_" + crawler.getExtractionName() + ".json", reviewsJson);
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

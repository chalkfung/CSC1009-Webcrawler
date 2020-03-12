import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import GUI.MainWindow;
import ricardo_crawlos.core.IReviewsExtractor;
import ricardo_crawlos.crawlers.GamespotReviewsClawer;
import ricardo_crawlos.extractors.GamespotReviewsExtractor;
import ricardo_crawlos.storage.CachedGameSiteCrawler;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;

public class Main
{
    public static void main(String[] args) throws IOException, ParseException
    {
        testExtraction();

        // ShowWindow();
    }

    private static void testExtraction()
    {
        var crawler = new CachedGameSiteCrawler(new GamespotReviewsClawer("dota-2"), "dota-2");

        var document = Jsoup.parse(crawler.getOrCacheHTML());

        TextWriter.writeAllText("extractedTest.html", document.html());

        // Elements ratings = document.select("div.media-well--review-user > strong");
        var gamespotDotaReviewsExtractor = new GamespotReviewsExtractor(0);

        var reviewsJson = JsonSerialiser.DefaultInstance().toJson(gamespotDotaReviewsExtractor.extract(document.html()));

        TextWriter.writeAllText("database/extracted/reviews/dota-2/gamespot_user-reviews.json", reviewsJson);
    }

    public static void ShowWindow()
    {
        MainWindow dialog = new MainWindow();
        dialog.setSize(1280, 720);
        dialog.setVisible(true);
        System.exit(0);
    }
}

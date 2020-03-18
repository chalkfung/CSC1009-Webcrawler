package ricardo_crawlos.managers;

import java.io.IOException;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import ricardo_crawlos.core.IExtractableCrawler;
import ricardo_crawlos.core.IReviewsExtractor;
import ricardo_crawlos.core.ISearchContext;
import ricardo_crawlos.crawlers.GamespotReviewsCrawler;
import ricardo_crawlos.crawlers.MetacriticCriticReviewsCrawler;
import ricardo_crawlos.crawlers.MetacriticUserReviewsCrawler;
import ricardo_crawlos.extractors.GamespotReviewsExtractor;
import ricardo_crawlos.extractors.MetacriticCriticReviewsExtractor;
import ricardo_crawlos.extractors.MetacriticUserReviewsExtractor;
import ricardo_crawlos.storage.CachedGamesiteCrawler;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;

public class SearchManager
{
    private String gameSpotKey;
    private String metaKey = "game/pc/";

    public SearchManager(String key)
    {
        key = key.toLowerCase();
        this.gameSpotKey = key.replaceAll(" ", "-");
        this.metaKey += this.gameSpotKey;
    }

    public ISearchContext retrieve()
    {
        return new SearchContextGamePC(gameSpotKey);
    }

    public static void fetchReviewsGamePC(String gameKey)
    {
        storeReviews(gameKey, new GamespotReviewsCrawler(gameKey), new GamespotReviewsExtractor(0));
        storeReviews(gameKey,  new MetacriticUserReviewsCrawler("game/pc/" + gameKey), new MetacriticUserReviewsExtractor(0));
        storeReviews(gameKey,  new MetacriticCriticReviewsCrawler("game/pc/" + gameKey), new MetacriticCriticReviewsExtractor(0));
    }

    private static void storeReviews(String referenceName, IExtractableCrawler crawler, IReviewsExtractor extractor)
    {
        System.out.println("Extracting: " + crawler.getDomain() + " " + crawler.getExtractionName() + " -> " + referenceName );
        var cachedCrawler = new CachedGamesiteCrawler(crawler, referenceName);
        var extractedReviews = extractor.extractFrom(cachedCrawler.getOrCacheHTML());
        var reviewsJson = JsonSerialiser.DefaultInstance().toJson(extractedReviews);
        TextWriter.writeAllText("database/extracted/reviews/" + referenceName + "/" + crawler.getDomain()  + "_" + crawler.getExtractionName() + ".json", reviewsJson);
    }
}

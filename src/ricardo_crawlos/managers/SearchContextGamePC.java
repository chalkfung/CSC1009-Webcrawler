package ricardo_crawlos.managers;

import java.io.IOException;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.stream.Stream;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import ricardo_crawlos.core.IExtractableCrawler;
import ricardo_crawlos.core.IReview;
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

public class SearchContextGamePC implements ISearchContext
{
    private String gameReference;
    private int gameId = 0;

    private String cachedGamespotUserReviewRaw;
    private String cachedMetacriticUSerReviewRaw;
    private String cachedMetacriticCriticReviewRaw;

    private IExtractableCrawler gamespotUserReviewExtractable;
    private IExtractableCrawler metacriticUserReviewExtractable;
    private IExtractableCrawler metacriticCriticReviewExtractable;

    private IReview[] gamespotUserReviews;
    private IReview[] metacriticUserReviews;
    private IReview[] metacriticCriticReviews;

    public SearchContextGamePC(String theGameReference)
    {
        gameReference = theGameReference;
    }

    private String getGamespotKey()
    {
        return gameReference;
    }

    private String getMetacriticKey()
    {
        return "game/pc/" + gameReference;
    }

    @Override
    public void probe() throws IOException
    {
        var gameSpotLink = "https://www.gamespot.com/" + getGamespotKey();
        var metacriticLink = "https://www.metacritic.com/" + getMetacriticKey();

        System.out.println("Probing " + gameSpotLink);
        Jsoup.connect(gameSpotLink).method(Connection.Method.HEAD).execute();
        System.out.println("Probing " + metacriticLink);
        Jsoup.connect(metacriticLink).method(Connection.Method.HEAD).execute();

        gamespotUserReviewExtractable = new GamespotReviewsCrawler(gameReference);
        metacriticUserReviewExtractable = new MetacriticUserReviewsCrawler(getMetacriticKey());
        metacriticCriticReviewExtractable = new MetacriticCriticReviewsCrawler(getMetacriticKey());
    }

    @Override
    public void fetch()
    {
        cachedGamespotUserReviewRaw = new CachedGamesiteCrawler(gamespotUserReviewExtractable, gameReference).getOrCacheHTML();
        cachedMetacriticUSerReviewRaw = new CachedGamesiteCrawler(metacriticUserReviewExtractable, gameReference).getOrCacheHTML();
        cachedMetacriticCriticReviewRaw = new CachedGamesiteCrawler(metacriticCriticReviewExtractable, gameReference).getOrCacheHTML();
    }

    @Override
    public void extract()
    {
        gamespotUserReviews = extractAndStoreReviews(cachedGamespotUserReviewRaw, gamespotUserReviewExtractable, new GamespotReviewsExtractor(gameId));
        metacriticUserReviews = extractAndStoreReviews(cachedMetacriticUSerReviewRaw, metacriticUserReviewExtractable, new MetacriticUserReviewsExtractor(gameId));
        metacriticCriticReviews = extractAndStoreReviews(cachedMetacriticCriticReviewRaw, metacriticCriticReviewExtractable, new MetacriticCriticReviewsExtractor(gameId));
    }

    private IReview[] extractAndStoreReviews(String rawHTML, IExtractableCrawler crawler, IReviewsExtractor extractor)
    {
        var extractedReviews = extractor.extractFrom(rawHTML);
        var reviewsJson = JsonSerialiser.DefaultInstance().toJson(extractedReviews);
        System.out.println("Storing extracted data: " + crawler.getDomain() + " " + crawler.getExtractionName() + " -> " + gameReference );
        TextWriter.writeAllText("database/extracted/reviews/" + gameReference + "/" + crawler.getDomain()  + "_" + crawler.getExtractionName() + ".json", reviewsJson);
        return extractedReviews;
    }

    public IReview[] getAllUserReviews()
    {
        return Stream.concat(Arrays.stream(gamespotUserReviews), Arrays.stream(metacriticCriticReviews))
                .toArray(IReview[]::new);
    }

    public IReview[] getAllCriticReviews()
    {
        return metacriticCriticReviews;
    }

    @Override
    public void analyse()
    {

    }
}

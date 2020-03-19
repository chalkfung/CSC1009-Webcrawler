package ricardo_crawlos.managers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Objects;
import java.util.stream.Stream;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import ricardo_crawlos.core.*;
import ricardo_crawlos.crawlers.GameInfoCrawler;
import ricardo_crawlos.crawlers.GamespotReviewsCrawler;
import ricardo_crawlos.crawlers.MetacriticCriticReviewsCrawler;
import ricardo_crawlos.crawlers.MetacriticUserReviewsCrawler;
import ricardo_crawlos.extractors.GamespotGameinfoExtractor;
import ricardo_crawlos.extractors.GamespotReviewsExtractor;
import ricardo_crawlos.extractors.MetacriticCriticReviewsExtractor;
import ricardo_crawlos.extractors.MetacriticUserReviewsExtractor;
import ricardo_crawlos.models.Game;
import ricardo_crawlos.storage.CachedGamesiteCrawler;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;
import ricardo_crawlos.utilities.AnalyserBase;
import ricardo_crawlos.utilities.Statistics;

public class SearchContextGamePC implements ISearchContext
{
    private String gameReference;
    private int gameId = 0;

    private String cachedGamespotUserReviewRaw;
    private String cachedMetacriticUSerReviewRaw;
    private String cachedMetacriticCriticReviewRaw;
    private String cachedGameinfoRaw;

    private IExtractableCrawler gamespotUserReviewExtractable;
    private IExtractableCrawler metacriticUserReviewExtractable;
    private IExtractableCrawler metacriticCriticReviewExtractable;
    private IExtractableCrawler gameinfoExtractable;

    private IReview[] gamespotUserReviews;
    private IReview[] metacriticUserReviews;
    private IReview[] metacriticCriticReviews;
    private Game gameInfo;

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
        var cachePath = Path.of("database/cache/" + gameReference);

        if (!Files.exists(cachePath))
        {
            var gameSpotLink = "https://www.gamespot.com/" + getGamespotKey();
            var metacriticLink = "https://www.metacritic.com/" + getMetacriticKey();

            var probeErrors = Arrays.asList(gameSpotLink, metacriticLink)
                    .stream()
                    .parallel()
                    .map(x ->
                    {
                        try
                        {
                            System.out.println("Probing " + x);
                            Jsoup.connect(x).method(Connection.Method.HEAD).execute();
                        }
                        catch (IOException e)
                        {
                            System.out.println("Probe failed for " + x);
                            return e;
                        }
                        return null;
                    })
                    .filter(Objects::nonNull)
                    .toArray(IOException[]::new);

            if (probeErrors.length > 0)
            {
                throw probeErrors[0];
            }
        }


        System.out.println("Starting crawlers");
        gamespotUserReviewExtractable = new GamespotReviewsCrawler(gameReference);
        metacriticUserReviewExtractable = new MetacriticUserReviewsCrawler(getMetacriticKey());
        metacriticCriticReviewExtractable = new MetacriticCriticReviewsCrawler(getMetacriticKey());
        gameinfoExtractable = new GameInfoCrawler(gameReference);
        System.out.println("Started crawlers");
    }

    @Override
    public void fetch()
    {
        cachedGamespotUserReviewRaw = new CachedGamesiteCrawler(gamespotUserReviewExtractable, gameReference).getOrCacheHTML();
        cachedMetacriticUSerReviewRaw = new CachedGamesiteCrawler(metacriticUserReviewExtractable, gameReference).getOrCacheHTML();
        cachedMetacriticCriticReviewRaw = new CachedGamesiteCrawler(metacriticCriticReviewExtractable, gameReference).getOrCacheHTML();
        cachedGameinfoRaw = new CachedGamesiteCrawler(gameinfoExtractable, gameReference).getOrCacheHTML();
    }

    @Override
    public void extract()
    {
        gameInfo = extractAndStoreGameInfo(cachedGameinfoRaw, new GamespotGameinfoExtractor());
        gamespotUserReviews = extractAndStoreReviews(cachedGamespotUserReviewRaw, gamespotUserReviewExtractable, new GamespotReviewsExtractor(gameId));
        metacriticUserReviews = extractAndStoreReviews(cachedMetacriticUSerReviewRaw, metacriticUserReviewExtractable, new MetacriticUserReviewsExtractor(gameId));
        metacriticCriticReviews = extractAndStoreReviews(cachedMetacriticCriticReviewRaw, metacriticCriticReviewExtractable, new MetacriticCriticReviewsExtractor(gameId));

    }

    private Game extractAndStoreGameInfo(String rawHtml, IExtractable<String, Game> extractor)
    {
        var game = extractor.extractFrom(rawHtml);
        var gameJson = JsonSerialiser.DefaultInstance().toJson(game);
        System.out.println("Storing extracted game: " + game.getGameName());
        TextWriter.writeAllText("database/extracted/games/" + gameReference + ".json", gameJson);
        gameId = game.getGameId();
        return game;
    }

    private IReview[] extractAndStoreReviews(String rawHTML, IExtractableCrawler crawler, IReviewsExtractor extractor)
    {
        var extractedReviews = extractor.extractFrom(rawHTML);
        var reviewsJson = JsonSerialiser.DefaultInstance().toJson(extractedReviews);
        System.out.println("Storing extracted reviews: " + crawler.getDomain() + " " + crawler.getExtractionName() + " " + extractedReviews.length + " -> " + gameReference);
        TextWriter.writeAllText("database/extracted/reviews/" + gameReference + "/" + crawler.getDomain() + "_" + crawler.getExtractionName() + ".json", reviewsJson);
        return extractedReviews;
    }

    public IReview[] getAllUserReviews()
    {
        return Stream.concat(Arrays.stream(gamespotUserReviews), Arrays.stream(metacriticUserReviews))
                .toArray(IReview[]::new);
    }

    public IReview[] getAllCriticReviews()
    {
        return metacriticCriticReviews;
    }

    @Override
    public Dictionary<Integer, Statistics<Double, IReview>> analyse()
    {
        Dictionary<Integer, Statistics<Double, IReview>> result = new Hashtable<>();
        AnalyserBase<IReview> analyser = new AnalyserBase<>();
        result.put(0, analyser.Analyse(Arrays.asList(getAllUserReviews())));
        result.put(1, analyser.Analyse(result.get(0).getNonOutliers()));
        if(result.get(0).getOutliers().size() > 0)
        {
            result.put(2, analyser.Analyse(result.get(0).getOutliers()));
            result.put(3, analyser.Analyse(Arrays.asList(getAllCriticReviews())));
        }
        else
            result.put(2, analyser.Analyse(Arrays.asList(getAllCriticReviews())));
        return result;
    }

    public Game getGameInfo()
    {
        return gameInfo;
    }
}

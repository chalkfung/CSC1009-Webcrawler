package ricardo_crawlos.managers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
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

class CrawlingBinder
{
    private String cached;
    private IExtractableCrawler extractable;
    private Function<Double, Double> progressSeekerRedirection;
    private BiConsumer<String, IExtractableCrawler> extractionConsumer;

    CrawlingBinder(IExtractableCrawler extractable,
                   Function<Double, Double> progressSeekerRedirection,
                   BiConsumer<String, IExtractableCrawler> extractionConsumer)
    {
        this.extractable = extractable;
        this.progressSeekerRedirection = progressSeekerRedirection;
        this.extractionConsumer = extractionConsumer;
    }

    public void performCaching(String gameReference, Consumer<Double> progressSeeker)
    {
        cached = new CachedGamesiteCrawler(extractable, gameReference)
                .getOrCacheHTML(progressSeekerRedirection == null ? null : x -> progressSeeker.accept(progressSeekerRedirection.apply(x)));
    }

    public void performExtraction()
    {
        this.extractionConsumer.accept(cached, this.extractable);
    }
}

public class SearchContextGamePC implements ISearchContext
{
    private String gameReference;
    private int gameId = 0;

    private List<CrawlingBinder> binders = new ArrayList<>();

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

            var probeErrors = Stream.of(gameSpotLink, metacriticLink)
                    .parallel()
                    .map(this::tryProbe)
                    .filter(Objects::nonNull)
                    .toArray(IOException[]::new);

            if (probeErrors.length > 0)
            {
                throw probeErrors[0];
            }
        }
        else
        {
            System.out.println("Found in cache for " + gameReference);
        }

        System.out.println("Starting crawlers");

        // Binding Gamespot user reviews crawling components
        binders.add(new CrawlingBinder(new GamespotReviewsCrawler(gameReference),
                x -> x / 2.0,
                (cached, extractable) -> this.gamespotUserReviews = extractAndStoreReviews(cached, extractable, new GamespotReviewsExtractor(gameId)))
        );

        // Bind Metacritic user reviews crawling components
        binders.add(new CrawlingBinder(new MetacriticUserReviewsCrawler(getMetacriticKey()),
                x -> 0.5 + x / 2.0,
                (cached, extractable) -> this.metacriticUserReviews =  extractAndStoreReviews(cached, extractable, new MetacriticUserReviewsExtractor(gameId)))
        );

        // Bind Metacritic critic reviews crawling components
        binders.add(new CrawlingBinder(new MetacriticCriticReviewsCrawler(getMetacriticKey()),
                null,
                (cached, extractable) -> this.metacriticCriticReviews =  extractAndStoreReviews(cached, extractable, new MetacriticCriticReviewsExtractor(gameId)))
        );

        // Bind Gamespot game information crawling components
        binders.add(new CrawlingBinder(new GameInfoCrawler(gameReference),
                null,
                (cached, extractable) -> this.gameInfo = extractAndStoreGameInfo(cached, new GamespotGameinfoExtractor()))
        );

        System.out.println("Started crawlers");
    }

    private IOException tryProbe(String url)
    {
        try
        {
            System.out.println("Probing " + url);
            Jsoup.connect(url).method(Connection.Method.HEAD).execute();
        }
        catch (IOException e)
        {
            System.out.println("Probe failed for " + url);
            return e;
        }
        return null;
    }

    @Override
    public void fetch(Consumer<Double> progressListener)
    {
        binders.forEach(x -> x.performCaching(gameReference, progressListener));
    }

    @Override
    public void extract()
    {
        binders.forEach(CrawlingBinder::performExtraction);
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
        if (result.get(0).getOutliers().size() > 0)
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

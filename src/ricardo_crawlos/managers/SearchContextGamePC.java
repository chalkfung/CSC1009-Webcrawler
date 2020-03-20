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

/**
 * SearchContextGamePC
 */
public class SearchContextGamePC implements ISearchContext
{
    private String gameReference;
    private int gameId = 0;

    private List<CrawlingBinder> binders = new ArrayList<>();

    private IReview[] gamespotUserReviews;
    private IReview[] metacriticUserReviews;
    private IReview[] metacriticCriticReviews;
    private Game gameInfo;

    /**
     * Constructor of SearchContextGamePC class
     * @param theGameReference the game tag String, e.g. DotA 2 game would be dota-2
     */
    public SearchContextGamePC(String theGameReference)
    {
        gameReference = theGameReference;
    }

    /**
     * Getter of the game tag string for GameSpot
     * @return game tag string for GameSpot
     */
    private String getGamespotKey()
    {
        return gameReference;
    }

    /**
     * Getter of the game tag string for Metacritic
     * @return game tag string for Metacritic
     */
    private String getMetacriticKey()
    {
        return "game/pc/" + gameReference;
    }

    /**
     * Checks if the given tag is not giving any faults, i.e. the game has not been crawled before, then crawl.
     * @throws IOException Exception when the tag is invalid and giving errors.
     */
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

    /**
     * Try probing with the given URL
     * @param url URL of the game
     * @return If exception occurs, returns the exception. Else, nothing.
     */
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

    /**
     * Crawling process
     * @param progressListener Any changes in the progress and update the GUI to display the changes.
     */
    @Override
    public void fetch(Consumer<Double> progressListener)
    {
        binders.forEach(x -> x.performCaching(gameReference, progressListener));
    }

    /**
     * Extracting process
     */
    @Override
    public void extract()
    {
        binders.forEach(CrawlingBinder::performExtraction);
    }


     /**
     * To parse the json file of the reviews into Game class object
     * @param rawHtml HTML of the game
     * @param extractor The extractor instance
     * @return  Game class objects parsed from the database json files.
     */
    private Game extractAndStoreGameInfo(String rawHtml, IExtractable<String, Game> extractor)
    {
        var game = extractor.extractFrom(rawHtml);
        var gameJson = JsonSerialiser.DefaultInstance().toJson(game);
        System.out.println("Storing extracted game: " + game.getGameName());
        TextWriter.writeAllText("database/extracted/games/" + gameReference + ".json", gameJson);
        gameId = game.getGameId();
        return game;
    }

    /**
     * To parse the json file of the reviews into data structure classes
     * @param rawHTML HTML of the game
     * @param crawler   The Crawler instance
     * @param extractor The extractor instance
     * @return  Array of IReview class objects parsed from the database json files.
     */
    private IReview[] extractAndStoreReviews(String rawHTML, IExtractableCrawler crawler, IReviewsExtractor extractor)
    {
        var extractedReviews = extractor.extractFrom(rawHTML);
        var reviewsJson = JsonSerialiser.DefaultInstance().toJson(extractedReviews);
        System.out.println("Storing extracted reviews: " + crawler.getDomain() + " " + crawler.getExtractionName() + " " + extractedReviews.length + " -> " + gameReference);
        TextWriter.writeAllText("database/extracted/reviews/" + gameReference + "/" + crawler.getDomain() + "_" + crawler.getExtractionName() + ".json", reviewsJson);
        return extractedReviews;
    }

    /**
     * Getter for the array of gamespot and metacritic users reviews of IReview type after concatenation of both arrays
     * @return Array of IReviews from GameSpot and Metacritic
     */
    public IReview[] getAllUserReviews()
    {
        return Stream.concat(Arrays.stream(gamespotUserReviews), Arrays.stream(metacriticUserReviews))
                .toArray(IReview[]::new);
    }

    /**
     * Getter for the array of Metacritic critics reviews of IReview type
     * @return Array of IReviews from Metacritic
     */
    public IReview[] getAllCriticReviews()
    {
        return metacriticCriticReviews;
    }

    /**
     * Produce a Dictionary which values are the Statistic classes after analysing, key 0 should be raw user data
     * key 1 should be raw user data without outliers.
     * If there are no acceptable outliers, key 2 will be critic stats values.
     * If there exists outliers, key 2 will be the acceptable outliers stats values and key 3 will be the critics.
     * @return Dictionary which values are the Statistic classes after analysing
     */
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

    /**
     * Getter to get Game class object which contains game information
     * @return Game class object which contains game information
     */
    public Game getGameInfo()
    {
        return gameInfo;
    }
}

package ricardo_crawlos.storage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import ricardo_crawlos.core.IExtractableCrawler;
import ricardo_crawlos.crawlers.TraversalCrawlerBase;

/**
 * CachedCrawler
 * This class will check if the cache of the crawl target exists in the file database, only crawl if it does not exist
 */
public class CachedGamesiteCrawler
{
    private IExtractableCrawler crawler;
    private String gamePathName;

    /**
     *
     * @param theCrawler The extractable crawler to run to crawl if cache is not found
     * @param theGamePathName The folder path name of the game
     */
    public CachedGamesiteCrawler(IExtractableCrawler theCrawler, String theGamePathName)
    {
        crawler = theCrawler;
        gamePathName = theGamePathName;
    }

    /**
     * Run the crawler and store the extracted crawled result to file database to cache it for faster subsequent access
     */
    private void storeToCache()
    {
        crawler.run();
        String[] cacheOutput = Arrays.stream(crawler.getTraversalResults()).map(x -> crawler.extractFrom(x.html())).toArray(String[]::new);

        TextWriter.writeAllText(getCachedPath(), JsonSerialiser.DefaultInstance().toJson(cacheOutput));
        System.out.println("Cached: " + getCachedPath());
    }

    /**
     * Get the html either through crawling or cached if has crawled before
     * @param progressSeeker Event listener to report the crawling progress, such as from a UI
     * @return The full html
     */
    public String getOrCacheHTML(Consumer<Double> progressSeeker)
    {
        // Cache exist
        if (Files.exists(Path.of(getCachedPath())))
        {
            try
            {
                String json = Files.readString(Path.of(getCachedPath()), StandardCharsets.UTF_8);
                String[] htmlStrings = JsonSerialiser.DefaultInstance().fromJson(json, String[].class);

                return Arrays.stream(htmlStrings).collect(Collectors.joining("\n"));
            }
            catch (IOException e)
            {
                e.printStackTrace();
                System.out.println("Cached Crawler IO failure for " + gamePathName);
                return null;
            }
        }
        // Cache does not exist
        else
        {
            if (crawler instanceof TraversalCrawlerBase)
            {
                ((TraversalCrawlerBase) crawler).addProgressListener(progressSeeker);
            }

            storeToCache();

            if (progressSeeker != null) progressSeeker.accept(1.0);

            if (crawler instanceof TraversalCrawlerBase)
            {
                ((TraversalCrawlerBase) crawler).removeProgressListener(progressSeeker);
            }

            return getOrCacheHTML(progressSeeker);
        }
    }

    /**
     * Get the file path to store the cache
     * @return the relative file path
     */
    public String getCachedPath()
    {
        return "database/cache/" + gamePathName + "/" + crawler.getWebsiteInfo().getDomain() + "_"
                + crawler.getExtractionName() + ".json";
    }
}

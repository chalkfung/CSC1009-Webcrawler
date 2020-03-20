package ricardo_crawlos.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import ricardo_crawlos.core.IExtractableCrawler;
import ricardo_crawlos.crawlers.TraversalCrawlerBase;

/**
 * CachedCrawler
 */
public class CachedGamesiteCrawler
{
    private IExtractableCrawler crawler;
    private String gamePathName;

    public CachedGamesiteCrawler(IExtractableCrawler theCrawler, String theGamePathName)
    {
        crawler = theCrawler;
        gamePathName = theGamePathName;
    }

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
        if (Files.exists(Path.of(getCachedPath())))
        {
            try
            {
                String json = Files.readString(Path.of(getCachedPath()));
                String[] htmlStrings = JsonSerialiser.DefaultInstance().fromJson(json, String[].class);

                return Arrays.stream(htmlStrings)
                        .collect(Collectors.joining("\n"));
            }
            catch (IOException e)
            {
                System.out.println("Cached Crawler IO failure for " + gamePathName);
                return null;
            }
        }
        else
        {
            if (crawler instanceof TraversalCrawlerBase)
            {
                ((TraversalCrawlerBase) crawler).addProgressListener(progressSeeker);
            }

            storeToCache();

            if (crawler instanceof TraversalCrawlerBase)
            {
                ((TraversalCrawlerBase) crawler).removeProgressListener(progressSeeker);
            }

            return getOrCacheHTML(progressSeeker);
        }
    }

    public String getCachedPath()
    {
        return "database/cache/" + gamePathName + "/" + crawler.getWebsiteInfo().getDomain() + "_"
                + crawler.getExtractionName() + ".json";
    }
}

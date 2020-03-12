package ricardo_crawlos.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;

import ricardo_crawlos.core.IDomainSegmentClawer;
import ricardo_crawlos.core.IWebsite;

/**
 * CachedCrawler
 */
public class CachedGameSiteCrawler
{
    private IDomainSegmentClawer crawler;
    private String gamePathName;

    public CachedGameSiteCrawler(IDomainSegmentClawer theCrawler, String theGamePathName)
    {
        crawler = theCrawler;
        gamePathName = theGamePathName;
    }

    private void storeToCache()
    {
        crawler.run();
        String[] cacheOutput = Arrays.stream(crawler.getTraversalResults()).map(x -> x.html()).toArray(String[]::new);

        TextWriter.writeAllText(getCachedPath(), JsonSerialiser.DefaultInstance().toJson(cacheOutput));
    }

    public String getOrCacheHTML()
    {
        if (Files.exists(Path.of(getCachedPath())))
        {
            try
            {
                String json = Files.readString(Path.of(getCachedPath()));
                String[] htmlStrings = JsonSerialiser.DefaultInstance().fromJson(json, String[].class);

                return Arrays.stream(htmlStrings)
                        .map(crawler::extractFrom)
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
            storeToCache();
            return getOrCacheHTML();
        }
    }

    public String getCachedPath()
    {
        return "database/cache/" + gamePathName + "/" + crawler.getWebsiteInfo().getDomain() + "_"
                + crawler.getSegmentName() + ".json";
    }
}

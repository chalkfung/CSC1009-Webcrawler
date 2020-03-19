package ricardo_crawlos.core;

import java.util.function.Function;

/**
 * IPaginatedCrawler
 */
public interface IPaginatedCrawler extends IExtractableCrawler
{
    String fromSubpage(String subPath);

    int getPageCount();
}

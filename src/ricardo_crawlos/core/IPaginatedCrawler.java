package ricardo_crawlos.core;

/**
 * IPaginatedCrawler
 */
public interface IPaginatedCrawler extends IExtractableCrawler
{
    String fromSubpage(String subPath);

    int getPageCount();
}

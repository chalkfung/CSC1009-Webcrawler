package ricardo_crawlos.core;

/**
 * IPaginatedCrawler
 * A paginated crawler crawls all the related pages which is broken up into page numbers
 */
public interface IPaginatedCrawler extends IExtractableCrawler
{
    String fromSubpage(String subPath);

    int getPageCount();
}

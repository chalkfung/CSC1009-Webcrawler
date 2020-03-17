package ricardo_crawlos.core;

public interface IExtractableCrawler extends ICrawler, IExtractable<String, String>
{
    IWebsite getWebsiteInfo();

    String getDomain();

    /**
     * Path name is a identifier name to be reference external, may not be a actual path of the website
     * Such as "user-reviews" indicate this paginated crawler is a kind of user-review crawler, then will be used by
     * storage to store to a file name containing "user-reviews:
     */
    String getExtractionName();
}

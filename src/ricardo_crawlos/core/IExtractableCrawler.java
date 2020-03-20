package ricardo_crawlos.core;

public interface IExtractableCrawler extends ICrawler, IExtractable<String, String>
{
    /**
     * @return The basic information of the website
     */
    IWebsite getWebsiteInfo();

    /**
     * @return The domain the site it is crawling
     */
    String getDomain();

    /**
     * Path name is a identifier name to be referenced internally, such as in the file database,
     * May not be a actual path of the website
     * Such as "user-reviews" indicate that this paginated crawler is a kind of user-review crawler, then will be used by
     * storage to store to a file name containing "user-reviews:
     */
    String getExtractionName();
}

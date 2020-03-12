package ricardo_crawlos.core;

/**
 * IReviewsExtractor
 */
public interface IReviewsExtractor
{
    IWebsite getWebsiteInfo();
    IReview[] extract(String html);
}
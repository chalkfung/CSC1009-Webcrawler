package ricardo_crawlos.core;

/**
 * IReviewsExtractor
 */
public interface IReviewsExtractor extends IExtractable<String, IReview[]>
{
    IWebsite getWebsiteInfo();
}
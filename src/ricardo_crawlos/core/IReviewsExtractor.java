package ricardo_crawlos.core;

/**
 * IReviewsExtractor
 * Extractor which is to extract reviews from a particular crawling result
 */
public interface IReviewsExtractor extends IExtractable<String, IReview[]>
{
    IWebsite getWebsiteInfo();
}

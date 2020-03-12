package ricardo_crawlos.core;

/**
 * IDomainPath
 */
public interface IDomainSegmentClawer extends ICrawler
{
    IWebsite getWebsiteInfo();
    
    String getDomain();

    String getSegmentName();

    String fromSubpage(String subPath);
}
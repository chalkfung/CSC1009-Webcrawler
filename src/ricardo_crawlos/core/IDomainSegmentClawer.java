package ricardo_crawlos.core;

import java.util.function.Function;

/**
 * IDomainPath
 */
public interface IDomainSegmentClawer extends ICrawler, IExtractable<String, String>
{
    IWebsite getWebsiteInfo();
    
    String getDomain();

    String getSegmentName();

    String fromSubpage(String subPath);
}
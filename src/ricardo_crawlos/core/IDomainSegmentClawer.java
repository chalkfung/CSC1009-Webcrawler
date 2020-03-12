package ricardo_crawlos.core;

/**
 * IDomainPath
 */
public interface IDomainSegmentClawer extends ICrawler
{
    String getDomain();

    String getSegmentName();

    String fromSubpage(String subPath);
}
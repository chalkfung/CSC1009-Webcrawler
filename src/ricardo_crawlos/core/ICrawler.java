package ricardo_crawlos.core;

import org.jsoup.nodes.Document;

/**
 * ICrawler
 * The interface all crawling class will implement
 */
public interface ICrawler extends Runnable
{
    /**
     * Gets the links available explore next from the initial page
     */
    String[] getTraversableLinks();

    /**
     * @return The links explored after calling the run
     */
    String[] getTraversedLinks();

    /**
     * @return The JSOUP HTML documents of the explored pages after the run
     */
    Document[] getTraversalResults();
}

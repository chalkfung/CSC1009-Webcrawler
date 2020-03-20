package ricardo_crawlos.crawlers;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import ricardo_crawlos.core.ICrawler;

/**
 * TraversalCrawlerBase
 */
public abstract class TraversalCrawlerBase implements ICrawler
{
    protected final Set<String> links;
    protected final Queue<Document> traversalResults;

    /**
     * Event listen to report the current crawling progress
     */
    protected List<Consumer<Double>> onProgressListeners;

    /**
     * Using atomic counter as crawling is parallelised to prevent race conditions
     */
    private AtomicInteger maxPageSeen = new AtomicInteger(0);

    public TraversalCrawlerBase()
    {
        // Using concurrent data structures are crawling is parallelised
        links = Collections.newSetFromMap(new ConcurrentHashMap<>());
        traversalResults = new ConcurrentLinkedQueue<>();
        onProgressListeners = new ArrayList<>();
    }

    /**
     * Progress event subscription point
     */
    public void addProgressListener(Consumer<Double> listener)
    {
        if (listener == null)
        {
            return;
        }
        onProgressListeners.add(listener);
    }

    /**
     * Progress event unsubscription point
     */
    public void removeProgressListener(Consumer<Double> listener)
    {
        if (listener == null)
        {
            return;
        }
        onProgressListeners.remove(listener);
    }

    /**
     * Traversal filter condition to be implemented by child classes
     */
    protected abstract boolean canTraverse(String url);

    /**
     * Get all absolute links from elements with href attribute
     */
    public String[] getTraversableLinks(Document document)
    {
        Elements linksOnPage = document.select("a[href]");
        return linksOnPage
                .stream()
                .map(x -> x.attr("abs:href"))
                .filter(this::canTraverse)
                .distinct()
                .toArray(String[]::new);
    }

    /**
     * Estimate the current recursive crawling process based on the largest paging number seen
     * @param currentUrl Current crawled url
     * @return Progress from 0.0 ~ 1.0
     */
    public double estimateTraversalProgress(String currentUrl)
    {
        try
        {
            var currentPage = Integer.parseInt(currentUrl.split("page=")[1]);
            if (maxPageSeen.get() < currentPage)
            {
                maxPageSeen.set(currentPage);
            }
            // Just return 0 of the max page seen is 3, the UI will not display the progress as it is probably not accurate yet
            if (maxPageSeen.get() < 3)
            {
                return 0;
            }
            return (traversalResults.size() / ((double) maxPageSeen.get()));
        }
        catch (Exception e)
        {
            return 0;
        }
    }

    /**
     * Note: Concurrent and recursive
     *
     * @param url The url to the page to look for more urls
     */
    protected void traverse(String url)
    {
        // Not to proceed if the set already contains the url, which means it has been crawled
        if (links.add(url))
        {
            var progress = estimateTraversalProgress(url);
            onProgressListeners.forEach(x -> x.accept(progress));
            System.out.println("Traversing: " + (progress == 0 ? "" : String.format("est %.2f%% ", progress * 100)) + url);
            try
            {
                // Load the html document using jsoup
                Document document = Jsoup.connect(url).get();

                // Store the document to crawled result
                traversalResults.add(document);

                document.select("a[href]") // Select all anchors
                        .parallelStream() // Using concurrency to speed up crawling
                        .map(x -> x.attr("abs:href")) // Get the url of the anchors
                        .filter(this::canTraverse) // Check if the url is valid candidate for the crawling context
                        .sorted(Comparator.comparing((String x) -> x).reversed()) // Crawl the furthest page first
                        .forEach(this::traverse); // Recursively call this function for each links
            }
            catch (IOException e)
            {
                System.err.println("For '" + url + "': " + e.getMessage());
            }
        }
    }

    @Override
    public String[] getTraversedLinks()
    {
        return links.toArray(String[]::new);
    }

    @Override
    public Document[] getTraversalResults()
    {
        return traversalResults.toArray(Document[]::new);
    }

    @Override
    public String toString()
    {
        return "Links traversed: " + links.size() + "\nAll links:\n\t" + String.join("\n\t", getTraversedLinks());
    }
}

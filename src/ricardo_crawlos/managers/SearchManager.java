package ricardo_crawlos.managers;

import ricardo_crawlos.core.ISearchContext;

/**
 * SearchManager class
 * Handles the functionality of searching
 */
public class SearchManager
{
    private String referenceKEy;

    /**
     * SearchManager constructor and formats the input string
     * @param key String which user input onto the search bar
     * @throws Error Throws exception if user empties nothing
     */
    public SearchManager(String key) throws Error
    {
        if (key == null || key.equals(""))
        {
            throw new Error("Bad input");
        }
        key = key.toLowerCase();
        this.referenceKEy = key.replaceAll(" ", "-");
    }

    /**
     * Returns the class which contains the formatted string for UI to use
     * @return ISearchContext class for the UI to use
     */
    public ISearchContext retrieve()
    {
        return new SearchContextGamePC(referenceKEy);
    }

    /**
     * Formatted string after constructing
     * @return Formatted string after user input on search bar
     */
    public String getGameReference()
    {
        return referenceKEy;
    }
}

package ricardo_crawlos.managers;

import ricardo_crawlos.core.ISearchContext;

public class SearchManager
{
    private String referenceKEy;

    public SearchManager(String key) throws Error
    {
        if (key == null || key.equals(""))
        {
            throw new Error("Bad input");
        }
        key = key.toLowerCase();
        this.referenceKEy = key.replaceAll(" ", "-");
    }

    public ISearchContext retrieve()
    {
        return new SearchContextGamePC(referenceKEy);
    }

    public String getGameReference()
    {
        return referenceKEy;
    }
}

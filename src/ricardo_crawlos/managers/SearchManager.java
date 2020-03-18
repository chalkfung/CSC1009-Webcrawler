package ricardo_crawlos.managers;

import ricardo_crawlos.core.ISearchContext;

public class SearchManager
{
    private String gameSpotKey;
    private String metaGamePCKey = "game/pc/";

    public SearchManager(String key)
    {
        key = key.toLowerCase();
        this.gameSpotKey = key.replaceAll(" ", "-");
        this.metaGamePCKey += this.gameSpotKey;
    }

    public ISearchContext retrieve()
    {
        return new SearchContextGamePC(gameSpotKey);
    }
}

package ricardo_crawlos.managers;

public class GameSearchManager
{
    private String gameSpotKey;
    private String metaKey = "game/pc/";

    public GameSearchManager(String key)
    {
        key = key.toLowerCase();
        this.gameSpotKey = key.replaceAll(" ", "-");
        this.metaKey += this.gameSpotKey;
    }

    public String getGameSpotKey()
    {
        return this.gameSpotKey;
    }

    public String getMetaKey()
    {
        return this.metaKey;
    }
}

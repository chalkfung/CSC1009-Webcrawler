package ricardo_crawlos.managers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import ricardo_crawlos.models.Game;
import ricardo_crawlos.storage.JsonSerialiser;

/**
 * GameManager class to handle the game information and provide an game ID for the scraped data.
 * Singleton class such that there should not be conflicting instances.
 */
public class GameManager extends ItemStoreManagerBase<Game, String>
{
    private static GameManager gameManagerInstance = null;

    /**
     * The storage path of the IDs bound to the game information
     * @return String path on the disk
     */
    @Override
    protected String getStoragePath()
    {
        return "database/managers/game_manager.json";
    }

    /**
     * Constructor for GameManager
     */
    private GameManager()
    {
        super();
    }

    /**
     * Get the Game class object given an id input
     * @param id Integer value which is mapped to a game.
     * @return Game class object
     */
    @Override
    public Game getFromID(int id)
    {
        try
        {
            var name = reverseLookupMap.get(id);
            var cached = Files.readString(Path.of("database/extracted/games/" + new SearchManager(name).getGameReference() + ".json"), StandardCharsets.UTF_8);
            return JsonSerialiser.DefaultInstance().fromJson(cached, Game.class);
        }
        catch (IOException e)
        {
            System.out.println("Game id not found: " + id);
            return null;
        }
    }

    /**
     * getInstance such that the constructor will only be called once through this function, which makes this class
     * singleton
     * @return GameManager class object
     */
    public static GameManager getInstance()
    {
        if (gameManagerInstance == null)
            gameManagerInstance = new GameManager();
        return gameManagerInstance;
    }
}

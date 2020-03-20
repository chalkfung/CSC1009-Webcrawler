package ricardo_crawlos.managers;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import ricardo_crawlos.models.Game;
import ricardo_crawlos.storage.JsonSerialiser;

public class GameManager extends ItemStoreManagerBase<Game, String>
{
    private static GameManager gameManagerInstance = null;

    @Override
    protected String getStoragePath()
    {
        return "database/managers/game_manager.json";
    }

    private GameManager()
    {
        super();
    }

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

    public static GameManager getInstance()
    {
        if (gameManagerInstance == null)
            gameManagerInstance = new GameManager();
        return gameManagerInstance;
    }
}

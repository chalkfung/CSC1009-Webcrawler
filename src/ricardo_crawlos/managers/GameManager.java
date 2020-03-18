package ricardo_crawlos.managers;

import ricardo_crawlos.core.IManager;
import ricardo_crawlos.models.Game;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.reflect.TypeToken;

public class GameManager implements IManager<Game, String>
{

    private static GameManager gameManagerInstance = null;
    private Map<String, Integer> gameMap;

    private String savePath = "database/managers/game_manager.json";

    private GameManager()
    {
        gameMap = new HashMap<>();

        var path = Path.of(savePath);
        if (Files.exists(path))
        {
            try
            {
                Type type = new TypeToken<Map<String, Integer>>(){}.getType();
                gameMap = JsonSerialiser.DefaultInstance().fromJson(Files.readString(path), type);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static GameManager getInstance()
    {
        if (gameManagerInstance == null)
            gameManagerInstance = new GameManager();
        return gameManagerInstance;
    }

    public int getID(String name)
    {
        return this.gameMap.get(name);
    }

    public boolean isExist(String name)
    {
        return this.gameMap.containsKey(name);
    }

    public void append(String name)
    {
        if (!isExist(name))
        {
            this.gameMap.put(name, this.gameMap.size());
            var serialised = JsonSerialiser.DefaultInstance().toJson(this.gameMap);
            TextWriter.writeAllText(savePath, serialised);
        }
    }
}

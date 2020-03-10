package ricardo_crawlos.managers;

import ricardo_crawlos.core.IManager;
import ricardo_crawlos.models.Game;
import java.util.HashMap;

public class GameManager implements IManager<Game,String>{

    private static GameManager gameManagerInstance = null;
    private HashMap<String, Integer> gameMap;

    private GameManager()
    {
        this.gameMap = new HashMap<String, Integer>();
    }

    public static GameManager getInstance()
    {
        if(gameManagerInstance == null)
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
        this.gameMap.putIfAbsent(name, this.gameMap.size());
    }
}

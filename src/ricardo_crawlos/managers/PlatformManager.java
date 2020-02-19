package ricardo_crawlos.managers;

import ricardo_crawlos.core.IManager;
import ricardo_crawlos.models.Platform;
import java.util.HashMap;

public class PlatformManager implements IManager<Platform, String > {

    private static PlatformManager pmInstance = null;

    private HashMap<String, Integer> platformList;

    private PlatformManager()
    {
        platformList = new HashMap<String, Integer>();
    }

    public static PlatformManager getInstance()
    {
        if(pmInstance == null)
            pmInstance = new PlatformManager();
        return pmInstance;
    }


    public int getID(String name)
    {
        return this.platformList.get(name);
    }


    public boolean isExist(String name) {
        return this.platformList.containsKey(name);
    }


    public void append(String name)
    {
        this.platformList.putIfAbsent(name, this.platformList.size());
    }
}

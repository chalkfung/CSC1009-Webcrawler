package ricardo_crawlos.managers;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.reflect.TypeToken;

import ricardo_crawlos.core.IManager;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;

public abstract class ItemStoreManagerBase<T, U> implements IManager<T, U>
{
    protected Map<U, Integer> itemMap;
    protected Map<Integer, U> reverseLookupMap;

    protected abstract String getStoragePath();

    public ItemStoreManagerBase()
    {
        itemMap = new HashMap<>();
        reverseLookupMap = new HashMap<>();

        var path = Path.of(getStoragePath());
        if (Files.exists(path))
        {
            try
            {
                Type type = new TypeToken<Map<U, Integer>>(){}.getType();
                itemMap = JsonSerialiser.DefaultInstance().fromJson(Files.readString(path, StandardCharsets.UTF_8), type);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        itemMap.keySet().forEach(key ->  reverseLookupMap.put(itemMap.get(key), key));
    }

    public int getID(U name)
    {
        return this.itemMap.get(name);
    }

    public boolean isExist(U name)
    {
        return this.itemMap.containsKey(name);
    }

    public void append(U name)
    {
        if (!isExist(name))
        {
            var id =  this.itemMap.size();
            this.itemMap.put(name, id);
            this.reverseLookupMap.put(id, name);
            var serialised = JsonSerialiser.DefaultInstance().toJson(this.itemMap);
            TextWriter.writeAllText(getStoragePath(), serialised);
        }
    }

    public abstract T getFromID(int id);
}

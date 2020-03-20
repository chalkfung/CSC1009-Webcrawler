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

/**
 * A manager is a lookup helper of a value which can be represented by 2 related types, mapped to a int ID
 * This class is also serialised into the file system for persistent storage throughout future application usage
 * @param <T> The primary type i.e the data model
 * @param <U> The secondary type i.e the unique name in String
 */
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

        // Retrieve previous data from the file system is there is any
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

            // Serialise into file system
            var serialised = JsonSerialiser.DefaultInstance().toJson(this.itemMap);
            TextWriter.writeAllText(getStoragePath(), serialised);
        }
    }

    public abstract T getFromID(int id);
}

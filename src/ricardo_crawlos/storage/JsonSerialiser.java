package ricardo_crawlos.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSerialiser
{
    private static Gson defaultSerialiser = new GsonBuilder()
        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        .setPrettyPrinting()
        .create();

    public static Gson DefaultInstance()
    {
        return defaultSerialiser;
    }
}

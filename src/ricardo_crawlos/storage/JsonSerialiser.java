package ricardo_crawlos.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonSerialiser
{
    /**
     * The default GSON JSON serialiser the application is using which has ISO date format and pretty printing
     */
    private static Gson defaultSerialiser = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .setPrettyPrinting()
            .create();

    /**
     * Get the default serialiser instance
     * @return GSON JSON instance
     */
    public static Gson DefaultInstance()
    {
        return defaultSerialiser;
    }
}

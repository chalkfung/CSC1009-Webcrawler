import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Arrays;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ricardo_crawlos.models.Game;

public class Main
{
    public static void main(String[] args)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Game game = new Game("doto 3", 1, "volvo", Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3), new Date());

        String serialised = gson.toJson(game);
        Game deserialised = gson.fromJson(serialised, Game.class);

        System.out.println("Serialised: " + serialised);
        System.out.println("Deserialised: " + gson.toJson(deserialised));

        writeAllText("./database/extracted/games/gameName_.json", serialised);
    }

    public static void writeAllText(String path, String text)
    {
        try
        {
            FileWriter writer = new FileWriter(path);
            writer.write(text);
            writer.close();
        }
        catch (FileNotFoundException e)
        {
            File file = new File(path);
            try
            {
                file.getParentFile().mkdirs();
                file.createNewFile();
                writeAllText(path, text);
            }
            catch (Exception e1)
            {
                System.out.println("File Creation Error: " + e1.getClass() + " " + e1.getMessage());
            }
        }
        catch (Exception e)
        {
            System.out.println("File Write Error: " + e.getClass() + " "+ e.getMessage());
        }
    }
}

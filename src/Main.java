import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ricardo_crawlos.models.Game;
import ricardo_crawlos.storage.TextWriter;

public class Main
{
    public static void main(String[] args)
    {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Game doto = new Game("doto 3", 1, "volvo", Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3), new Date());
        Game hl3 = new Game("hl 3", 1, "volvo", Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3), new Date());

        TextWriter.writeAllText("./database/extracted/games/doto.json", gson.toJson(doto));
        TextWriter.writeAllText("./database/extracted/games/hl3.json", gson.toJson(hl3));
    }
}

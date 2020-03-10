import java.util.Arrays;
import java.util.Date;

import ricardo_crawlos.models.Game;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;

public class Main
{
    public static void main(String[] args)
    {
        Game doto = new Game("doto 3", 1, "volvo", Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3), new Date());
        Game hl3 = new Game("hl 3", 1, "volvo", Arrays.asList(1, 2, 3), Arrays.asList(1, 2, 3), new Date());

        TextWriter.writeAllText("./database/extracted/games/doto.json", JsonSerialiser.DefaultInstance().toJson(doto));
        TextWriter.writeAllText("./database/extracted/games/hl3.json", JsonSerialiser.DefaultInstance().toJson(hl3));
    }
}

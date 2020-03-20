package ricardo_crawlos.storage;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TextWriter
{
    public static void writeAllText(String path, String text)
    {
        try
        {
            var writer = new PrintWriter(path, StandardCharsets.UTF_8);
//            FileWriter writer = new FileWriter(path);
            writer.println(text);
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
            System.out.println("File Write Error: " + e.getClass() + " " + e.getMessage());
        }
    }
}

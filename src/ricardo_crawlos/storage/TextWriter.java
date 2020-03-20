package ricardo_crawlos.storage;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class TextWriter
{
    /**
     * Helper function to write to a file, create its directories if they does not exist, if the file exists it will override
     * @param path File path and extension
     * @param text Text String to write
     */
    public static void writeAllText(String path, String text)
    {
        try
        {
            var writer = new PrintWriter(path, StandardCharsets.UTF_8);
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

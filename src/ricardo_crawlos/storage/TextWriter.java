package ricardo_crawlos.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class TextWriter
{
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
            System.out.println("File Write Error: " + e.getClass() + " " + e.getMessage());
        }
    }
}

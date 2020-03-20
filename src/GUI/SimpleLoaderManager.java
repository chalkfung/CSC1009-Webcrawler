package GUI;

import javax.swing.*;
import java.awt.*;

interface ThrowableSupplier
{
    String get() throws Exception;
}

public class SimpleLoaderManager
{
    private static JLabel instanceLabel = null;

    public static void setLoaderString(String input)
    {
        if (instanceLabel != null)
        {
            instanceLabel.setText(input);
        }
    }

    public static void StartLoadingInstance(JFrame jframe, ThrowableSupplier workerSupplier, Runnable onDone)
    {
        if (instanceLabel != null)
        {
            System.out.println("There is already a loading progress.");
            return;
        }
        JDialog load = new JDialog(jframe, true);
        load.getContentPane().setBackground(Color.gray);

        JLabel gifLabel = new JLabel("Loading!");
        gifLabel.setIcon(new ImageIcon(jframe.getClass().getResource("/GUI/Image/25.gif")));
        instanceLabel = gifLabel;

        load.getContentPane().add(gifLabel);
        load.setUndecorated(true);
        load.setBounds(300, 300, 50, 50);
        load.setSize(165, 75);
        load.setLocationRelativeTo(jframe);

        SwingWorker<String, Void> s_worker = new SwingWorker<String, Void>()
        {
            @Override
            protected String doInBackground() throws Exception
            {
                return workerSupplier.get();
            }

            @Override
            protected void done()
            {
                super.done();
                instanceLabel = null;
                load.dispose();

                if (onDone != null)
                {
                    onDone.run();
                }
            }
        };

        s_worker.execute();

        load.setVisible(true);
        try
        {
            s_worker.get();
        }
        catch (Exception swing_exception)
        {
            System.out.print("Error Message: " + swing_exception.getMessage());
        }
    }
}

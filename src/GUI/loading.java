package GUI;

import javax.swing.JDialog;
import javax.swing.SwingWorker;

public class loading extends SwingWorker<Integer, Integer>
{
    private JDialog load;

    public loading(JDialog load)
    {
        this.load = load;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        // TODO Auto-generated method stub
        Thread.sleep(2000);
        load.dispose();
        return null;
    }
}
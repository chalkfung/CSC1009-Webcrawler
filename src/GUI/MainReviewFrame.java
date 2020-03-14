package GUI;


import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainReviewFrame extends JFrame {

    private static final long serialVersionUID = 1L;

    public MainReviewFrame() {

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Game Review Analyser");
        setSize(1000, 840);
        setLocationRelativeTo(null);
        JPanel panel = new GameReviewHomePanel(this);
        setContentPane(panel);
        setResizable(false);
        setVisible(true);
    }

//    public static void main(String[] args) {
//        EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                try {
//                    MainReviewFrame frame = new MainReviewFrame();
//                    frame.setVisible(true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
}
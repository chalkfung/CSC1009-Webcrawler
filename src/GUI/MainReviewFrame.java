package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainReviewFrame extends JFrame 
{
	private static final long serialVersionUID = 1L;

	public MainReviewFrame() 
	{
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Game Review Analyser");
		setSize(1000, 840);
		setLocationRelativeTo(null);
		JPanel panel = new GameReviewHomePanel(this);
		setContentPane(panel);
		setResizable(false);
        setVisible(true);
	}
}

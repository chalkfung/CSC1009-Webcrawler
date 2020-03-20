package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * MainReviewFrame class inherits from JFrame
 */
public class MainReviewFrame extends JFrame 
{
	private static final long serialVersionUID = 1L;

	/**
	 * Settings for the JFrame like setting the Title "Game Review Analyser", the size of the frame to be 1000 by 840,
	 * disable the option to be in fullscreen (setResizable)
	 */
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

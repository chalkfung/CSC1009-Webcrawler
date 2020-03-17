package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameReviewHomePanel extends JPanel  
{

	private static final long serialVersionUID = 1L;
	private JTextField txtSearchYourGame;

	public GameReviewHomePanel(JFrame jframe) 
	{
		setBackground(new Color(25, 32, 96));
		setLayout(null);
		setSize(1000, 800);

		txtSearchYourGame = new JTextField();
		txtSearchYourGame.setToolTipText("Search your game by typing the game name");
		txtSearchYourGame.setFont(new Font("Consolas", Font.PLAIN, 29));
		txtSearchYourGame.setBounds(223, 450, 588, 50);
		add(txtSearchYourGame);

		JButton search_button = new JButton("Search");
		search_button.setForeground(Color.WHITE);
		search_button.setFont(new Font("Consolas", Font.PLAIN, 31));
		search_button.setBackground(new Color(101, 101, 238));
		search_button.setBounds(433, 574, 164, 50);
		add(search_button);

		JLabel logo = new JLabel("");
		logo.setIcon(new ImageIcon(GameReviewHomePanel.class.getResource("/GUI/Image/Game Review.png")));
		logo.setBounds(261, 169, 500, 226);
		add(logo);		

		search_button.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				JDialog load = new JDialog(jframe, true);
				load.getContentPane().setBackground(Color.gray);

				JLabel gifLabel = new JLabel("Fetching Data!"); 
				gifLabel.setIcon(new ImageIcon(this.getClass().getResource("/GUI/Image/25.gif")));

				load.getContentPane().add(gifLabel); 
				load.setUndecorated(true);
				load.setBounds(300, 300, 50, 50);
				load.setLocationRelativeTo(jframe);
				load.pack(); 

				SwingWorker<String, Void> s_worker = new SwingWorker<String, Void>() 
				{

					@Override
					protected String doInBackground() throws Exception 
					{
						// TODO Auto-generated method stub
						// Search Method
						String keyword = txtSearchYourGame.getText();
						System.out.println(keyword);

						Thread.sleep(3000);
						return null;
					}

					@Override
					protected void done()
					{
						load.dispose();
					}
				};

				s_worker.execute();
				load.setVisible(true);
				try 
				{
					s_worker.get();
				}catch(Exception swing_exception){
					System.out.print("Error Message: " + swing_exception.getMessage());
				}

				GameReviewInformationPanel infoPanel = new GameReviewInformationPanel(jframe);
				jframe.setContentPane(infoPanel);
			}				
		});
	}
}

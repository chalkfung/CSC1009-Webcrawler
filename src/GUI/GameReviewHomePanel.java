package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jsoup.HttpStatusException;

import ricardo_crawlos.core.ISearchContext;
import ricardo_crawlos.managers.SearchManager;

/**
 * GameReviewHomePanel class inherits JPanel
 */
public class GameReviewHomePanel extends JPanel
{
    private final int SLEEPTIME = 0;
    private static final long serialVersionUID = 1L;
    private JTextField txtSearchYourGame;
    private JFrame jframe;

    /**
     * This constructor is the home page of the GUI, it sets up the search field, search button and logo.
     * It will display the loading gif with the help of the SwingWorker while the system is searching, analysing etc
     * and stops the gif when the data is finalised.
     *
     * @param jframe Using the settings from the MainReviewFrame
     */
    public GameReviewHomePanel(JFrame jframe)
    {
        this.jframe = jframe;
        setBackground(new Color(25, 32, 96));
        setLayout(null);
        setSize(1000, 800);

        txtSearchYourGame = new JTextField();
        txtSearchYourGame.setToolTipText("Enter Game Title");
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
                SimpleLoaderManager.StartLoadingInstance(jframe, () -> doSearch(), null);
            }
        });

        txtSearchYourGame.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    SimpleLoaderManager.StartLoadingInstance(jframe, () -> doSearch(), null);
                }
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
            }
        });
    }

    private String doSearch() throws InterruptedException
    {
        // Search Method
        String keyword = txtSearchYourGame.getText();

        SimpleLoaderManager.setLoaderString("Searching!");

        SearchManager searchManager;
        try
        {
            searchManager = new SearchManager(keyword);
        }
        catch (Error e)
        {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        ISearchContext searchContext = searchManager.retrieve();

        try
        {
            searchContext.probe();
        }
        catch (IOException e)
        {
            System.out.println(e.getMessage());
            if (e instanceof HttpStatusException)
            {
                var httpException = (HttpStatusException) e;
                if (httpException.getStatusCode() == 404)
                {
                    JOptionPane.showMessageDialog(null, "Unable to find the game", "Search Error", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "HTTP Raised Status: " + httpException.getStatusCode(), "Search Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "IO Exception: " + e.getMessage(), "Search Error", JOptionPane.ERROR_MESSAGE);
            }
            GameReviewHomePanel searchPanel = new GameReviewHomePanel(jframe);
            jframe.setContentPane(searchPanel);
            return null;
        }

        Thread.sleep(SLEEPTIME);
        SimpleLoaderManager.setLoaderString("Fetching Data!");

        try
        {
            searchContext.fetch(p ->
            {
                if (p != 0) SimpleLoaderManager.setLoaderString(String.format("Est %.2f%%", p * 100));
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Please try again later.", "Fetch Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Thread.sleep(SLEEPTIME);
        SimpleLoaderManager.setLoaderString("Extracting Data!");

        try
        {
            searchContext.extract();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Please try another game. The web layout may have changed.", "Extraction Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        Thread.sleep(SLEEPTIME);
        SimpleLoaderManager.setLoaderString("Analysing Data!");

        try
        {
            GameReviewInformationPanel infoPanel = new GameReviewInformationPanel(jframe, searchContext.analyse(), searchContext);
            jframe.setContentPane(infoPanel);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Please try another game.", "Analysis Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return null;
    }
}

package GUI;

import ricardo_crawlos.core.ISearchContext;
import ricardo_crawlos.managers.*;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import org.jsoup.HttpStatusException;


public class GameReviewHomePanel extends JPanel
{
    private final int SLEEPTIME = 0;
    private static final long serialVersionUID = 1L;
    private JTextField txtSearchYourGame;

    public GameReviewHomePanel(JFrame jframe)
    {
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
                JDialog load = new JDialog(jframe, true);
                load.getContentPane().setBackground(Color.gray);

                JLabel gifLabel = new JLabel("Searching!");
                gifLabel.setIcon(new ImageIcon(this.getClass().getResource("/GUI/Image/25.gif")));

                load.getContentPane().add(gifLabel);
                load.setUndecorated(true);
                load.setBounds(300, 300, 50, 50);
                load.setLocationRelativeTo(jframe);
                load.setSize(165, 75);

                SwingWorker<String, Void> s_worker = new SwingWorker<String, Void>()
                {

                    @Override
                    protected String doInBackground() throws InterruptedException
                    {
                        // TODO Auto-generated method stub
                        // Search Method
                        String keyword = txtSearchYourGame.getText();
                        SearchManager searchManager = new SearchManager(keyword);
//						System.out.println(key.getGameSpotKey() + "\n" + key.getMetaKey());
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

                        gifLabel.setText("Fetching Data!");
                        searchContext.fetch();

                        Thread.sleep(SLEEPTIME);
                        gifLabel.setText("Extracting Data!");
						searchContext.extract();
                        Thread.sleep(SLEEPTIME);
                        gifLabel.setText("Analysing Data!");
                        GameReviewInformationPanel infoPanel = new GameReviewInformationPanel(jframe, searchContext.analyse());
                        jframe.setContentPane(infoPanel);
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
                }
                catch (Exception swing_exception)
                {
                    System.out.print("Error Message: " + swing_exception.getMessage());
                }
            }
        });

        txtSearchYourGame.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e){}

            @Override
            public void keyPressed(KeyEvent e)
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    JDialog load = new JDialog(jframe, true);
                    load.getContentPane().setBackground(Color.gray);

                    JLabel gifLabel = new JLabel("Searching!");
                    gifLabel.setIcon(new ImageIcon(this.getClass().getResource("/GUI/Image/25.gif")));

                    load.getContentPane().add(gifLabel);
                    load.setUndecorated(true);
                    load.setBounds(300, 300, 50, 50);
                    load.setLocationRelativeTo(jframe);
                    load.setSize(165, 75);

                    SwingWorker<String, Void> s_worker = new SwingWorker<String, Void>()
                    {

                        @Override
                        protected String doInBackground() throws InterruptedException
                        {
                            // TODO Auto-generated method stub
                            // Search Method
                            String keyword = txtSearchYourGame.getText();
                            SearchManager searchManager = new SearchManager(keyword);
//						System.out.println(key.getGameSpotKey() + "\n" + key.getMetaKey());
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

                            gifLabel.setText("Fetching Data!");
                            searchContext.fetch();

                            Thread.sleep(SLEEPTIME);
                            gifLabel.setText("Extracting Data!");
                            searchContext.extract();
                            Thread.sleep(SLEEPTIME);
                            gifLabel.setText("Analysing Data!");
                            GameReviewInformationPanel infoPanel = new GameReviewInformationPanel(jframe, searchContext.analyse());
                            jframe.setContentPane(infoPanel);
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
                    }
                    catch (Exception swing_exception)
                    {
                        System.out.print("Error Message: " + swing_exception.getMessage());
                    }
                }

            }

            @Override
            public void keyReleased(KeyEvent e){}
        });
    }
}

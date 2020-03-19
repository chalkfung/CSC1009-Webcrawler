package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;

import ricardo_crawlos.core.IReview;
import ricardo_crawlos.core.ISearchContext;
import ricardo_crawlos.models.CriticReview;
import ricardo_crawlos.models.UserReview;
import ricardo_crawlos.utilities.Statistics;

public class GameReviewInformationPanel extends JPanel
{
    private static final long serialVersionUID = 1L;

    public GameReviewInformationPanel(JFrame jframe, Dictionary<Integer, Statistics<Double, IReview>> results, ISearchContext context)
    {
        setBackground(Color.WHITE);

        setLayout(null);
        setSize(1000, 800);

        JLabel header_label = new JLabel("");
        header_label.setIcon(new ImageIcon(GameReviewInformationPanel.class.getResource("/GUI/Image/HeaderLogo.png")));
        header_label.setBounds(0, 0, 1000, 100);
        add(header_label);

        JLabel game_name_label = new JLabel("Game Name");
        game_name_label.setFont(new Font("Tahoma", Font.PLAIN, 26));
        game_name_label.setBounds(15, 100, 200, 40);
        add(game_name_label);

        JLabel description_label = new JLabel("Game Information");
        description_label.setFont(new Font("Tahoma", Font.PLAIN, 26));
        description_label.setBounds(15, 170, 400, 40);
        add(description_label);


        JLabel count_label = new JLabel("Count");
        count_label.setFont(new Font("Tahoma", Font.PLAIN, 26));
        count_label.setBounds(15, 350, 400, 40);
        add(count_label);

        JLabel mean_label = new JLabel("Mean Score");
        mean_label.setFont(new Font("Tahoma", Font.PLAIN, 26));
        mean_label.setBounds(15, 450, 400, 40);
        add(mean_label);


        JLabel std_label = new JLabel("S.D. Score");
        std_label.setFont(new Font("Tahoma", Font.PLAIN, 26));
        std_label.setBounds(15, 550, 400, 40);
        add(std_label);


        BoxAndWhiskerItem userBW = new BoxAndWhiskerItem(results.get(1).getMean(), results.get(1).getQ2(), results.get(1).getQ1(),
                results.get(1).getQ3(), results.get(1).getMin(), results.get(1).getMax(), null, null, null);
        BoxAndWhiskerItem outBW = null;
        BoxAndWhiskerItem criticBW;

        if (results.size() > 3)
        {
            outBW = new BoxAndWhiskerItem(results.get(2).getMean(), results.get(2).getQ2(), results.get(2).getQ1(),
                    results.get(2).getQ3(), results.get(2).getMin(), results.get(2).getMax(), null, null, null);
            criticBW = new BoxAndWhiskerItem(results.get(3).getMean(), results.get(3).getQ2(), results.get(3).getQ1(),
                    results.get(3).getQ3(), results.get(3).getMin(), results.get(3).getMax(), null, null, null);
        }
        else
            criticBW = new BoxAndWhiskerItem(results.get(2).getMean(), results.get(2).getQ2(), results.get(2).getQ1(),
                    results.get(2).getQ3(), results.get(2).getMin(), results.get(2).getMax(), null, null, null);

        DefaultBoxAndWhiskerCategoryDataset boxData = new DefaultBoxAndWhiskerCategoryDataset();
        boxData.add(userBW, "Reviews", "User (w/o Outliers)");
        if (results.size() > 3 && outBW != null)
        {
            boxData.add(outBW, "Reviews", "Acceptable Outliers");
        }
        boxData.add(criticBW, "Reviews", "Critics");


        BoxAndWhiskerRenderer boxRenderer = new BoxAndWhiskerRenderer();
        DefaultCategoryDataset catData = new DefaultCategoryDataset();
        catData.addValue(boxData.getMeanValue(0, 0), "Mean", boxData.getColumnKey(0));
        if (results.size() > 3 && outBW != null)
            catData.addValue(boxData.getMeanValue(0, 2), "Mean", boxData.getColumnKey(2));
        else
            catData.addValue(boxData.getMeanValue(0, 1), "Mean", boxData.getColumnKey(1));

        LineAndShapeRenderer lineRenderer = new LineAndShapeRenderer();
        CategoryAxis xAxis = new CategoryAxis("Type of Reviews");
        NumberAxis yAxis = new NumberAxis("Score");
        yAxis.setAutoRangeIncludesZero(false);
        CategoryPlot plot = new CategoryPlot(boxData, xAxis, yAxis, boxRenderer);

        plot.setDataset(1, catData);
        plot.setRenderer(1, lineRenderer);
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.FORWARD);

        JFreeChart chart = new JFreeChart("", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

        String tooltipformat = "<html><body>Q1: {6}<br>Q3: {7}<br>Min: {4}<br>Max: {5}<br>Median: {3}<br>Mean: {2}</body></html>";
        boxRenderer.setBaseToolTipGenerator(new BoxAndWhiskerToolTipGenerator(tooltipformat, NumberFormat.getNumberInstance()));
        boxRenderer.setMeanVisible(false);

        ChartPanel panel = new ChartPanel(chart);
        panel.setBackground(Color.WHITE);
        panel.setBounds(500, 100, 500, 700);
        panel.setAlignmentY(CENTER_ALIGNMENT);
        add(panel);

        JButton back_button = new JButton("Back");
        back_button.setForeground(Color.WHITE);
        back_button.setFont(new Font("Consolas", Font.PLAIN, 12));
        back_button.setBackground(new Color(101, 101, 238));
        back_button.setBounds(161, 750, 180, 40);
        add(back_button);
        back_button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //Go the Display Info panel
                GameReviewHomePanel searchPanel = new GameReviewHomePanel(jframe);
                jframe.setContentPane(searchPanel);
            }
        });
        back_button.addKeyListener(new KeyListener()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                GameReviewHomePanel searchPanel = new GameReviewHomePanel(jframe);
                jframe.setContentPane(searchPanel);
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
            }
        });

        JButton UserWord_cloud = new JButton("User Word Cloud");
        UserWord_cloud.setForeground(Color.WHITE);
        UserWord_cloud.setFont(new Font("Consolas", Font.PLAIN, 12));
        UserWord_cloud.setBackground(new Color(101, 101, 238));
        UserWord_cloud.setBounds(40, 650, 180, 40);
        add(UserWord_cloud);
        UserWord_cloud.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                WordCloudGenerator.showWorldCloud("User Word Cloud", Arrays.stream(context.getAllUserReviews())
                        .parallel()
                        .map(x -> x.getComments())
                        .collect(Collectors.toList())
                );
            }
        });

        JButton CriticWord_cloud = new JButton("Critic Word Cloud");
        CriticWord_cloud.setForeground(Color.WHITE);
        CriticWord_cloud.setFont(new Font("Consolas", Font.PLAIN, 12));
        CriticWord_cloud.setBackground(new Color(101, 101, 238));
        CriticWord_cloud.setBounds(280, 650, 180, 40);
        add(CriticWord_cloud);
        CriticWord_cloud.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                WordCloudGenerator.showWorldCloud("User Word Cloud", Arrays.stream(context.getAllCriticReviews())
                        .parallel()
                        .map(x -> x.getComments())
                        .collect(Collectors.toList())
                );
            }
        });

        JButton ShowUser_Review = new JButton("Show User Reviews");
        ShowUser_Review.setForeground(Color.WHITE);
        ShowUser_Review.setFont(new Font("Consolas", Font.PLAIN, 12));
        ShowUser_Review.setBackground(new Color(101, 101, 238));
        ShowUser_Review.setBounds(40, 700, 180, 40);
        add(ShowUser_Review);
        ShowUser_Review.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                var content = Arrays.stream(context.getAllUserReviews())
                        .map(x -> (UserReview)x)
                        .map(x -> x.getAuthor() + "\n"
                                + new SimpleDateFormat("dd-MMM-yyyy").format(x.getDateCreated()) + "\n"
                                + "Score: " + x.getScore() + "\n"
                                + "Helpfulness: " + x.getHelpfulScore() + " of " + x.getHelpfulCount() + "\n"
                                + x.getComments())
                        .collect(Collectors.joining("\n\n"));
                ScrollingPlane.createStatic(content, 640, 480).showWindow("User Comments Summary");
            }
        });

        JButton ShowCritic_Review = new JButton("Show Critic Reviews");
        ShowCritic_Review.setForeground(Color.WHITE);
        ShowCritic_Review.setFont(new Font("Consolas", Font.PLAIN, 12));
        ShowCritic_Review.setBackground(new Color(101, 101, 238));
        ShowCritic_Review.setBounds(280, 700, 180, 40);
        add(ShowCritic_Review);
        ShowCritic_Review.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                var content = Arrays.stream(context.getAllCriticReviews())
                        .map(x -> (CriticReview) x)
                        .map(x -> x.getAuthor() + "\n"
                                + x.getSource() + "\n"
                                + new SimpleDateFormat("dd-MMM-yyyy").format(x.getDateCreated()) + "\n"
                                + "Score: " + x.getScore() + "\n"
                                + x.getComments())
                        .collect(Collectors.joining("\n\n"));
                ScrollingPlane.createStatic(content, 640, 480).showWindow("Critic Review Summary");
            }
        });


        JLabel game_name_value = new JLabel(context.getGameInfo().getGameName());
        game_name_value.setVerticalAlignment(SwingConstants.TOP);
        game_name_value.setFont(new Font("Tahoma", Font.PLAIN, 20));
        game_name_value.setBounds(15, 140, 455, 40);
        add(game_name_value);

        JTextArea description_value = new JTextArea(context.getGameInfo().toString());
        description_value.setWrapStyleWord(true);
        description_value.setLineWrap(true);
        description_value.setEditable(false);
//        description_value.setal(SwingConstants.TOP);
        description_value.setFont(new Font("Tahoma", Font.PLAIN, 15));
        description_value.setBounds(15, 210, 455, 150);
        add(description_value);

        JLabel count_value = null;

        if (results.size() > 3 && outBW != null)
        {
            count_value = new JLabel(results.get(1).getOriginal().size() + " (User w/o Outliers), "
                    + results.get(2).getOriginal().size() + " (Acceptable User Outliers), "
                    + results.get(3).getOriginal().size() + " (Critics)");
        }
        else
        {
            count_value = new JLabel(results.get(1).getOriginal().size() + " (User w/o Outliers), "
                    + results.get(2).getOriginal().size() + " (Critics)");

        }

        count_value.setVerticalAlignment(SwingConstants.TOP);
        count_value.setFont(new Font("Tahoma", Font.PLAIN, 15));
        count_value.setBounds(15, 400, 500, 100);
        add(count_value);

        JLabel avg_score_value = null;
        if (results.size() > 3 && outBW != null)
        {
            avg_score_value = new JLabel(formatDoubleD2(results.get(1).getMean()) + " (User w/o Outliers), "
                    + formatDoubleD2(results.get(2).getMean()) + " (Acceptable User Outliers), "
                    + formatDoubleD2(results.get(3).getMean()) + " (Critics)");
        }
        else
        {
            avg_score_value = new JLabel(formatDoubleD2(results.get(1).getMean()) + " (User w/o Outliers), "
                    + formatDoubleD2(results.get(2).getMean()) + " (Critics)");
        }
        avg_score_value.setVerticalAlignment(SwingConstants.TOP);
        avg_score_value.setFont(new Font("Tahoma", Font.PLAIN, 15));
        avg_score_value.setBounds(15, 500, 500, 100);
        add(avg_score_value);

        JLabel std_score_value = null;
        if (results.size() > 3 && outBW != null)
        {
            std_score_value = new JLabel(results.get(1).getSd().toString() + " (User w/o Outliers), "
                    + results.get(2).getSd().toString() + " (Acceptable Outliers), "
                    + results.get(3).getSd().toString() + " (Critics)");
        }
        else
        {
            std_score_value = new JLabel(results.get(1).getSd().toString() + " (User w/o Outliers), "
                    + results.get(2).getSd().toString() + " (Critics)");
        }
        std_score_value.setVerticalAlignment(SwingConstants.TOP);
        std_score_value.setFont(new Font("Tahoma", Font.PLAIN, 15));
        std_score_value.setBounds(15, 600, 500, 100);
        add(std_score_value);
    }

    private static String formatDoubleD2(Double input)
    {
        return String.format("%.2f", input);
    }
}

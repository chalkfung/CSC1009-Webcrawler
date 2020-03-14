package GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Arrays;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;

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
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class GameReviewInformationPanel extends JPanel
{

    private static final long serialVersionUID = 1L;

    public GameReviewInformationPanel(JFrame jframe)
    {
        setBackground(Color.WHITE);

        setLayout(null);
        setSize(1000, 800);

        JLabel header_label = new JLabel("");
        header_label.setIcon(new ImageIcon(GameReviewInformationPanel.class.getResource("/image/HeaderLogo.png")));
        header_label.setBounds(0, 0, 1000, 100);
        add(header_label);

        JLabel game_name_label = new JLabel("Game Name:");
        game_name_label.setFont(new Font("Tahoma", Font.PLAIN, 26));
        game_name_label.setBounds(15, 150, 200, 40);
        add(game_name_label);

        JLabel description_label = new JLabel("Description:");
        description_label.setFont(new Font("Tahoma", Font.PLAIN, 26));
        description_label.setBounds(15, 250, 145, 40);
        add(description_label);

        JLabel avg_score_label = new JLabel("Average Score:");
        avg_score_label.setFont(new Font("Tahoma", Font.PLAIN, 26));
        avg_score_label.setBounds(15, 350, 200, 40);
        add(avg_score_label);

        JLabel std_score_label = new JLabel("Standard Deviation Score:");
        std_score_label.setFont(new Font("Tahoma", Font.PLAIN, 26));
        std_score_label.setBounds(15, 450, 400, 40);
        add(std_score_label);

        DefaultBoxAndWhiskerCategoryDataset boxData = new DefaultBoxAndWhiskerCategoryDataset();
        boxData.add(Arrays.asList(30, 36, 46, 55, 65, 76, 81, 80, 71, 59, 44, 34), "Reviews", "Critic");
        boxData.add(Arrays.asList(22, 25, 34, 44, 54, 63, 69, 67, 59, 48, 38, 28), "Reviews", "User");

        BoxAndWhiskerRenderer boxRenderer = new BoxAndWhiskerRenderer();

        DefaultCategoryDataset catData = new DefaultCategoryDataset();
        catData.addValue(boxData.getMeanValue(0, 0), "Mean", boxData.getColumnKey(0));
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
        ChartPanel panel = new ChartPanel(chart);
        panel.setBackground(Color.WHITE);
        panel.setBounds(500, 100, 500, 700);
        panel.setAlignmentY(CENTER_ALIGNMENT);
        add(panel);

        JButton back_button = new JButton("Back");
        back_button.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //Go the Display Info panel
                GameReviewHomePanel searchPanel = new GameReviewHomePanel(jframe);
                jframe.setContentPane(searchPanel);
            }
        });
        back_button.setForeground(Color.WHITE);
        back_button.setFont(new Font("Consolas", Font.PLAIN, 31));
        back_button.setBackground(new Color(101, 101, 238));
        back_button.setBounds(157, 718, 164, 50);
        add(back_button);

        JLabel game_name_value = new JLabel("<placeholder>");
        game_name_value.setVerticalAlignment(SwingConstants.TOP);
        game_name_value.setFont(new Font("Tahoma", Font.PLAIN, 20));
        game_name_value.setBounds(15, 200, 455, 40);
        add(game_name_value);

        JLabel description_value = new JLabel("<placeholder>");
        description_value.setVerticalAlignment(SwingConstants.TOP);
        description_value.setFont(new Font("Tahoma", Font.PLAIN, 20));
        description_value.setBounds(15, 300, 455, 40);
        add(description_value);

        JLabel avg_score_value = new JLabel("<Score>");
        avg_score_value.setVerticalAlignment(SwingConstants.TOP);
        avg_score_value.setFont(new Font("Tahoma", Font.PLAIN, 20));
        avg_score_value.setBounds(15, 400, 100, 40);
        add(avg_score_value);

        JLabel std_score_value = new JLabel("<Std Score>");
        std_score_value.setVerticalAlignment(SwingConstants.TOP);
        std_score_value.setFont(new Font("Tahoma", Font.PLAIN, 20));
        std_score_value.setBounds(15, 500, 150, 40);
        add(std_score_value);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$()
    {
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
    }
}
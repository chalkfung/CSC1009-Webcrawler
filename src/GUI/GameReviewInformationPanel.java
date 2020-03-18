package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;
import java.util.stream.Collectors;

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
import org.jfree.data.statistics.BoxAndWhiskerItem;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import javax.swing.JButton;
import javax.swing.SwingConstants;

import ricardo_crawlos.core.IReview;
import ricardo_crawlos.utilities.AnalyserBase;
import ricardo_crawlos.utilities.Statistics;

public class GameReviewInformationPanel extends JPanel
{

	private static final long serialVersionUID = 1L;

	public GameReviewInformationPanel(JFrame jframe, Dictionary<Integer, Statistics<Double, IReview>> results)
	{
		setBackground(Color.WHITE);

		setLayout(null);
		setSize(1000, 800);

		JLabel header_label = new JLabel("");
		header_label.setIcon(new ImageIcon(GameReviewInformationPanel.class.getResource("/GUI/Image/HeaderLogo.png")));
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


		List<Double> usrScores = results.get(1).getNonOutliers().stream().map(x->x.getScore()).collect(Collectors.toList());
		BoxAndWhiskerItem outBW = null;
		BoxAndWhiskerItem criticBW;

		if(results.size() > 3)
		{
			outBW = new BoxAndWhiskerItem(results.get(2).getMean(), results.get(2).getQ2(),results.get(2).getQ1(),
					results.get(2).getQ3(), results.get(2).getMin(), results.get(2).getMax(), null, null,null);
			criticBW = new BoxAndWhiskerItem(results.get(3).getMean(), results.get(3).getQ2(),results.get(3).getQ1(),
					results.get(3).getQ3(), results.get(3).getMin(), results.get(3).getMax(), null, null,null);
		}
		else
			criticBW = new BoxAndWhiskerItem(results.get(2).getMean(), results.get(2).getQ2(),results.get(2).getQ1(),
					results.get(2).getQ3(), results.get(2).getMin(), results.get(2).getMax(), null, null,null);

		DefaultBoxAndWhiskerCategoryDataset boxData = new DefaultBoxAndWhiskerCategoryDataset();
        boxData.add( usrScores, "Reviews", "User (w/o Outliers)");
		if(results.size() > 3 && outBW != null)
		{
			boxData.add(outBW, "Reviews", "Acceptable Outliers");
		}
		boxData.add(criticBW, "Reviews", "Critics");


        BoxAndWhiskerRenderer boxRenderer = new BoxAndWhiskerRenderer();
        DefaultCategoryDataset catData = new DefaultCategoryDataset();
        catData.addValue(boxData.getMeanValue(0, 0), "Mean", boxData.getColumnKey(0));
		if(results.size() > 3 && outBW != null)
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
		boxRenderer.setBaseToolTipGenerator(new BoxAndWhiskerToolTipGenerator(tooltipformat,NumberFormat.getNumberInstance()));
		boxRenderer.setMeanVisible(false);

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

		JLabel game_name_value = new JLabel(String.valueOf("DOTO"));
		game_name_value.setVerticalAlignment(SwingConstants.TOP);
		game_name_value.setFont(new Font("Tahoma", Font.PLAIN, 20));
		game_name_value.setBounds(15, 200, 455, 40);
		add(game_name_value);

		JLabel description_value = new JLabel("DOTO");
		description_value.setVerticalAlignment(SwingConstants.TOP);
		description_value.setFont(new Font("Tahoma", Font.PLAIN, 20));
		description_value.setBounds(15, 300, 455, 40);
		add(description_value);

		JLabel count_value = null;

		if(results.size() > 3 && outBW != null)
		{
			count_value = new JLabel(usrScores.size() + " (User w/o Outliers), "
					+ results.get(2).getOriginal().size() + " (Acceptable User Outliers), "
					+ results.get(3).getOriginal().size() + " (Critics)");
		}
		else
		{
			count_value = new JLabel(usrScores.size() + " (User w/o Outliers), "
					+ results.get(2).getOriginal().size() + " (Critics)");

			System.out.println(results.get(2).getIqr());
			
		}

		count_value.setVerticalAlignment(SwingConstants.TOP);
		count_value.setFont(new Font("Tahoma", Font.PLAIN, 15));
		count_value.setBounds(15, 400, 500, 100);
		add(count_value);

		JLabel avg_score_value = null;
		if(results.size() > 3 && outBW != null)
		{
			avg_score_value = new JLabel(results.get(1).getMean().toString() + " (User w/o Outliers), "
					+ results.get(2).getMean().toString() + " (Acceptable User Outliers), "
					+ results.get(3).getMean().toString() + " (Critics)");
		}
		else
		{
			avg_score_value = new JLabel(results.get(1).getMean().toString() + " (User w/o Outliers), "
					+ results.get(2).getMean().toString() + " (Critics)");
		}
		avg_score_value.setVerticalAlignment(SwingConstants.TOP);
		avg_score_value.setFont(new Font("Tahoma", Font.PLAIN, 15));
		avg_score_value.setBounds(15, 500, 500, 100);
		add(avg_score_value);

		JLabel std_score_value = null;
		if(results.size() > 3 && outBW != null)
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
}

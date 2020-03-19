import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.image.AngleGenerator;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import com.kennycason.kumo.wordstart.CenterWordStart;

import GUI.MainReviewFrame;
import GUI.ScrollingPlane;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        showWindow();
//        test();
    }

    public static void showWindow()
    {
        EventQueue.invokeLater(() ->
        {
            try
            {
                MainReviewFrame frame = new MainReviewFrame();
                frame.setVisible(true);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }

    public static void test() throws IOException
    {
        final JFrame frame = new JFrame("JPanel WordCloud");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new JPanelDemo());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

class JPanelDemo extends JPanel
{

    private final WordCloud wordCloud;

    public JPanelDemo() throws IOException {
        wordCloud = buildWordCloud();

        final JLabel wordCloudLabel = new JLabel(new ImageIcon(wordCloud.getBufferedImage()));
        add(wordCloudLabel);
        repaint();
    }

    private static WordCloud buildWordCloud() throws IOException {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(200);
        frequencyAnalyzer.setMinWordLength(5);

        var wordFrequencies = frequencyAnalyzer.load("database/extracted/reviews/dota-2/metacritic_critic-reviews.json");
        final Dimension dimension = new Dimension(800, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setBackground(new RectangleBackground(dimension));
        wordCloud.setColorPalette(new ColorPalette(new Color(0x4055F1), new Color(0x408DF1), new Color(0x40AAF1), new Color(0x40C5F1), new Color(0x40D3F1), new Color(0xFFFFFF)));
        wordCloud.setFontScalar(new LinearFontScalar(10, 100));
        wordCloud.setWordStartStrategy(new CenterWordStart());
        wordCloud.setAngleGenerator(new AngleGenerator(0));
        wordCloud.build(wordFrequencies);

        return wordCloud;
    }
}

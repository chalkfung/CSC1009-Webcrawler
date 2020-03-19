package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.image.AngleGenerator;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;
import com.kennycason.kumo.wordstart.CenterWordStart;

public class WordCloudGenerator extends JPanel
{
    public static void showWorldCloud(String title, List<String> input)
    {
        final JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(new WordCloudGenerator(input));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public WordCloudGenerator(List<String> input)
    {
        WordCloud wordCloud = buildWordCloud(input);
        final JLabel wordCloudLabel = new JLabel(new ImageIcon(wordCloud.getBufferedImage()));
        add(wordCloudLabel);
        repaint();
    }

    private static WordCloud buildWordCloud(List<String> input)
    {
        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
        frequencyAnalyzer.setWordFrequenciesToReturn(300);
        frequencyAnalyzer.setMinWordLength(5);

        var wordFrequencies = frequencyAnalyzer.load(input);
        final Dimension dimension = new Dimension(800, 600);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setPadding(1);
        wordCloud.setBackground(new RectangleBackground(dimension));
        wordCloud.setColorPalette(new ColorPalette(
                new Color(0x4055F1),
                new Color(0x408DF1),
                new Color(0x40AAF1),
                new Color(0x40C5F1),
                new Color(0x40D3F1),
                new Color(0xFFFFFF))
        );
        wordCloud.setFontScalar(new LinearFontScalar(10, 100));
        wordCloud.setWordStartStrategy(new CenterWordStart());
        wordCloud.setAngleGenerator(new AngleGenerator(0));
        wordCloud.build(wordFrequencies);

        return wordCloud;
    }
}

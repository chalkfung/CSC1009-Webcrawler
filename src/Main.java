import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import GUI.MainWindow;
import ricardo_crawlos.managers.GameManager;
import ricardo_crawlos.models.UserReview;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;

public class Main
{
    public static void main(String[] args) throws IOException, ParseException
    {
        // ICrawler clawler = new GameSpotReviewsClawer("dota-2");
        // clawler.run();

        // String[] cacheOutput = Arrays.stream(clawler.getTraversalResults()).map(x ->
        // x.html()).toArray(String[]::new);

        // TextWriter.writeAllText("database/cache/dota-2/Gamespot-UserReviews" +
        // ".json", JsonSerialiser.DefaultInstance().toJson(
        // cacheOutput));

        String json = Files.readString(Path.of("database/cache/dota-2/Gamespot-UserReviews.json"));

        String[] htmlStrings = JsonSerialiser.DefaultInstance().fromJson(json, String[].class);

        String combinedFilteredHTML = Arrays.stream(htmlStrings)
                .map(x -> Jsoup.parse(x).select("li.userReview-list__item").outerHtml())
                .collect(Collectors.joining("\n"));

        Document document = Jsoup.parse(combinedFilteredHTML);

        TextWriter.writeAllText("extractedTest.html", document.html());

        // Elements ratings = document.select("div.media-well--review-user > strong");

        GameManager.getInstance().append("Dota 2");

        SimpleDateFormat dateParser = new SimpleDateFormat("MMM-dd-yyyy");

        UserReview[] reviews = document.select("li.userReview-list__item").stream().map(element ->
        {
            String title = element.select("h3.media-title > a").text();
            Elements reviewComponents = element.getElementsByAttributeValueContaining("class", "userReview-list");
            String comment = reviewComponents.get(2).text();
            double score = Double.parseDouble(element.select("div.media-well--review-user > strong").text());
            Element reviewMeta = reviewComponents.get(1);
            String author = reviewMeta.select("a").text();
            String[] dateFragments = reviewMeta.text().replace(author, "").split("Review Date: ")[1].split("\\|")[0]
                    .replace(",", "").split(" ");
            Date date;
            try
            {
                date = dateParser.parse(String.join("-", dateFragments));
            }
            catch (ParseException e)
            {
                date = new Date();
            }
            String[] helpfulFragments = reviewComponents.get(3).text().substring(0, 16).split(" ");
            int helpfulScore = Integer.parseInt(helpfulFragments[0]);
            int helpfulCount = Integer.parseInt(helpfulFragments[2]);

            return new UserReview(0, score, title + "\n" + comment, date, author, helpfulScore, helpfulCount);
        }).toArray(UserReview[]::new);

        String reviewsJson = JsonSerialiser.DefaultInstance().toJson(reviews);


        TextWriter.writeAllText("database/extracted/reviews/dota-2/Gamespot-UserReviews.json", reviewsJson);



        // ShowWindow();
    }

    public static void ShowWindow()
    {
        MainWindow dialog = new MainWindow();
        dialog.setSize(1280, 720);
        dialog.setVisible(true);
        System.exit(0);
    }
}

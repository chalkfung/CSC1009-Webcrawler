package ricardo_crawlos.utilities;

public class StringUtils
{
    public static String trimEndExcess(String input, int limit, String replacement)
    {
        if (input.length() > limit - replacement.length())
        {
            return input.substring(0, limit - replacement.length()) + replacement;
        }
        return input;
    }

    public static String formatDoubleD2(Double input)
    {
        return String.format("%.2f", input);
    }
}

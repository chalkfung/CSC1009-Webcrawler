package ricardo_crawlos.utilities;

/**
 * StringUtils class
 * Contains function to format strings such that they won't trail and flood the GUI
 */
public class StringUtils
{
    /**
     * Trims the string and replace with whatever the replacement is
     * @param input The String input to be trim
     * @param limit The limit of the string, any character after will be replaced
     * @param replacement The replacement string to replace with
     * @return
     */
    public static String trimEndExcess(String input, int limit, String replacement)
    {
        if (input.length() > limit - replacement.length())
        {
            return input.substring(0, limit - replacement.length()) + replacement;
        }
        return input;
    }

    /**
     * Rounds a double input to 2 decimal and return as String
     * @param input Double input to be rounded
     * @return Formatted string of the double input.
     */
    public static String formatDoubleD2(Double input)
    {
        return String.format("%.2f", input);
    }
}

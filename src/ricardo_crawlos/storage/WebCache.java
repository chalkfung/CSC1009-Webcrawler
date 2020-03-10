package ricardo_crawlos.storage;

public class WebCache
{
    private final String baseUrl;

    public WebCache(String baseUrl)
    {
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl()
    {
        return baseUrl;
    }
}

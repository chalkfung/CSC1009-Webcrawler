package ricardo_crawlos.models;

public class Platform {
    private String platformName;
    private int platformId;
    public Platform(String platformName_, int platformId_ )
    {
        this.platformName = platformName_;
        this.platformId = platformId_;
    }

    public String getPlatformName()
    {
        return this.platformName;
    }

    public int getPlatformId()
    {
        return this.platformId;
    }
}

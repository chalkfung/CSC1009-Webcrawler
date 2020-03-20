package ricardo_crawlos.models;

/**
 * Platform class
 * To hold the game platforms and their given ID
 */
public class Platform {
    private String platformName;
    private int platformId;

    /**
     * Constructor for Platform class
     * @param platformName_ Platform name
     * @param platformId_ Platform ID after mapping
     */
    public Platform(String platformName_, int platformId_ )
    {
        this.platformName = platformName_;
        this.platformId = platformId_;
    }

    /**
     * Getter of the platform name
     * @return Platform name in String
     */
    public String getPlatformName()
    {
        return this.platformName;
    }

    /**
     * Getter of the platform ID after mapping
     * @return Platform ID after mapping
     */
    public int getPlatformId()
    {
        return this.platformId;
    }
}

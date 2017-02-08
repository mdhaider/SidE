package birdofparadise.side.ConfigurationsService;

/**
 * Created by i822763 on 7/20/16.
 */
public class ConfigurationsService {
    private static ConfigurationsService mInstance;
    private boolean releaseMode = false;
    private int widthPixels;
    private int heightPixels;

    public boolean isReleaseMode() {
        return releaseMode;
    }

    public void setReleaseMode(boolean releaseMode) {
        this.releaseMode = releaseMode;
    }

    public static ConfigurationsService sharedInstance() {
        if (mInstance == null) {
            mInstance = new ConfigurationsService();
        }
        return mInstance;
    }


    public void setDisplaySize(int widthPixels, int heightPixels) {
        this.widthPixels = widthPixels;
        this.heightPixels = heightPixels;
    }

    public int getWidthPixels() {
        return widthPixels;
    }


    public int getHeightPixels() {
        return heightPixels;
    }

}

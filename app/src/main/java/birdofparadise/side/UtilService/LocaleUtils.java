package birdofparadise.side.UtilService;

import android.content.Context;
import android.os.Build;

import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by C5248763 on 10/19/2016.
 */

public class LocaleUtils {

    private static LocaleUtils mInstance;
    private Locale locale;
    private TimeZone timeZone;

    public TimeZone getTimeZone() {
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        return timeZone;
    }

    public Locale getLocale() {
        return locale;
    }


    public static LocaleUtils sharedInstance() {
        if (mInstance == null) {
            mInstance = new LocaleUtils();
        }
        return mInstance;
    }


    public void setLocale(Context baseContext) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locale = baseContext.getResources().getConfiguration().getLocales().get(0);
        } else {
            locale = baseContext.getResources().getConfiguration().locale;
        }
    }
}

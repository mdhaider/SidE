package birdofparadise.side.UtilService;
/*
Copyright © 2016 Ariba. All rights reserved.
*/

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;

public class DrawableUtils {
    private static DrawableUtils ourInstance = new DrawableUtils();

    public static DrawableUtils getInstance() {
        return ourInstance;
    }

    private DrawableUtils() {
    }

    public Drawable getDrawable(Context context, int r) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return context.getResources().getDrawable(r, null);
        } else {
            return context.getResources().getDrawable(r);
        }
    }
}

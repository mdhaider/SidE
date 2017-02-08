package birdofparadise.side.UtilService;
/*
Copyright © 2016 Ariba. All rights reserved.
*/

import java.text.SimpleDateFormat;

public class DateUtils {
    private static DateUtils ourInstance = new DateUtils();
    private SimpleDateFormat simpleDateFormat_MMMMM_ddwith_at;

    public static DateUtils getInstance() {
        return ourInstance;
    }

    private SimpleDateFormat simpleDateFormat_d_mm_yy;

    public SimpleDateFormat getSimpleDateFormat_MMMMM_dd() {
        if (simpleDateFormat_MMMMM_dd != null) {
            return simpleDateFormat_MMMMM_dd;
        } else {
            return simpleDateFormat_MMMMM_dd = new SimpleDateFormat("MMMM d, yyyy h:mm a", LocaleUtils.sharedInstance().getLocale());
        }
    }

    public SimpleDateFormat getSimpleDateFormat_MMMMM_dd_with_at() {

        if (simpleDateFormat_MMMMM_ddwith_at != null) {
            return simpleDateFormat_MMMMM_ddwith_at;
        } else {
            return simpleDateFormat_MMMMM_ddwith_at = new SimpleDateFormat("MMMM d, yyyy @ h:mm a", LocaleUtils.sharedInstance().getLocale());
        }
    }

    private SimpleDateFormat simpleDateFormat_MMMMM_dd;

    public SimpleDateFormat getSimpleDateFormat_d_mm_yy() {
        if (simpleDateFormat_d_mm_yy != null) {
            return simpleDateFormat_d_mm_yy;
        } else {
            return simpleDateFormat_d_mm_yy = new SimpleDateFormat("d/MM/yy, h:mm a", LocaleUtils.sharedInstance().getLocale());
        }
    }

    private DateUtils() {
    }
}

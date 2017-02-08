package birdofparadise.side.UtilService;
/*
Copyright © 2016 Ariba. All rights reserved.
*/

import android.graphics.PorterDuff;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class AnimationUtils {
    private static AnimationUtils ourInstance = new AnimationUtils();
    public View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public static AnimationUtils getInstance() {
        return ourInstance;
    }

    private AnimationUtils() {
    }


    public View.OnTouchListener onTouchListenerForImageView = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN: {

                    ImageView view = (ImageView) v;
                    //overlay is black with transparency of 0x77 (119)
                    view.getDrawable().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                    view.invalidate();
                    break;
                }
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL: {
                    ImageView view = (ImageView) v;
                    //clear the overlay
                    view.getDrawable().clearColorFilter();
                    view.invalidate();
                    break;
                }
            }

            return false;
        }
    };

    public View.OnTouchListener onTouchListenerForView = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            View view = v;
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //overlay is black with transparency of 0x77 (119)
                    view.getBackground().setColorFilter(0x77000000, PorterDuff.Mode.SRC_ATOP);
                    view.invalidate();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    //clear the overlay
                    view.getBackground().clearColorFilter();
                    view.invalidate();
                    break;
            }
            return false;
        }
    };

}

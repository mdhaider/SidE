package birdofparadise.side.UtilService;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

/**
 * Created by i822763 on 7/27/16.
 */
public class UtilService {
    private static ProgressDialog mProgressDialog;
    public static void hideKeyboard(View view, Activity activity) {
        InputMethodManager inputMethodManager =(InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void enableButton(Button button, boolean enabled) {
        button.setEnabled(enabled);
        button.getBackground().setAlpha(enabled ? 255 : 128);
    }

    public static void showProgress(Context context, String message) {
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle("ANMobile Application");
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public static void dismissDiaolog() {
        mProgressDialog.dismiss();
    }
}
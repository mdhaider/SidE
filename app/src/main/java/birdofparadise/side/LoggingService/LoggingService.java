package birdofparadise.side.LoggingService;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.zip.GZIPOutputStream;

import birdofparadise.side.ConfigurationsService.ConfigurationsService;
import birdofparadise.side.R;
import de.mindpipe.android.logging.log4j.LogConfigurator;


/**
 * Created by i822763 on 7/21/16.
 */
public class LoggingService {
    private static LoggingService mInstance = null;

    private static final String logFileName = Environment.getExternalStorageDirectory() + File.separator + "an_app.log";
    private static final String zippedLogFileName = logFileName + ".gz";
    private static final int maxFileSize = 1024 * 1024 * 5;
    private static final String loggerName = "org.apache";

    public static LoggingService getInstance() {
        if (mInstance == null) {
            mInstance = new LoggingService();
            final LogConfigurator logConfigurator = new LogConfigurator();
            Level logLevel = Level.DEBUG;

            logConfigurator.setFileName(logFileName);
            if (ConfigurationsService.sharedInstance().isReleaseMode()) {
                logLevel = Level.INFO;
            }
            logConfigurator.setRootLevel(logLevel);
            logConfigurator.setLevel(loggerName, logLevel);
            logConfigurator.setUseFileAppender(true);
            logConfigurator.setMaxFileSize(maxFileSize);
            logConfigurator.setImmediateFlush(true);
//            logConfigurator.configure();
        }
        return mInstance;
    }

    public Logger log(Class logClass) {
        return Logger.getLogger(logClass);
    }

    public void SendLoagcatMail(Context context) {
        zipLogFile();
        sendLogEmail(context);
    }

    private void sendLogEmail(Context context) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, "");
        i.putExtra(Intent.EXTRA_SUBJECT, "");
        i.putExtra(Intent.EXTRA_TEXT, "");
        i.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(new File(zippedLogFileName)));
        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, R.string.no_email_client, Toast.LENGTH_SHORT).show();
        }
    }

    private void zipLogFile() {
        try {
            File logFile = new File(logFileName);
            InputStream in = new FileInputStream(logFile);
            GZIPOutputStream zip = new GZIPOutputStream(new FileOutputStream(
                    new File(zippedLogFileName)));
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                zip.write(buffer, 0, len);
            }
            zip.close();
        } catch (IOException e) {
            LoggingService.getInstance().log(LoggingService.class).error("Problem when zipping log file.", e);
        }
    }

    public void log(Object aClass, String msg) {
        log(aClass.getClass()).debug(msg);
    }

    public void logE(Object aClass, StackTraceElement[] eMessage) {
        log(aClass.getClass()).error(Arrays.toString(eMessage));
    }

    public void logI(Object aClass, String msg) {
        log(aClass.getClass()).info(msg);
    }

}

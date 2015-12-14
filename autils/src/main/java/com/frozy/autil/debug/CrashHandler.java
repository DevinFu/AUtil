package com.frozy.autil.debug;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.Properties;

@SuppressWarnings("unused")
public class CrashHandler implements UncaughtExceptionHandler {

    public static final String TAG = "CrashHandler";

    /**
     * The system's default exception handler
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private static CrashHandler Instance;

    private CrashEventListener mCrashEventListener;

    private Context mContext;

    /**
     * Information of the device and crash event
     */
    private Properties mDeviceCrashInfo = new Properties();

    private CrashHandler(Context context, CrashEventListener listener) {
        this.mContext = context;
        this.mCrashEventListener = listener;
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public synchronized static CrashHandler getInstance(Context context, CrashEventListener listener) {
        if (Instance == null)
            Instance = new CrashHandler(context, listener);
        return Instance;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (mCrashEventListener != null && ex != null) {
            collectCrashDeviceInfo(mContext, ex);
            boolean handled = mCrashEventListener.onCrash(ex, mDeviceCrashInfo.toString());
            if (!handled) {
                mDefaultHandler.uncaughtException(thread, ex);
            }
        } else {
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }

    /**
     * Collect device information
     */
    private void collectCrashDeviceInfo(Context ctx, Throwable ex) {
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                mDeviceCrashInfo.put("App Version Name", pi.versionName);
                mDeviceCrashInfo.put("App Version Code", "" + pi.versionCode);
            }
        } catch (NameNotFoundException e) {
            // ignore//
        }


        mDeviceCrashInfo.put("Board", Build.BOARD);
        mDeviceCrashInfo.put("Device", Build.DEVICE);
        mDeviceCrashInfo.put("Display", Build.DISPLAY);
        mDeviceCrashInfo.put("Hardware", Build.HARDWARE);
        mDeviceCrashInfo.put("Model", Build.MODEL);
        mDeviceCrashInfo.put("SDK Version", Build.VERSION.SDK_INT);

        mDeviceCrashInfo.put("EXCEPTION", ex.getLocalizedMessage());

        Writer info = new StringWriter();
        PrintWriter printWriter = new PrintWriter(info);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        String result = info.toString();
        printWriter.close();

        mDeviceCrashInfo.put("STACK_TRACE", result);

    }


    public interface CrashEventListener {
        /**
         * If you consumed {@code Throwable ex} , return {@code true}. Otherwise, return {@code false}.
         * Then, {@code Throwable ex} will be handled by the default {@code Thread.UncaughtExceptionHandler}
         *
         * @param ex       Exception that caused the crash event.
         * @param crashLog Contains device information and exception details
         */
        boolean onCrash(Throwable ex, String crashLog);
    }
}

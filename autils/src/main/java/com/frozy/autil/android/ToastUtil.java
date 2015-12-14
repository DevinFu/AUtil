package com.frozy.autil.android;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Devin.Fu
 * @version Sep 14, 2014
 */
@SuppressWarnings("unused")
public class ToastUtil {
    private static Toast sToast;

    public static void showToast(Context context, String msg, int duration) {
        if (sToast != null) {
            sToast.setText(msg);
            sToast.setDuration(duration);
        }
        else {
            sToast = Toast.makeText(context, msg, duration);
        }
        sToast.show();
    }

    public static void showToast(Context context, int msgRes, int duration) {
        if (sToast != null) {
            sToast.setText(msgRes);
            sToast.setDuration(duration);
        }
        else {
            sToast = Toast.makeText(context, msgRes, duration);
        }
        sToast.show();
    }

    public static void release() {
        if (sToast != null) {
            sToast.cancel();
            sToast = null;
        }
    }
}

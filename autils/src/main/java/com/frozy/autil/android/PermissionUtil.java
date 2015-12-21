package com.frozy.autil.android;

import android.content.Context;
import android.content.pm.PackageManager;

@SuppressWarnings("unused")
public class PermissionUtil {

    public final static String PERMISSION_GPS = "android.permission.ACCESS_FINE_LOCATION";
    public final static String PERMISSION_READ_CONTACTS = "android.permission.READ_CONTACTS";
    public final static String PERMISSION_RECORD_AUDIO = "android.permission.RECORD_AUDIO";
    public final static String PERMISSION_CAMERA = "android.permission.CAMERA";

    /**
     * if the permission is granted
     *
     * @param context    上下文
     * @param permission 权限字符串
     * @return true if permission is granted, false otherwise
     */
    public static boolean checkPermission(Context context, String permission) {
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }
}

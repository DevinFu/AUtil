package com.frozy.autil.file;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * @author Devin devin5719@gmail.com
 * @version 2013-6-10
 */
@SuppressWarnings("unused")
public class SDUtil {
    /**
     * Get a usable cache directory (external if available, internal otherwise).
     *
     * @param context    The context to use
     * @param uniqueName A unique directory name to append to the cache dir
     * @return The cache dir
     */
    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    public static File getDiskCacheDir(Context context, String uniqueName) {
        boolean isExternalStorageRemovable = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            isExternalStorageRemovable = Environment.isExternalStorageRemovable();
        }

        boolean isExternalStorageMounted = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());

        String cachePath;
        // Use external cache dir
        if (isExternalStorageMounted && isExternalStorageRemovable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO)
                cachePath = context.getCacheDir().getPath();
            else
                cachePath = "/Android/data/" + context.getPackageName() + "/cache/";
        }
        // Use internal cache dir
        else cachePath = context.getCacheDir().getPath();

        return new File(cachePath + File.separator + uniqueName);
    }
}

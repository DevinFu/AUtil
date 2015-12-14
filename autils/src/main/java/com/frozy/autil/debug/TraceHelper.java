package com.frozy.autil.debug;

import android.annotation.SuppressLint;
import android.util.Log;

public final class TraceHelper {
    private static String mLogTag = "AndroidUtil-TraceHelper";

    private static final String CLASS_METHOD_LINE_FORMAT = "%s.%s()  Line:%d  (%s)";

    @SuppressLint("DefaultLocale")
    public static void trace() {
        // 从堆栈信息中获取当前被调用的方法信息
        StackTraceElement traceElement = Thread.currentThread().getStackTrace()[3];
        String logText = String.format(CLASS_METHOD_LINE_FORMAT, traceElement.getClassName(), traceElement.getMethodName(),
                traceElement.getLineNumber(), traceElement.getFileName());
        Log.d(mLogTag, logText);
    }
}

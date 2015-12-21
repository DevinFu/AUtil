package com.frozy.autil.iml;

/**
 * Created by Devin.Fu on 9/29/15.
 * 耗时操作的回调类
 */
@SuppressWarnings("unused")
public interface OnFinishListener<R> {
    /**
     * 操作成功返回的数据，若不需要，只返回null
     *
     * @param result 返回数据
     */
    void onResultOk(R result);

    /**
     * 操作失败返回的错误码
     *
     * @param code 错误码
     */
    void onResultError(int code);
}

package com.frozy.autil.iml;

import java.util.ArrayList;

/**
 * 数据分页获取接口的回调函数
 *
 * @param <T> 数据条目对应的java实体类
 */
@SuppressWarnings("unused")
public interface PagerFetcherListener<T> {
    /**
     * 接收到数据时调用该函数
     *
     * @param data 数据
     * @param page 当前页
     */
    void onReceived(ArrayList<T> data, int page);

    /**
     * 没有加载到更多数据时调用该函数
     *
     * @param page 当前页
     */
    void onNoMore(int page);

    /**
     * 发生错误时调用该函数
     *
     * @param error 错误码
     * @param page  当前页
     */
    void onError(int error, int page);
}

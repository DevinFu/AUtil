package com.frozy.autil.iml;

/**
 * Created by Devin.Fu on 10/5/15.
 * 数据分页获取接口，具体的数据分页获取辅助类需要实现此接口
 */
@SuppressWarnings("unused")
public interface PagerDataFetcher<T> {

    /**
     * 设置初始页码，下一次获取时，从该页码的下一页开始获取
     *
     * @param page 初始页码
     */
    void setInitialPage(int page);

    /**
     * 获取下一页数据
     */
    void getNextPage();

    /**
     * 设置数据回调函数
     *
     * @param listener 回调函数
     */
    void setPagerFetcherListener(PagerFetcherListener<T> listener);
}

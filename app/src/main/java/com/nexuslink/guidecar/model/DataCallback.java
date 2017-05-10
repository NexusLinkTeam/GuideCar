package com.nexuslink.guidecar.model;

/**
 * model与presenter间通信使用的回调接口
 * Created by Rye on 2017/5/10.
 */

public interface DataCallback<T>{

    void responseSuccess(T bean);

    void responseFailed(String exception);
}

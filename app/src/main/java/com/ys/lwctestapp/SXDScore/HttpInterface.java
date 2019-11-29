package com.ys.lwctestapp.SXDScore;


public interface HttpInterface<M> {
    void httpCode(M m, HttpResult httpResult);

    void httpQuery(M m, HttpResult httpResult);

    void httpCancel(M m, HttpResult httpResult);

    void httpRefund(M m, HttpResult httpResult);

    interface HttpResult<M> {
        void onSuccess(int result, String msg, M t);

        void onFail(int result, String msg);
    }

}


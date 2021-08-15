package com.deutsche.tradeStoreService.tradestore.beans;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class ResponseBean<T> {
    private ResponsesStatus responsesStatus;
    private ErrorBean errorBean;
    private T data;

    public ResponsesStatus getResponsesStatus() {
        return responsesStatus;
    }

    public void setResponsesStatus(ResponsesStatus responsesStatus) {
        this.responsesStatus = responsesStatus;
    }

    public ErrorBean getErrorBean() {
        return errorBean;
    }

    public void setErrorBean(ErrorBean errorBean) {
        this.errorBean = errorBean;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

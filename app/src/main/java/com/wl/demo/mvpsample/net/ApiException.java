package com.wl.demo.mvpsample.net;


import com.wl.demo.mvpsample.domain.Response;

/**
 * Created by wangliang on 16-9-30.
 */

public class ApiException extends RuntimeException {
    private static final long serialVersionUID = -8057603642495146152L;

    private Response response;

    public ApiException(Response response) {
        this.response = response;
    }

    public String getErrorMsg() {
        if(response != null) {
            return response.getMsg();
        }

        return null;
    }
}

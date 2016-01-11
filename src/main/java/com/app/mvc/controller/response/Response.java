package com.app.mvc.controller.response;

public class Response {

    private ResponseStatus status;
    private Object data;

    public Response(ResponseStatus status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Response(ResponseStatus status) {
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "status=" + status +
                ", data=" + data +
                '}';
    }
}

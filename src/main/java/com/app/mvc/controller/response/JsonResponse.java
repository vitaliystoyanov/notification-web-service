package com.app.mvc.controller.response;

public class JsonResponse {

    private Status status;
    private Object data;

    public JsonResponse(Status status, Object data) {
        this.status = status;
        this.data = data;
    }

    public JsonResponse(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
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

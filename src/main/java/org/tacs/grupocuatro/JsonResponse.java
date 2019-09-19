package org.tacs.grupocuatro;

public class JsonResponse {
    private String message;
    private String error;
    private Object data;

    public JsonResponse(String message) {
        this.message = message;
        this.error = null;
        this.data = null;
    }

    public JsonResponse(String message, String error) {
        this.message = message;
        this.error = error;
        this.data = null;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JsonResponse with(Object obj) {
        this.data = obj;
        return this;
    }
}

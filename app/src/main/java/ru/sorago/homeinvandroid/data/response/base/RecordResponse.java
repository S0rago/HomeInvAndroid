package ru.sorago.homeinvandroid.data.response.base;

public class RecordResponse<T> extends Response {
    private T data;

    public RecordResponse() {
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

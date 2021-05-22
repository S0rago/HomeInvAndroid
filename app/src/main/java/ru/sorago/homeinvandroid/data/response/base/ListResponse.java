package ru.sorago.homeinvandroid.data.response.base;

import java.util.List;

public class ListResponse<T> extends Response {
    private Long total;
    private Integer offset;
    private Integer perPage;
    private List<T> data;

    public ListResponse() {
    }

    public ListResponse(List<T> data) {
        this.data = data;
    }

    public ListResponse(List<T> data, Long total, Integer offset, Integer perPage) {
        this.data = data;
        this.total = total;
        this.offset = offset;
        this.perPage = perPage;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void add(T data) {
        this.data.add(data);
    }

    public void addAll(List<T> data) {
        this.data.addAll(data);
    }
}

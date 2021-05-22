package ru.sorago.homeinvandroid.core;

import android.view.View;

public class ViewTag {
    private String elem;
    private int count;

    public ViewTag(String elem, int count) {
        this.elem = elem;
        this.count = count;
    }

    public String getElem() {
        return elem;
    }

    public void setElem(String elem) {
        this.elem = elem;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ViewTag viewTag = (ViewTag) o;

        if (count != viewTag.count) return false;
        return elem.equals(viewTag.elem);
    }

    @Override
    public int hashCode() {
        int result = elem.hashCode();
        result = 31 * result + count;
        return result;
    }

    public ViewTag getNext(int step) {
        return new ViewTag(this.elem, this.count + step);
    }
}

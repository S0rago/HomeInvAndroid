package ru.sorago.homeinvandroid.data.model;

public class ItemType {
    private String id;
    private String name;

    public ItemType() {
    }

    public ItemType(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

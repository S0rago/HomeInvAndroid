package ru.sorago.homeinvandroid.data.model;

import java.util.Set;

public class Item {
    private long id;
    private ItemType type;
    private String name;
    private Set<ItemProp> props;

    public long getId() {
        return id;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ItemProp> getProps() {
        return props;
    }

    public void setProps(Set<ItemProp> props) {
        this.props = props;
    }

    public void addProp(ItemProp prop) {
        props.add(prop);
    }
}

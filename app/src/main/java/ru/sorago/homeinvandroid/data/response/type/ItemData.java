package ru.sorago.homeinvandroid.data.response.type;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

import ru.sorago.homeinvandroid.data.model.ItemProp;
import ru.sorago.homeinvandroid.data.model.ItemType;


public class ItemData {
    private long id;
    private String name;
    private String typeStr;
    private ItemType type;
    private Set<ItemProp> props;

    public ItemData() {

    }

    public ItemData(long id, String name, String typeStr, ItemType type, Set<ItemProp> props) {
        this.id = id;
        this.name = name;
        this.typeStr = typeStr;
        this.type = type;
        this.props = props;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public Set<ItemProp> getProps() {
        return props;
    }

    public void setProps(Set<ItemProp> props) {
        this.props = props;
    }
}

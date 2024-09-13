package it.Roles;

import java.util.Map;
import java.util.Map.Entry;

public class Role implements Entry<String,RoleAttributes> {
    String name;
    RoleAttributes attributes;

    public Role(String name, RoleAttributes attr){
        this.name=name;
        this.attributes=attr;
    }

    @Override
    public String getKey() {
        return name;
    }

    @Override
    public RoleAttributes  getValue() {
        return attributes;
    }

    @Override
    public RoleAttributes setValue(RoleAttributes  value) {
        this.attributes=value;
        return attributes;
    }
}

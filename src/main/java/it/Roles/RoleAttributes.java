package it.Roles;

import net.kyori.adventure.text.format.TextColor;

import java.util.Map;

public class RoleAttributes {
    String TabListName;
    TextColor Color;
    int TabListPosition;

    public RoleAttributes(String tln, TextColor c, int tlp){
        this.TabListName = tln;
        this.Color = c;
        this.TabListPosition = tlp;
    }
    public void setColor(TextColor color){
        this.Color =color;
    }
    public void setTabListName(String TabListName){
        this.TabListName=TabListName;
    }
    public void setTabListPosition(int TabListPosition){
        this.TabListPosition=TabListPosition;
    }
    public String getTabListName(){
        return this.TabListName;
    }
    public TextColor getColor(){
        return this.Color;
    }
    public int getTabListPosition(){
        return this.TabListPosition;
    }

    public Map<String, String> serialize(){
        return Map.of("TabListName", TabListName,
                "Color", Color.asHexString(),
                "TabListPosition", String.valueOf(TabListPosition));
    }
    public static RoleAttributes deserialize(Map<String, String> data){
        return new RoleAttributes(data.get("TabListName")
                , TextColor.fromHexString(data.get("Color"))
                ,Integer.parseInt(data.get("TabListPosition")));
    }
}

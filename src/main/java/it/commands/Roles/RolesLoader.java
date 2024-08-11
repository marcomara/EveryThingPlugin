package it.commands.Roles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static it.plugin.Plugin.roles;

public class RolesLoader {
    public RolesLoader(List<Role> croles){
        Map<Integer , Role> map = new HashMap<>();
        for (Role r : croles){
            map.put(r.getPosition(), r);
        }
        int i =0;
        while(i< map.size()-1){
            Role a = map.get(i);
            roles.registerNewTeam(a.getName()).prefix(a.getPrefix());
        }
    }
}

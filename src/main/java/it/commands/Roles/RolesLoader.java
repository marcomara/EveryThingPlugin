package it.commands.Roles;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;
import java.util.Map;

import static it.plugin.Plugin.*;

public class RolesLoader{
    public RolesLoader() throws Exception{
        File rfile = new File(dataFolder, "Roles.yml");
        rfile.createNewFile();
        YamlConfiguration rcfg = YamlConfiguration.loadConfiguration(rfile);
        List<Map<?, ?>> list = rcfg.getMapList("Roles");
        for (Map m : list){
            Role r = new Role((String) m.get("Name"), (String) m.get("Prefix"), (String) m.get("Color"));
            roles.registerNewTeam(r.getName()).prefix(r.getDisplayPrefix());
            rolesl.add(r);
        }
    }
}

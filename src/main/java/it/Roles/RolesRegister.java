package it.Roles;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.util.*;

import static it.Roles.Command.*;
import static it.plugin.Plugin.dataFolder;
import static it.plugin.Plugin.lgg;

public class RolesRegister {

    static List<Role> roles = new ArrayList<>();
    static Map<String, String> map = new HashMap<>();
    static File f = new File(dataFolder, "Roles.yml");

    public static boolean RolesRegister() {
        YamlConfiguration fc;
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (Exception e) {
                lgg.warning("Unable to create Roles.yml");
                return false;
            }
            CreateBasicRoles();
            fc = YamlConfiguration.loadConfiguration(f);
            for (Role r : roles) {
                fc.set(r.getKey(), r.getValue().serialize());
            }
        }
        fc = YamlConfiguration.loadConfiguration(f);
        Set<String> r = fc.getKeys(false);
        for (String rol : r) {
            roles.add(new Role(rol, RoleAttributes.deserialize(fc.getObject(rol, map.getClass()))));
        }
        RegisterRoles();
        return true;
    }

    private static void RegisterRoles() {
        for (Role r : roles) {
            if(!RegisterRole(r)){
                lgg.warning("Unable to register team " + r.getValue().TabListName + " because its position is already occupied");
            }
        }
    }

    public static void RemoveRole(String name){
        YamlConfiguration fc = YamlConfiguration.loadConfiguration(f);
        for (String s : fc.getKeys(false)){
            Map<String,String> role = fc.getObject(s,map.getClass());
            if (role.get("TabListName").equals(name)){
                
            }
        }
    }

    public static boolean RegisterRole(Role r){
        if (poslist.contains(r.getValue().getTabListPosition())){
            return false;
        }
        poslist.add(r.getValue().getTabListPosition());
        String name = r.getValue().getTabListPosition() + r.getKey();
        Team t = scoreboard.registerNewTeam(name);
        t.prefix(Component.text("[").color(NamedTextColor.GRAY)
                .append(Component.text(r.getValue().getTabListName()).color(r.getValue().getColor()))
                .append(Component.text("] ").color(NamedTextColor.GRAY)));
        teams.put(r.getValue().getTabListName(), t);
        return true;
    }

    private static void CreateBasicRoles() {
        RoleAttributes ownAtt = new RoleAttributes("Owner", NamedTextColor.LIGHT_PURPLE, 0);
        RoleAttributes admAtt = new RoleAttributes("Admin", NamedTextColor.RED, 1);
        RoleAttributes modAtt = new RoleAttributes("Mod", NamedTextColor.BLUE, 2);
        RoleAttributes plyAtt = new RoleAttributes("Player", NamedTextColor.GREEN, 3);
        Role owner = new Role("owner", ownAtt);
        Role admin = new Role("admin", admAtt);
        Role mod = new Role("mod", modAtt);
        Role player = new Role("player", plyAtt);
        roles.add(owner);
        roles.add(admin);
        roles.add(mod);
        roles.add(player);
    }
}

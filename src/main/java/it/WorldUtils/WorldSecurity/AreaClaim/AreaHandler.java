package it.WorldUtils.WorldSecurity.AreaClaim;

import it.commands.DisabledCommandMessage;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static it.plugin.Plugin.*;
import static it.utils.SaveUtility.save;

public class AreaHandler implements Listener {
    public static List<Area> areas = new ArrayList<>();
    File areasFile = new File(dataFolder, "ClaimedAreas.chunk");
    public AreaHandler(){
        try{
            areasFile.createNewFile();
        }catch (Exception e){
            e.printStackTrace();
            lgg.warning("Unable to create protected areas file, disabling function...");
            plugin.getCommand("claimarea").setExecutor(new DisabledCommandMessage());
            return;
        }
        readAreas();
    }
    private static boolean isInProtectedArea(Location bloc, Area a){
        int x = bloc.getBlockX(), y = bloc.getBlockY(), z = bloc.getBlockZ();
        int xM=0, xm=0,yM=0,ym=0,zM=0,zm=0;
        if (a.getS()[0]>a.getE()[0]){
            xM=a.getS()[0];
            xm=a.getE()[0];
        }else {
            xM=a.getE()[0];
            xm=a.getS()[0];
        }
        if (a.getS()[1]>a.getE()[1]) {
            yM=a.getS()[1];
            ym=a.getE()[1];
        }else {
            yM=a.getE()[1];
            ym=a.getS()[1];
        }
        if (a.getS()[2]>a.getE()[2]) {
            zM=a.getS()[2];
            zm=a.getE()[2];
        }else {
            zM=a.getE()[2];
            zm=a.getS()[2];
        }
        if (x <= xM && x >= xm) {
            if (y <= yM && y >= ym) {
                if (z <= zM && z >= zm) {
                    return true;
                }else return false;
            }else return false;
        }else return false;
    }
    @EventHandler
    public static void onTntExplode(BlockExplodeEvent e){
        e.setCancelled(true);
        for (Area a:areas) {
            if (a.allowsGreefing()) continue;
            for (Block b : e.blockList()) {
                Location bloc = b.getLocation();
                if (isInProtectedArea(bloc,a))e.blockList().remove(b);
            }
        }
        for (Block b : e.blockList()){
            b.breakNaturally();
        }
    }

    @EventHandler
    public static void onBlockBrake(BlockBreakEvent e){
        for (Area a : areas){
            Block b = e.getBlock();
            Location bloc = b.getLocation();
            if (isInProtectedArea(bloc, a)){
                if (!a.getPermitted().contains(e.getPlayer().getName())||e.getPlayer().hasPermission("admin.area.override")){
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(Component.text("You can't brake blocks in this area").decorate(TextDecoration.BOLD).color(NamedTextColor.DARK_PURPLE));
                }
            }
        }
    }
    @EventHandler
    public static void onBlockPlace(BlockPlaceEvent e){
        for (Area a : areas){
            Location l = e.getBlockPlaced().getLocation();
            String name = e.getPlayer().getName();
            if (isInProtectedArea(l, a)){
                if (!a.getPermitted().contains(name)||e.getPlayer().hasPermission("admin.area.override")){
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(Component.text("You can't place blocks in this area").decorate(TextDecoration.BOLD).color(NamedTextColor.DARK_PURPLE));
                }
            }
        }
    }
    @EventHandler
    public static void onInventoryOpen(PlayerInteractEvent e){
        if (e.getClickedBlock()==null||e.getClickedBlock().getType()==Material.AIR) return;
        if (e.getAction().isRightClick() && (e.getClickedBlock().getType()== Material.CHEST || e.getClickedBlock().getType()==Material.BARREL || e.getClickedBlock().getType()==Material.CHEST_MINECART)){
            for (Area a : areas){
                Location l = e.getClickedBlock().getLocation();
                if (isInProtectedArea(l,a)){
                    if (a.getPermitted().contains(e.getPlayer().getName())) return;
                    if (!a.getVisitors().contains(e.getPlayer().getName())||e.getPlayer().hasPermission("admin.area.override")){
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(Component.text("You can't open inventories in this area").decorate(TextDecoration.BOLD).color(NamedTextColor.DARK_PURPLE));
                    }
                }
            }
        }
    }
    private void readAreas(){
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(areasFile);
        List<String> list = conf.getStringList("areas");
        for (String s : list){
            areas.add(Area.fromString(s));
        }
    }
    public void addArea(Area a){
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(areasFile);
        List<String> list = conf.getStringList("areas");
        list.add(a.toString());
        conf.set("areas", list);
        areas.add(a);
        save(areasFile,conf);
    }
    public void removeArea(String name){
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(areasFile);
        for (Area a : areas){
            if (a.getName().equals(name)){
                areas.remove(a);
                conf.set("areas", areas);
                save(areasFile, conf);
                break;
            }
        }
    }
}

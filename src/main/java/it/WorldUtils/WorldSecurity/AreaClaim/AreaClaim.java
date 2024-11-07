package it.WorldUtils.WorldSecurity.AreaClaim;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.WorldUtils.WorldSecurity.AreaClaim.AreaHandler.areas;
import static it.plugin.Plugin.plugin;

public class AreaClaim implements CommandExecutor {
    private AreaHandler handler;
    public AreaClaim(){
        handler = new AreaHandler();
        Bukkit.getServer().getPluginManager().registerEvents(handler, plugin);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String bho, @NotNull String[] args) {
        if (args.length==1){
            if (args[0].equals("list")){
                sender.sendMessage("Listed areas:");
                for (Area a : areas){
                    sender.sendMessage(a.getName() + " : " + Arrays.toString(a.getS()) + " to " + Arrays.toString(a.getE()));
                }
            }
            return true;
        }else if (args.length==2) {
            if (args[0].equals("remove")) {
                for (Area a : areas) {
                    if (a.getName().equals(args[1])) {
                        handler.removeArea(a.getName());
                        sender.sendMessage("Area removed successfully");
                        break;
                    } else sender.sendMessage("No area named " + args[1] + " found");
                }
            }
            return true;
        }else if (args.length==8){
            int[] se = getCoords(args, ((Player)sender).getLocation());
            int[] s = {se[0],se[1],se[2]};
            int[] e = {se[3],se[4],se[5]};
            boolean b = switch (args[6]){
                case "true" -> true;
                case "false" ->false;
                default -> false;
            };
            handler.addArea(new Area(s,e,sender.getName(),b, args[7]));
            sender.sendMessage("Area successfully registered");
        }
        return true;
    }
    
    private int[] getCoords(String[] values, Location l){
        int[] coords = new int[6];
        for (int i = 0; i<6; i++){
            if (values[i].startsWith("~")){
                if (values[i].equals("~")){
                    coords[i] = switch (i){
                        case 0, 3 -> l.getBlockX();
                        case 1, 4 -> l.getBlockY();
                        case 2, 5 -> l.getBlockZ();
                        default -> throw new IndexOutOfBoundsException("Unexpected value: " + i);
                    };
                }else{
                    int offset = Integer.parseInt(values[i].substring(1));
                    coords[i]=switch (i){
                        case 0, 3 -> l.getBlockX()+offset;
                        case 1, 4 -> l.getBlockY()+offset;
                        case 2, 5 -> l.getBlockZ()+offset;
                        default -> throw new IndexOutOfBoundsException("Unexpected value: " + i);
                    };
                }
            }else{
                coords = new int[]{Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]), Integer.parseInt(values[3]), Integer.parseInt(values[4]), Integer.parseInt(values[5])};
            }
        }
        return coords;
    }
    public static class AreaClaimTC implements TabCompleter{
        @Override
        public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {
            if (args.length==7) return List.of("true", "false");
            return new ArrayList<>();
        }
    }
}

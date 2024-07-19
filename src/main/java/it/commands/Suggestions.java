package it.commands;

import it.plugin.Plugin;
import it.utils.Colors;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import it.utils.SaveUtility;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static it.plugin.Plugin.dataFolder;
import static it.utils.SaveUtility.create;

public class Suggestions implements CommandExecutor, TabCompleter {
    private final File file;

    public Suggestions(){
        this.file=new File(dataFolder, "suggestions.txt");
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        create(file);
        FileConfiguration filec = SaveUtility.createyml(file);
        StringBuilder suggestion = new StringBuilder();
        String name = sender.getName();
        for(String word : args){
            suggestion.append(word).append(" ");
        }
        List<String> suggestions = filec.getStringList(name);
        suggestions.add(suggestion.toString());
        filec.set(name,suggestions);
        SaveUtility.save(file,filec);
        Plugin.ccs.sendMessage(name + " says: " +suggestion);
        sender.sendMessage(Colors.GREEN +"Thank you for your suggestion(s)!");
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return new ArrayList<>();
    }
}

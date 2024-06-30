package it.plugin.StartupLoaders;


import it.plugin.Plugin;

import static it.plugin.Plugin.commands;

public class CommandTabCompleterHandler {
    public static void  Handler(Plugin plugin){
        CommandTabCompleter ctc = new CommandTabCompleter();
        if(commands.isEmpty()){
            return;
        }
        for (String command : commands) {
            plugin.getCommand(command).setTabCompleter(ctc);
        }
    }
}

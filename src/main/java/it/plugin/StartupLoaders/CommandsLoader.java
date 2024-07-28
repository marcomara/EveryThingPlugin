package it.plugin.StartupLoaders;

import java.util.Map;

import static it.plugin.Plugin.*;


public class CommandsLoader {
    public static void CommandsLoader() {
        Map<String, String> map = Commands.ConfigMap();
        for (String str : map.keySet()){
            if(booleanMap.get(map.get(str))) {
                try {
                    plugin.getCommand(str).setExecutor(Commands.CommandsMap().get(str).newInstance());
                    if (Commands.TabsMap().keySet().contains(str)) {
                        plugin.getCommand(str).setTabCompleter(Commands.TabsMap().get(str).newInstance());
                    }
                    commands.add(str);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else plugin.getCommand(str).setExecutor(executor);

        }
    }
}
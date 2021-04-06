package npc.zeldaboy111;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getServer().getPluginCommand("npc").setExecutor(new CommandMain());
        Bukkit.getServer().getConsoleSender().sendMessage("The plugin Npc has been enabled");
    }
    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage("The plugin Npc has been disabled.");
    }

}

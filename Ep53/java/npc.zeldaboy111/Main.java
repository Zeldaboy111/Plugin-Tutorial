package npc.zeldaboy111;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        NpcUtils.getInstance().load();
        //TODO chance 'new CommandMain()' to 'CommandMain.getInstance()'
        Bukkit.getServer().getPluginCommand("npc").setExecutor(new CommandMain());
        Bukkit.getServer().getConsoleSender().sendMessage("The plugin Npc has been enabled");
    }
    @Override
    public void onDisable() {
        NpcUtils.getInstance().hideNpcs();
        Bukkit.getServer().getConsoleSender().sendMessage("The plugin Npc has been disabled.");
    }

}

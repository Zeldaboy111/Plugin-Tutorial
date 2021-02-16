package me.zeldaboy111;

import me.zeldaboy111.holograms.HologramUtil;
import me.zeldaboy111.storage.Yml;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Plugin plugin;

    public static void main(String[] in) { }

    public void onEnable() {
        plugin = this;
        Bukkit.getServer().getPluginCommand("hologram").setExecutor(new Commands());
        Yml.init();
        HologramUtil.getInstance().init();
    }

    public void onDisable() {
        HologramUtil.getInstance().hideAll();
    }

    public static Plugin getPlugin() { return plugin; }

}

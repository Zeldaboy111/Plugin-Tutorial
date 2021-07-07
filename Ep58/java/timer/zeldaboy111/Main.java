package timer.zeldaboy111;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {

        Bukkit.getServer().getConsoleSender().sendMessage("§6Timer§8: §7The plugin has been enabled.");
    }
    @Override
    public void onDisable() {

        Bukkit.getServer().getConsoleSender().sendMessage("§6Timer§8: §7The plugin has been disabled.");
    }

}

package fm.zeldaboy111;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Main plugin;

    @Override
    public void onEnable() {
        plugin = this;
        printLn("The plugin FileManager has been enabled.");
        printLn(Files.FILE1.toString());
    }

    @Override
    public void onDisable() {
        printLn("The plugin FileManager has been disabled.");
    }

    private void printLn(String msg) {
        Bukkit.getServer().getConsoleSender().sendMessage(msg);
    }
}

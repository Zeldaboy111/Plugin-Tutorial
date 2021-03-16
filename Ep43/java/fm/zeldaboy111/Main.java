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
        Files.FILE1.save("File1Test_plugin", "Test");
        printLn(Files.FILE1.getString("test1"));
        printLn(Files.FILE1.getStringArray("test1_list", false).toString());
    }

    @Override
    public void onDisable() {
        printLn("The plugin FileManager has been disabled.");
    }

    private void printLn(String msg) {
        Bukkit.getServer().getConsoleSender().sendMessage(msg);
    }
}

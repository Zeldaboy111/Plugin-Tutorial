package npc.zeldaboy111;

import npc.zeldaboy111.handlers.NpcPipeline;
import npc.zeldaboy111.handlers.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        NpcUtils.getInstance().load();
        Bukkit.getServer().getPluginCommand("npc").setExecutor(CommandMain.getInstance());
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        addPlayersToPipeline();
        Bukkit.getServer().getConsoleSender().sendMessage("The plugin Npc has been enabled");
    }
    private void addPlayersToPipeline() {
        for(Player p : Bukkit.getServer().getOnlinePlayers()){
            NpcPipeline.getInstance().inject(p);
        }
    }
    @Override
    public void onDisable() {
        NpcUtils.getInstance().hideAll();
        Bukkit.getServer().getConsoleSender().sendMessage("The plugin Npc has been disabled.");
    }

}

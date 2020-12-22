package me.zeldaboy111.holograms;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HoloCmd {
    private final String prefix = "§b§lHologram§8: §7";
    private static HoloCmd clazz;
    private HoloCmd() { }
    public static HoloCmd getInstance() { return clazz == null ? (clazz = new HoloCmd()) : clazz; }

    public void execute(CommandSender s, String[] args) {
        if(!(s instanceof Player)) s.sendMessage(prefix + "This command can only be executed by a player.");
        else if(!s.hasPermission("holo.execute")) s.sendMessage(prefix + "You do not have enough permissions to execute this command.");
        else checkAndExecuteCommand((Player)s, args);
    }

    private void checkAndExecuteCommand(Player p, String[] args) {
        p.sendMessage(prefix + "WIP");
    }

}

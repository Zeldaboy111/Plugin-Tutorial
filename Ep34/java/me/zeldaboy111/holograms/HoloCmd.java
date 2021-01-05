package me.zeldaboy111.holograms;


import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HoloCmd {
    private final String prefix = "§6§lHologram§8: §7";
    private static HoloCmd clazz;
    private HoloCmd() { }
    public static HoloCmd getInstance() { return clazz == null ? (clazz = new HoloCmd()) : clazz; }

    public void execute(CommandSender s, String[] args) {
        if(!(s instanceof Player)) s.sendMessage(prefix + "This command can only be executed by a player.");
        else if(!s.hasPermission("holo.execute")) s.sendMessage(prefix + "You do not have enough permissions to execute this command.");
        else checkAndExecuteCommand((Player)s, args);
    }

    private void checkAndExecuteCommand(Player p, String[] args) {
        if(args.length < 1) p.sendMessage(prefix + "Incorrect Usage! §b/hologram <create | delete | reload> <id>§7.");
        else if(args[0].equalsIgnoreCase("reload")) reload(p, (args.length >= 2 ? args[1].toLowerCase() : ""));
    }

    private void create(Player p, String id) {
        HologramUtil.getInstance().createHologram(id, p.getLocation().getBlock().getLocation().add(0.5, 0.5, 0.5));
        p.sendMessage(prefix + "Created the hologram of id §b" + id + "§7.");
    }
    private void delete(Player p, String id) {
        p.sendMessage(prefix + "WIP");
    }
    private void reload(Player p, String id) {
        // no id given -> ""
        p.sendMessage(prefix + "WIP");
    }

}

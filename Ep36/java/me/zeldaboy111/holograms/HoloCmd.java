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
        if(args.length < 1) p.sendMessage(prefix + "Incorrect Usage! §e/hologram <create | delete | reload> <id>§7.");
        else if(args[0].equalsIgnoreCase("reload")) reload(p, (args.length >= 2 ? args[1].toLowerCase() : ""));
        else if(args[0].equalsIgnoreCase("create")) create(p, (args.length >= 2 ? args[1].toLowerCase() : null));
        else if(args[0].equalsIgnoreCase("delete")) delete(p, (args.length >= 2 ? args[1].toLowerCase() : null));
    }

    private void create(Player p, String id) {
        if(!checkIfCreateIdIsValid(p, id)) return;
        HologramUtil.getInstance().createHologram(id, p.getLocation().getBlock().getLocation().add(0.5, 0.5, 0.5));
        p.sendMessage(prefix + "Created the hologram of id §e" + id + "§7.");
    }
    private Boolean checkIfCreateIdIsValid(Player p, String id) {
        if(id == null) p.sendMessage(prefix + "Incorrect Usage! §e/hologram create <id>§7.");
        else if(HologramUtil.getInstance().checkIfHologramExists(id)) p.sendMessage(prefix + "A hologram with the id §e" + id + " §7already exists.");
        else return true;
        return false;
    }

    private void delete(Player p, String id) {
        if(!checkIfDeleteIdIsValid(p, id)) return;
        p.sendMessage(prefix + "WIP");
        // Work at deleting
    }
    private Boolean checkIfDeleteIdIsValid(Player p, String id) {
        if(id == null) p.sendMessage(prefix + "Incorrect Usage! §e/hologram delete <id>§7.");
        else if(!HologramUtil.getInstance().checkIfHologramExists(id)) p.sendMessage(prefix + "A hologram with the id §e" + id + " §7does not exist.");
        else return true;
        return false;
    }
    private void reload(Player p, String id) {
        // no id given -> ""
        p.sendMessage(prefix + "WIP");
    }

}

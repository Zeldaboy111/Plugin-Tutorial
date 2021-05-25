package npc.zeldaboy111;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NpcCommand {
    private static NpcCommand clazz;
    public static NpcCommand getInstance() { return clazz == null ? (clazz = new NpcCommand()) : clazz; }
    private NpcCommand() { }

    private static final String prefix = "§6Npc§8: §7";
    public void execute(CommandSender s, String[] args) {
        if(!(s instanceof Player)) s.sendMessage(prefix + "This command can only be executed by a player.");
        else if(args.length <= 0) sendHelp(s);
        else tryExecuteCommand((Player)s, args[0].toLowerCase(), args);
    }
    private void sendHelp(CommandSender s) {
        s.sendMessage(prefix + "Incorrect Usage!\n §8- §e/npc create <id>\n §8- §e/npc delete <id>");
    }
    private void tryExecuteCommand(Player p, String action, String[] args) {
        if(isCreate(action)) tryExecuteCreate(p, args);
        else if(isDelete(action)) tryExecuteDelete(p, args);
        else sendHelp(p);
    }
    private void tryExecuteCreate(Player p, String[] args) {
        if(args.length <= 1) p.sendMessage(prefix + "Incorrect Usage! §e/npc create <id>§7.");
        else if(NpcUtils.getInstance().doesNpcExist(args[1].toLowerCase())) p.sendMessage("A Npc with the id §e" + args[1].toLowerCase() + " §7already exists.");
        else executeCreate(p, args[1].toLowerCase());
    }
    private void executeCreate(Player p, String id) {
        NpcUtils.getInstance().create(id, p.getLocation().getBlock().getLocation().add(0.5, 0.0, 0.5));
        p.sendMessage("Created");
    }

    private void tryExecuteDelete(Player p, String[] args) {
        if (args.length <= 1) p.sendMessage(prefix + "Incorrect Usage! §e/npc delete <id>§7.");
        else if (!NpcUtils.getInstance().doesNpcExist(args[1].toLowerCase()))
            p.sendMessage("A Npc with the id §e" + args[1].toLowerCase() + " §7does not exist.");
        else executeDelete(p, args[1].toLowerCase());
    }
    private void executeDelete(Player player, String id) {
        NpcUtils.getInstance().delete(id);
        player.sendMessage("Deleted");
    }

    private Boolean isCreate(String toCheck) { return toCheck.equalsIgnoreCase("c") || toCheck.equalsIgnoreCase("create"); }
    private Boolean isDelete(String toCheck) { return toCheck.equalsIgnoreCase("d") || toCheck.equalsIgnoreCase("delete"); }

}

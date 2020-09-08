package tutorial.zeldaboy111;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import tutorial.zeldaboy111.CommandHandlers.TestCommand;

public class Commands implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equals("test")) {
            TestCommand.clazz.executeCommand(s, args);
        } else {
            if(s instanceof Player) {
                CustomInventory.instance.open((Player)s);
            } else {
                s.sendMessage("This command can only be executed by a player.");
            }
        }
        return true;
    }
}

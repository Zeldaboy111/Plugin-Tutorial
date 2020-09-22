package tutorial.zeldaboy111.CommandHandlers;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tutorial.zeldaboy111.FireworksManager;

public class TestCommand {
    public static final TestCommand clazz = new TestCommand();

    public void executeCommand(CommandSender sender, String[] args) {
        if(!isExecutorAPlayer(sender)) sender.sendMessage("This command can only be executed by a player.");
        else spawnFireworksAtPlayer((Player)sender);
    }

    private void spawnFireworksAtPlayer(Player player) {
        //FireworksManager manager = new FireworksManager(player);
        //manager.spawn();
        //new FireworksManager(player).spawn();
        new FireworksManager(player.getLocation(), 22, 1).spawn();
        //new FireworksManager(player.getLocation(), 20, 2).spawn();
    }

    private boolean isExecutorAPlayer(CommandSender sender) { return sender instanceof Player; }

}

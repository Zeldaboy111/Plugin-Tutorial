package tutorial.zeldaboy111.CommandHandlers;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tutorial.zeldaboy111.FireworksManager;
import tutorial.zeldaboy111.Timer;

public class TestCommand {
    public static final TestCommand clazz = new TestCommand();

    public void executeCommand(CommandSender sender, String[] args) {
        // Encrypt & decrypt message
        if(!isExecutorAPlayer(sender)) sender.sendMessage("This command can only be executed by a player.");
        else timer(sender);
    }

    private void timer(CommandSender sender) {
        Timer timer = new Timer(200L);
        Boolean started = timer.start();
        if(started) sender.sendMessage("TIMER STARTED");
        else sender.sendMessage("TIMER IS ALREADY RUNNING");
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

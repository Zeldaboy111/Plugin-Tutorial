package tutorial.zeldaboy111.CommandHandlers;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCommand {
    public static final TestCommand clazz = new TestCommand();

    public void executeCommand(CommandSender sender, String[] args) {
        if(!isExecutorAPlayer(sender)) sender.sendMessage("This command can only be executed by a player.");
        else checkAndSendIntegers((Player)sender, args);
    }

    private void checkAndSendIntegers(Player player, String[] args) {
        if(args.length == 0) sendIntegersToPlayer(player, 5);
        else if(Integer.valueOf(args[0]) != null) sendIntegersToPlayer(player, Integer.valueOf(args[0]));
        else player.sendMessage("Invalid integer" + args[0]);
    }

    private void sendIntegersToPlayer(Player player, int maxBound) {
        for(int i = 0; i <= maxBound; i++) player.sendMessage(i + "");
    }

    private boolean isExecutorAPlayer(CommandSender sender) { return sender instanceof Player; }

}

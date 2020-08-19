package tutorial.zeldaboy111;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandHandler implements Listener {

    @EventHandler
    public void commandExecute(PlayerCommandPreprocessEvent e) {
        String[] args = e.getMessage().split(" ");
        String command = args[0].toLowerCase().substring(1);

        FileHandler.Command cmd;
        if((cmd = FileHandler.getCommand(command)) != null) {
            cmd.executeCommand(e.getPlayer(), args);
        }
    }

}

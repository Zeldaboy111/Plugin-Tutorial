package me.zeldaboy111;

import me.zeldaboy111.holograms.HoloCmd;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equals("hologram")) HoloCmd.getInstance().execute(s, args);
        return true;
    }
}

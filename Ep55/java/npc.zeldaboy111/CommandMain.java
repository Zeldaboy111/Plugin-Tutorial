package npc.zeldaboy111;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Field;

public class CommandMain implements CommandExecutor {
    private static CommandMain clazz;
    public static CommandMain  getInstance() { return clazz == null ? (clazz = new CommandMain()) : clazz; }
    private static CommandMap commandMap;
    private CommandMain() { initCommandMap(); }
    private void initCommandMap() {
        try {
            Field cmdMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            cmdMapField.setAccessible(true);
            commandMap = (CommandMap)cmdMapField.get(Bukkit.getServer());
        } catch(Exception e) { e.printStackTrace(); }
    }
    public Command getCommandFromMap(String cmd) { return commandMap != null ? commandMap.getCommand(cmd) : null; }

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
        if(cmd.getName().equals("npc")) NpcCommand.getInstance().execute(s, args);
        return true;
    }
}

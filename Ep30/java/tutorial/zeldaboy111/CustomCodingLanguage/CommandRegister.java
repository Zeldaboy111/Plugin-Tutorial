package tutorial.zeldaboy111.CustomCodingLanguage;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandRegister extends BukkitCommand {
    private ArrayList<String> code;
    public CommandRegister(String name, ArrayList<String> aliases, ArrayList<String> code) {
        super(name);
        this.description = "description";
        this.setAliases(aliases);
        this.code = code;
    }


    @Override
    public boolean execute(CommandSender s, String label, String[] args) {
        for(String s1 : code) {
            if(s1.startsWith("broadcast: ")) {
                String message = s1.replaceFirst("broadcast: ", "");
                for(Player lp : Bukkit.getServer().getOnlinePlayers()) {
                    lp.sendMessage(message);
                }
            }
        }
        return true;
    }
}

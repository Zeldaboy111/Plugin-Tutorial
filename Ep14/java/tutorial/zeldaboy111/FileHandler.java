package tutorial.zeldaboy111;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class FileHandler {
    public static final FileHandler instance = new FileHandler();
    final String dir = Main.plugin.getDataFolder() + "/scripts";

    private HashMap<String, Command> commandMap = new HashMap<>();

    private Boolean cmdDetected = false;
    private ArrayList<String> aliases;
    private String command;
    private Command cmdClass;
    private int line;

    public void loadFiles() {
        File file = new File(Main.plugin.getDataFolder(), "scripts");
        if(!file.exists()) file.mkdirs();
        else {
            for(File entry : file.listFiles()) {
                initFile(entry);
            }
        }
    }

    /**
     *
     * @param file file that will be read
     */
    public void initFile(File file) {
        if(!file.exists()) return;
        if(!file.getName().endsWith(".tut")) return;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;

            int lineID = 0;
            while((line = reader.readLine()) != null) {
                lineID++;
                this.line = lineID;
                if(line != null && !line.equals("") && !line.equals(" ") && !line.startsWith("//")) {
                    if (!cmdDetected) {
                        if (!testObject(line) && !detect(line)) {
                            Bukkit.getServer().getConsoleSender().sendMessage("Error found whilst initializing file " + file.getName() + ": " + lineID);
                        }
                    } else {
                        if (!cmdSubDetect(line)) {
                            Bukkit.getServer().getConsoleSender().sendMessage("Error found whilst initializing file " + file.getName() + ": " + lineID);
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param l is the desired line/object to test for code
     */
    private Boolean testObject(String l) {
        String lower = l.toLowerCase();
        if(lower.startsWith("command /") && lower.endsWith(":")) {
            cmdDetected = true;
            //command = lower.replaceFirst("command /", "");
            command = lower.substring(9, lower.length()-1);
            aliases = new ArrayList<>();
            if(command.equals("")) {
                cmdDetected = false;
                Bukkit.getServer().getConsoleSender().sendMessage("Command name required: " + line);
            }
        } else {
            return false;
        }
        return true;
    }

    private Boolean detect(String l) {
        String lower = l.toLowerCase();
        if (lower.startsWith("broadcast")) {
            cmdClass.code.add("broadcast: " + lower.substring(11, lower.length()-1));

        } else {
            return false;
        }

        return true;
    }

    public static Command getCommand(String name) {
        return instance.commandMap.get(name);
    }

    private Boolean cmdSubDetect(String l) {
        String lower = l.toLowerCase();
        if(lower.startsWith("aliases:")) {
            lower = lower.replaceFirst("aliases:", "");
            lower = lower.replace(" ", "");
            String[] aliases = lower.split(",");
            for(String s : aliases) {
                //while(s.length() > 0 && s.substring(0, 1).equals(" ")) s.replaceFirst(" ", "");
                this.aliases.add(s);
            }

        } else if(lower.equals("run:") || lower.equals("trigger:")) {
            cmdDetected = false;
            cmdClass = instance.new Command(command, aliases);
        }
        return true;
    }

    class Command {
        private Command command;
        private ArrayList<String> code;

        public Command(String name, ArrayList<String> aliases) {
            if(name == null) {
                Bukkit.getServer().getConsoleSender().sendMessage("Error found whilst initializing command: invalid command-name.");
            } else if(name.equalsIgnoreCase("command") || name.equalsIgnoreCase("cmd")) {
                Bukkit.getServer().getConsoleSender().sendMessage("Invalid Command " + name + " at line " + line);
            } else {
                commandMap.put(name, this);
                code = new ArrayList<>();
                if(aliases != null && !aliases.isEmpty()) {
                    Bukkit.getServer().getPluginCommand(name).setAliases(aliases);
                }
            }



        }

        public void executeCommand(Player p, String[] args) {
            for(String s : code) {
                if(s.startsWith("broadcast: ")) {
                    String message = s.replaceFirst("broadcast: ", "");
                    for(Player lp : Bukkit.getServer().getOnlinePlayers()) {
                        lp.sendMessage(message);
                    }
                }
            }
        }

        //Aliases toevoegen
        //Run-methode
    }

}

package tutorial.zeldaboy111.YML;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import tutorial.zeldaboy111.Main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class YML {

    public static final HashMap<Integer, YML> fileList = new HashMap<Integer, YML>();
    public static final YML testFile = new ymlConstructor("testFile", 0);

    int id;
    String name;

    File file;
    FileConfiguration config;

    public YML(String name, int id) {
        if(fileList.get(id) != null) {
            Bukkit.getServer().getConsoleSender().sendMessage("§4A file of id " + id + " already exists.");
        } else {
            fileList.put(id, this);
            this.name = name;
            this.id = id;

            createFile();
            config.options().copyDefaults(true);
            saveFile();
        }
    }

    private void createFile() {
        if(!Main.plugin.getDataFolder().exists()) Main.plugin.getDataFolder().mkdirs();
        file = new File(Main.plugin.getDataFolder(), name + ".yml");

        if(file != null && !file.exists()) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }

        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void saveFile() {
        if(file != null && file.exists()) {
            try {
                config.save(file);
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void reload() {
        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getData() {
        return config;
    }

}

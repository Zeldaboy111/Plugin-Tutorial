package me.zeldaboy111.storage;

import me.zeldaboy111.Main;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Yml {
    public static final HashMap<Integer, Yml> fileList = new HashMap<>();
    public static final Yml holograms = new ymlConstructor("holograms", 0);

    int id;
    String name;

    File file;
    FileConfiguration config;

    public static void init() { }
    public Yml(String name, int id) {
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
        if(!Main.getPlugin().getDataFolder().exists()) Main.getPlugin().getDataFolder().mkdirs();
        file = new File(Main.getPlugin().getDataFolder(), name + ".yml");

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

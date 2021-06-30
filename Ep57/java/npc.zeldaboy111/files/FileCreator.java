package npc.zeldaboy111.files;

import npc.zeldaboy111.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileCreator {

    private File file;
    private YamlConfiguration configuration;
    private Boolean doesFileExist;

    public FileCreator(String fileAsString, Boolean isPreMade) throws Exception {
        this.doesFileExist = false;
        if(fileAsString == null || fileAsString.trim().equals("")) throw new IllegalArgumentException("fileAsString cannot be null or empty");
        file = new File(fileAsString);
        if(file == null) throw new NullPointerException("file (" + fileAsString + ") is null");
        tryCreateFileFolders();
        if(file.exists()) doesFileExist = true;
        else if(isPreMade) saveFile();
        else createFile();
        reload();
    }
    private void tryCreateFileFolders() throws Exception {
        File folder = getFolderFromFile();
        if(!folder.exists()) folder.mkdirs();
    }
    private File getFolderFromFile() throws Exception {
        String[] dirFolderSplitted = file.getAbsolutePath().replace("\\", "/").split("/");
        if(dirFolderSplitted.length < 2) throw new IllegalArgumentException(file.getAbsolutePath() + " is an invalid path");
        String dir = "";
        for(int i = 0; i < dirFolderSplitted.length-1; i++) {
            dir += dirFolderSplitted[i] + "\\";
        }
        return new File(dir.substring(0, dir.length()-1));
    }

    private void saveFile() throws Exception {
        Main.plugin.saveResource(file.getName(), false);
    }
    private void createFile() throws Exception {
        doesFileExist = file.createNewFile();
    }
    public Boolean doesFileExist() {
        return doesFileExist && file != null && file.exists();
    }

    public YamlConfiguration getConfiguration() { return configuration; }
    public void reload() {
        configuration = YamlConfiguration.loadConfiguration(file);
    }
    public void save() {
        try {
            if (configuration != null) configuration.save(file);
        } catch(Exception e) { e.printStackTrace(); }
    }

}

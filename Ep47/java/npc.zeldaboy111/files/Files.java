package npc.zeldaboy111.files;

import com.sun.istack.internal.NotNull;
import npc.zeldaboy111.Main;

import java.io.File;
import java.util.ArrayList;

public enum Files {
    NPC("npc.yml"),
    ;

    private File dataFolder;
    private String path;
    private Boolean isPreMade;
    private FileCreator fileCreator;
    private Boolean fileCreated;

    Files(@NotNull String path) {
        this.path = path;
        this.isPreMade = false;
        this.dataFolder = Main.plugin.getDataFolder();
        prepareAndCreateFile();
    }
    Files(@NotNull String path, @NotNull Boolean isPreMade) {
        this.path = path;
        this.isPreMade = (isPreMade == null ? false : isPreMade);
        prepareAndCreateFile();
    }
    private void prepareAndCreateFile() {
        fileCreated = false;
        try {
            fileCreator = new FileCreator(getAbsolutePath(), isPreMade);
            fileCreated = fileCreator.doesFileExist();
        } catch(Exception e) { e.printStackTrace(); }
    }

    public String getPath() {
        return path;
    }
    public String getAbsolutePath() {
        return dataFolder.toString() + "\\" + getPath();
    }

    /**
     *  Functions below are used to manage the files
     */
    public Boolean reload() {
        if(!fileCreated || !fileCreator.doesFileExist()) return false;
        fileCreator.reload();
        return true;
    }

    //write & get functions
    private Boolean areParametersValid(String path) {
        return path != null && !path.trim().equals("") && fileCreator.getConfiguration() != null && fileCreator.getConfiguration().get(path) != null;
    }

    public String getString(String path) {
        return areParametersValid(path) ? fileCreator.getConfiguration().getString(path) : null;
    }
    public ArrayList<String> getStringArray(String path) {
        return getStringArray(path, true);
    }
    public ArrayList<String> getStringArray(String path, Boolean returnAsNull) {
        if(!areParametersValid(path)) return returnAsNull ? null : new ArrayList<>();
        else return (ArrayList<String>)fileCreator.getConfiguration().getStringList(path);
    }

    public void set(String path, Object value) {
        save(path, value);
    }
    public void save(String path, Object value) {
        if(fileCreator.getConfiguration() == null || path == null || path.trim().equals("")) return;
        fileCreator.getConfiguration().set(path, value);
        fileCreator.save();
    }

    @Override
    public String toString() {
        return "\nPath: " + getPath() +
                "\nAbsolute path: " + getAbsolutePath() +
                "\nPre-made: " + isPreMade +
                "\nFile created: " + fileCreated +
                "\n";
    }

}

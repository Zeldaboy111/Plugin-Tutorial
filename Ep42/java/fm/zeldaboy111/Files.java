package fm.zeldaboy111;

import com.sun.istack.internal.NotNull;

import java.io.File;

public enum Files {
    FILE1("file1.yml"),
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


    @Override
    public String toString() {
        return "\nPath: " + getPath() +
                "\nAbsolute path: " + getAbsolutePath() +
                "\nPre-made: " + isPreMade +
                "\nFile created: " + fileCreated +
                "\n";
    }

}

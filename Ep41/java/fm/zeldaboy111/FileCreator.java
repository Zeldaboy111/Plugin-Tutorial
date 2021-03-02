package fm.zeldaboy111;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.io.File;

public class FileCreator {

    private File file;
    public FileCreator(String fileAsString, Boolean isPreMade) throws Exception {
        if(fileAsString == null || fileAsString.trim().equals("")) throw new NullPointerException("fileAsString");
        file = new File(fileAsString);
        if(file == null) throw new NullPointerException("file (" + fileAsString + ")");
        if(file.exists()) return;
        tryCreateFileFolders();
        if(isPreMade) saveFile();
        else createFile();
    }
    private void tryCreateFileFolders() throws Exception {
        File folder = getFolderFromFile();
        if(!folder.exists()) folder.mkdirs();
    }
    private File getFolderFromFile() throws Exception {
        // "plugins/[pluginname]/[filename].yml"
        String[] dirFolderSplitted = file.getAbsolutePath().split("/");
        if(dirFolderSplitted.length < 2) throw new NullPointerException(file.getAbsolutePath());
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

    }

}

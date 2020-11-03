package tutorial.zeldaboy111.YML;

import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;

public class ymlHandler {
    private static YML yml = YML.testFile;

    public static void setLogin(Player p) {
        UUID uuid = p.getUniqueId();
        setLastLogin(uuid);

        if(yml.getData().get("data." + uuid) == null || yml.getData().get("data." + uuid + ".firstLogin") == null) {
            Date now = new Date();
            SimpleDateFormat date = new SimpleDateFormat("dd-mm-yyyy");
            SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");

            now.setTime(System.currentTimeMillis());

            yml.getData().set("data." + uuid + ".firstLogin", (Object)date.format(now) + " " + time.format(now));
            yml.saveFile();
        }
    }

    private static void setLastLogin(UUID uuid) {
        Date now = new Date(0);
        SimpleDateFormat date = new SimpleDateFormat("dd-mm-yyyy");
        SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss");

        now.setTime(System.currentTimeMillis());

        yml.getData().set("data." + uuid + ".lastLogin", (Object)date.format(now) + " " + time.format(now));
        yml.saveFile();
    }

}

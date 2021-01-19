package me.zeldaboy111.holograms;

import me.zeldaboy111.storage.Yml;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HologramUtil {
    private static HologramUtil clazz;
    private HologramUtil() { }
    public static HologramUtil getInstance() { return clazz == null ? (clazz = new HologramUtil()) : clazz; }

    private ArrayList<String> hologramIdList = new ArrayList<>();
    private HashMap<String, IHologram> holograms = new HashMap<>();

    public void init() {
        List<String> hologramList;
        if((hologramList = Yml.holograms.getData().getStringList("HologramList")) != null) {
            for(String id : hologramList) {
                //List text = Yml.holograms.getData().getStringList("Data." + id);
                Location loc = Yml.holograms.getData().getLocation("Data." + id + ".location");
                if(loc != null) createHologram(id, loc);
            }
        }
    }
    public void createHologram(String id, Location location) {
        hologramIdList.add(id);
        saveIdList();
        holograms.put(id, new Hologram(id, location));
    }
    public void deleteHologram(String id) {

    }

    private void saveIdList() {
        Yml.holograms.getData().set("HologramList", hologramIdList);
        Yml.holograms.saveFile();
    }
    private IHologram getHologram(String id) {
        return holograms.get(id);
    }
    public Boolean checkIfHologramExists(String id) { return getHologram(id) != null; }

}

package me.zeldaboy111.holograms;

import me.zeldaboy111.storage.Yml;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.HashMap;

public class HologramUtil {
    private static HologramUtil clazz;
    private HologramUtil() { }
    public static HologramUtil getInstance() { return clazz == null ? (clazz = new HologramUtil()) : clazz; }

    private ArrayList<String> hologramIdList = new ArrayList<>();
    private HashMap<String, IHologram> holograms = new HashMap<>();

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
    private Boolean checkIfHologramExists(String id) { return getHologram(id) != null; }

}

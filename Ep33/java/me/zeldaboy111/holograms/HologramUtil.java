package me.zeldaboy111.holograms;

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
        //TODO save id list
        holograms.put(id, new Hologram(id, location));
    }
    public void deleteHologram(String id) {

    }

    private IHologram getHologram(String id) { }
    private Boolean checkIfHologramExists(String id) { return getHologram(id) != null; }

}

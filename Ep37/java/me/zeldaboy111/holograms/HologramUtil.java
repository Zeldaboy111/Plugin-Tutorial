package me.zeldaboy111.holograms;

import me.zeldaboy111.storage.Yml;
import org.bukkit.Location;
import org.bukkit.entity.Player;

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
    public void reloadAll(Player p) {
        for(IHologram holo : holograms.values()) holo.refresh();
        if(p != null) p.sendMessage(HoloCmd.prefix + "You have reloaded all holograms!");
    }
    public void reload(String id, Player p) {
        reload(id);
        if(p != null) p.sendMessage(HoloCmd.prefix + "You have reloaded the hologram §e" + id + "§7!");
    }
    public void reload(String id) {
        IHologram holo = getHologram(id);
        if(holo != null) holo.refresh();
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

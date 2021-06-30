package npc.zeldaboy111;

import npc.zeldaboy111.files.Files;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class NpcUtils {
    private static NpcUtils clazz;
    public static NpcUtils getInstance() { return clazz == null ? (clazz = new NpcUtils()) : clazz; }
    private NpcUtils() { }

    private static final HashMap<String, INpc> npcIdList = new HashMap<>();
    private static final HashMap<Integer, INpc> npcIdListById = new HashMap<>();
    private static final ArrayList<String> npcIdArrayList = new ArrayList<>();

    public void load() {
        npcIdList.clear();
        npcIdListById.clear();
        npcIdArrayList.clear();
        ArrayList<String> idListFromFile = Files.NPC.getStringArray("NpcList", false);
        if(idListFromFile.isEmpty()) return;
        for(String id : idListFromFile) {
            Location location = Files.NPC.getLocation("npc."+id+".location");
            if(location != null) create(id, location);
        }
    }
    public void hideAll() {
        for(INpc npc : npcIdList.values()) npc.hide();
    }

    public void create(String id, Location location) {
        INpc npc = new Npc(id, location);
        npcIdList.put(id, npc);
        npcIdListById.put(npc.getEntityId(), npc);
        npcIdArrayList.add(id);
        saveNpcList();
    }
    private void saveNpcList() {
        Files.NPC.save("NpcList", npcIdArrayList);
    }
    public void delete(String id) {
        INpc npc = npcIdList.get(id);
        if(npc == null) return;
        npc.hide();
        npcIdList.remove(id);
        npcIdListById.remove(npc.getEntityId());
        npcIdArrayList.remove(id);
        saveNpcList();
        Files.NPC.save("npc."+id, null);
    }
    public Boolean doesNpcExist(String id) { return npcIdList.get(id) != null; }


    public void showNpcs() {
        for(Player player : Bukkit.getServer().getOnlinePlayers()) showNpcs(player);
    }
    public void showNpcs(Player player) {
        for(INpc npc : npcIdList.values()) {
            npc.show(player);
        }
    }
    public void hideNpcs(Player player) {
        for(INpc npc : npcIdList.values()) npc.hide();
    }
    public INpc getNpcFromEntityId(int entityId) { return npcIdListById.get(entityId); }
    
}

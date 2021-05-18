package npc.zeldaboy111;

import org.bukkit.Bukkit;
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


    public void showNpcs() {
        for(Player player : Bukkit.getServer().getOnlinePlayers()) showNpcs(player);
    }
    public void showNpcs(Player player) {

    }
    public void hideNpcs() {
        for(Player player : Bukkit.getServer().getOnlinePlayers()) hideNpcs(player);
    }
    public void hideNpcs(Player player) {

    }
    
}

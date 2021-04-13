package npc.zeldaboy111;

public class NpcUtils {
    private static NpcUtils clazz;
    public static NpcUtils getInstance() { return clazz == null ? (clazz = new NpcUtils()) : clazz; }
    private NpcUtils() { }


    
}

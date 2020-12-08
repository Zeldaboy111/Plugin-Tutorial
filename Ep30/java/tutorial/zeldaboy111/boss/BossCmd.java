package tutorial.zeldaboy111.boss;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import tutorial.zeldaboy111.Main;

public class BossCmd {
    private static BossCmd instance;
    private BossCmd() { }
    public static BossCmd getInstance() { return instance == null ? (instance = new BossCmd()) : instance; }

    public void execute(Player p, String[] args) {
        if(!p.hasPermission("boss.spawn")) p.sendMessage("You do not have enough permissions.");
        else summonBoss(p);
    }
    private void summonBoss(Player p) {
        p.sendMessage("Spawning boss in 5 seconds on your current location...");
        Location loc = p.getLocation();
        Bukkit.getServer().getScheduler().runTaskLater(Main.plugin, new Runnable() {
            public void run() {
                spawn(loc);
            }
        }, 100L);
    }
    private void spawn(Location loc) {
        if(loc == null) return;
        Zombie e = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
        e.setBaby(false);
        e.setCustomNameVisible(true);
        e.setCustomName("§4§lZombie Boss");

    }

}

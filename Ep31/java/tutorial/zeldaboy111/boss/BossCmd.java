package tutorial.zeldaboy111.boss;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import tutorial.zeldaboy111.Main;

import java.util.ArrayList;
import java.util.Random;

public class BossCmd {
    private static BossCmd instance;
    private BossCmd() { }
    public static BossCmd getInstance() { return instance == null ? (instance = new BossCmd()) : instance; }

    public void execute(Player p, String[] args) {
        if(!p.hasPermission("boss.spawn")) p.sendMessage("You do not have enough permissions.");
        else summonBoss(p);
    }
    private void summonBoss(Player p) {
        p.sendMessage("Spawning boss in 2 seconds on your current location...");
        Location loc = p.getLocation();
        Bukkit.getServer().getScheduler().runTaskLater(Main.plugin, new Runnable() {
            public void run() {
                spawn(loc);
            }
        }, 40L);
    }
    private void spawn(Location loc) {
        if(loc == null) return;
        Zombie e = (Zombie) loc.getWorld().spawnEntity(loc, EntityType.ZOMBIE);
        e.setBaby(false);
        e.setCustomNameVisible(true);
        e.setCustomName("§4§lZombie Boss");
        e.setMaxHealth(200);
        e.setHealth(e.getMaxHealth());
        startBossBattle(e);
    }

    private void startBossBattle(Zombie boss) {
        if(boss == null || boss.isDead()) return;
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.plugin, new Runnable() {
            public void run() {
                if(boss == null || boss.isDead()) return;
                castSkill(boss, getRandom(4));
            }
        }, 20, 40);
    }

    private void castSkill(Zombie boss, int skill) {
        if(skill == 0) skill_tnt(boss);
        else if(skill == 1) Bukkit.getServer().broadcastMessage("SKILL: ZOMBIE");
        else Bukkit.getServer().broadcastMessage("SKILL SKIPPED");

    }

    private void skill_tnt(Zombie boss) {
        Bukkit.getServer().broadcastMessage("SKILL: TNT");
        Player target = getRandomTarget(boss);
        if(target == null) return;
        TNTPrimed tnt = (TNTPrimed) boss.getWorld().spawnEntity(boss.getLocation(), EntityType.PRIMED_TNT);
        tnt.setFuseTicks(15);
        tnt.setVelocity(target.getLocation().getDirection().multiply(-1.0));
    }

    private Player getRandomTarget(Zombie boss) {
        ArrayList<Player> targetList = new ArrayList<>();
        for(Entity e : boss.getNearbyEntities(15, 15, 15)) {
            if(e instanceof Player) targetList.add((Player)e);
        }

        return targetList.isEmpty() ? null : targetList.get(getRandom(targetList.size()));
    }
    private int getRandom(int maxRandom) { return new Random().nextInt(maxRandom); }

}

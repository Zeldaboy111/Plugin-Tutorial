package me.zeldaboy111.holograms;

import me.zeldaboy111.storage.Yml;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

import java.util.List;

public class Hologram implements IHologram {
    private static final double lineSpacing = -0.28;
    private double spacing;


    private String id;
    private Location location;
    private String[] text;
    private ArmorStand[] holograms;

    public Hologram(String id, Location location) {
        this.id = id;
        this.location = location;
        this.spacing = 0.0;
        initText();
        killNearbyHolograms();
        initStands();
        save();
    }

    @Override
    public void show() {
        if(holograms == null || text == null || holograms.length < 1 || text.length < 1) return;
        for(int id = 0; id < text.length; id++)
            if(text[id] != null) showHologramFromId(id);
    }
    private void showHologramFromId(int id) {
        if(checkIfStandExists(id)) return;
        ArmorStand stand = getNewArmorStand(id);
        if(stand != null) holograms[id] = stand;
    }
    private ArmorStand getNewArmorStand(int id) {
        if(location == null) return null;
        ArmorStand stand = (ArmorStand)location.getWorld().spawnEntity(location.clone().add(0.0, spacing, 0.0), EntityType.ARMOR_STAND);
        stand.setCustomName(ChatColor.translateAlternateColorCodes('&', text[id]));
        stand.setInvulnerable(true);
        stand.setCollidable(false);
        stand.setArms(false);
        stand.setSmall(true);
        stand.setGravity(false);
        stand.setCustomNameVisible(true);
        stand.setVisible(false);

        spacing += lineSpacing;
        return stand;
    }

    @Override
    public void hide() {
        if(holograms == null || text == null || holograms.length < 1 || text.length < 1) return;
        for(int id = 0; id < holograms.length; id++) {
            if(holograms[id] != null) holograms[id].remove();
        }
    }

    @Override
    public void delete() {

    }

    @Override
    public void refresh() {
        hide();
        Yml.holograms.reload();
        Location newLoc = Yml.holograms.getData().getLocation("Data." + id + ".location");
        if(newLoc != null) this.location = newLoc;
        this.spacing = 0.0;
        initText();
        killNearbyHolograms();
        initStands();
        show();
    }

    @Override
    public void moveToLocation(Location location) {
        hide();
        this.location = location;
        show();
    }

    @Override
    public Location getLocation() {
        return location.clone();
    }
    private void initText() {
        List<String> textFromFile = Yml.holograms.getData().getStringList("Data." + id + ".text");
        text = textFromFile == null || textFromFile.isEmpty() ? new String[] { id } : new String[textFromFile.size()];
        if(text[0] != null && text[0].equals(id)) return;
        for(int i = 0; i < textFromFile.size(); i++) text[i] = textFromFile.get(i);
    }
    private void killNearbyHolograms() {
        if(text == null || text.length < 1) return;
        try {
            for(int i = 0; i < text.length; i++) {
                Location loc = this.location.clone().add(0.0, i*lineSpacing, 0.0);
                for(Entity e : loc.getWorld().getNearbyEntities(loc, 0.2, 0.2, 0.2)) {
                    if(e instanceof ArmorStand && e.getCustomName() != null &&
                            e.getCustomName().equals(ChatColor.translateAlternateColorCodes('&', text[i]))) {
                        e.remove();
                        ((ArmorStand)e).setHealth(0.0);
                    }
                }
            }
        } catch(Exception e) { System.out.println("[Hologram] Failed to kill all nearby entities for the hologram " + id + "."); }

    }
    private void initStands() {
        if(text == null || text.length <= 0) return;
        holograms = new ArmorStand[text.length];
        show();
    }
    private void save() {
        Yml.holograms.getData().set("Data." + id + ".text", text);
        Yml.holograms.getData().set("Data." + id + ".location", location);
        Yml.holograms.saveFile();
    }

    private Boolean checkIfStandExists(int id) {
        if(holograms == null || text == null || holograms.length < id || holograms[id] == null) return false;
        return !holograms[id].isDead() && holograms[id].getHealth() > 0.0;
    }

}

package me.zeldaboy111.holograms;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;

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

        // Text, clear systeem voor andere stands met dezelfde naam op dezelfde locatie

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
        stand.setCustomName(text[id]);
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

    }

    @Override
    public void delete() {

    }

    @Override
    public void refresh() {

    }

    @Override
    public void moveToLocation() {

    }

    @Override
    public Location getLocation() {
        return location.clone();
    }
    private void initText() {

    }
    private void killNearbyHolograms() {

    }
    private void initStands() {

    }
    private void save() {

    }

    private Boolean checkIfStandExists(int id) {
        if(holograms == null || text == null || holograms.length < id || holograms[id] == null) return false;
        return !holograms[id].isDead() && holograms[id].getHealth() > 0.0;
    }

}

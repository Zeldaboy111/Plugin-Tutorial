package me.zeldaboy111.holograms;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;

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

}

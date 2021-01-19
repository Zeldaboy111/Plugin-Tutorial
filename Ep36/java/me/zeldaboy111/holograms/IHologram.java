package me.zeldaboy111.holograms;

import org.bukkit.Location;

public interface IHologram {
    void show();
    void hide();

    void delete();
    void refresh();

    void moveToLocation();
    Location getLocation();

}

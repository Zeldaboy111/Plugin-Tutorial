package npc.zeldaboy111;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface INpc {

    void setLocation(Location location);

    void refresh();
    void hide();
    void hide(Player player);
    void show();
    void show(Player player);
    int getEntityId();

}

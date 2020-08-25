package tutorial.zeldaboy111.multiversion;

import net.minecraft.server.v1_16_R1.ChatComponentText;
import net.minecraft.server.v1_16_R1.Packet;
import net.minecraft.server.v1_16_R1.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.craftbukkit.v1_16_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;

public class minecraft_1_16_1_R1 implements Minecraft {

    @Override
    public void sendTablist(Player player, String header, String footer) {
        try {
            PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
            Field h = packet.getClass().getDeclaredField("header");
            Field f = packet.getClass().getDeclaredField("footer");
            h.setAccessible(true);
            f.setAccessible(true);

            h.set(packet, new ChatComponentText(header));
            f.set(packet, new ChatComponentText(footer));

            (((CraftPlayer)player).getHandle()).playerConnection.sendPacket((Packet)packet);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}

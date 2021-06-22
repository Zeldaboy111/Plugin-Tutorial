package npc.zeldaboy111.handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_15_R1.Packet;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NpcPipeline {
    private static NpcPipeline clazz;
    public static NpcPipeline getInstance() { return clazz == null ? (clazz = new NpcPipeline()) : clazz; }
    private NpcPipeline() { }

    private static final HashMap<UUID, Channel> channels = new HashMap<>();
    public void inject(Player p) {
        CraftPlayer cp = (CraftPlayer)p;
        Channel channel = cp.getHandle().playerConnection.networkManager.channel;

        channels.put(p.getUniqueId(), channel);
        if(p.isOnline()) initPipeline(p, channel);
    }
    private void initPipeline(Player p, Channel channel) {
        if(channel.pipeline().get("NpcPipeline") != null) channel.pipeline().remove("NpcPipeline");
        addPacketDecoder(p, channel);
    }
    private void addPacketDecoder(Player p, Channel channel) {
        try {
            channel.pipeline().addAfter("decoder", "NpcPipeline", new MessageToMessageDecoder<Packet<?>>() {
                @Override
                protected void decode(ChannelHandlerContext channel, Packet<?> packet, List<Object> arg) {
                    arg.add(packet);
                    readPacket(p, packet);
                }
            });
        } catch(Exception e) { e.printStackTrace(); }
    }
    private void readPacket(Player p, Packet<?> packet) {
        if(packet != null && packet.getClass().getSimpleName().equals("PacketPlayInUseEntity")) handlePacketPlayInUseEntity(p, packet);
    }
    private void handlePacketPlayInUseEntity(Player p, Packet<?> packet) {
        int entityId = (Integer)getObjectFromPacket(packet, "a");
        String action = getObjectFromPacket(packet, "action").toString();
        if(action.equals("INTERACT") || action.equals("INTERACT_AT")) {
            //TODO Npc interaction, with a check between the last click and now
        }
    }
    private Object getObjectFromPacket(Packet<?> packet, String object) {
        try {
            //TODO Three-line method using 'Field'
        } catch(Exception e) { return null; }
    }


}

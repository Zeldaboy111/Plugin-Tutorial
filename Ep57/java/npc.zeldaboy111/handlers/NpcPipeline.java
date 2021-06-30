package npc.zeldaboy111.handlers;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_15_R1.Packet;
import npc.zeldaboy111.INpc;
import npc.zeldaboy111.NpcUtils;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NpcPipeline {
    private static NpcPipeline clazz;
    public static NpcPipeline getInstance() { return clazz == null ? (clazz = new NpcPipeline()) : clazz; }
    private NpcPipeline() { }

    private static final HashMap<UUID, Channel> channels = new HashMap<>();
    private static final HashMap<UUID, Long> lastInteraction = new HashMap<>();
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
            INpc npc;
            if(!checkAndUpdateLastClick(p.getUniqueId())) return;
            else if((npc = NpcUtils.getInstance().getNpcFromEntityId(entityId)) != null) npc.tryExecuteNpcCommands(p);
        }
    }
    private Boolean checkAndUpdateLastClick(UUID uuid) {
        Long lastInteract = lastInteraction.isEmpty() ? 0l : lastInteraction.containsKey(uuid) ? lastInteraction.get(uuid) : 0l;
        if(System.currentTimeMillis()-lastInteract < 500) return false;
        Boolean isInMap = lastInteract > 0;
        lastInteract = System.currentTimeMillis();
        if(isInMap) lastInteraction.replace(uuid, lastInteract);
        else lastInteraction.put(uuid, lastInteract);
        return true;
    }
    private Object getObjectFromPacket(Packet<?> packet, String object) {
        try {
            Field field = packet.getClass().getDeclaredField(object);
            field.setAccessible(true);
            return field.get(packet);
        } catch(Exception e) { return null; }
    }


}

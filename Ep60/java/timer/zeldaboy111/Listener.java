package timer.zeldaboy111;

import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import timer.zeldaboy111.events.TimerResetEvent;
import timer.zeldaboy111.events.TimerStartEvent;
import timer.zeldaboy111.events.TimerStopEvent;
import timer.zeldaboy111.events.TimerTickEvent;

public class Listener implements org.bukkit.event.Listener {
    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {
        Timer.updateTimers();
    }
    @EventHandler
    public void playerQuitEvent(PlayerQuitEvent e) {
        new BukkitRunnable() {
            public void run() {
                Timer.updateTimers();
            }
        }.runTaskLater(Main.plugin, 2);
    }
    @EventHandler
    public void timerStartEvent(TimerStartEvent e) {
        System.out.println("START");
    }
    @EventHandler
    public void timerTickEvent(TimerTickEvent e) {
        System.out.println("TICK: "+e.getTimer().getTime());
    }
    @EventHandler
    public void timerStopEvent(TimerStopEvent e) {
        System.out.println("STOP");
    }
    @EventHandler
    public void timerResetEvent(TimerResetEvent e) {
        System.out.println("RESET");
    }
}

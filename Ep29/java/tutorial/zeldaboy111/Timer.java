package tutorial.zeldaboy111;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public class Timer {
    private int currentDuration;
    private Long timerDuration;
    private Boolean started;
    private Boolean paused;
    private Boolean stopped;

    public Timer(Long timerDuration) {
        this.timerDuration = timerDuration;
        setStarted(false);
        setPaused(false);
        setStopped(false);
    }

    public Boolean start() {
        if(started) return false;
        setStarted(true);
        setStopped(false);
        setPaused(false);
        this.currentDuration = 0;
        new BukkitRunnable() {
            public void run() {
                if(stopped) cancel();
                if(!paused) {
                    if(currentDuration >= timerDuration) {
                        setStarted(false);
                        setStopped(true);
                        cancel();
                        return;
                    }
                    if(currentDuration%20 == 0) Bukkit.getServer().broadcastMessage("§6Timer: §e" + (timerDuration-currentDuration)/20);
                    currentDuration++;
                }
            }
        }.runTaskTimer(Main.plugin, 1, 0);

        return true;
    }
    public void pause() {
        setPaused(true);
    }
    public void unpaused() { setPaused(false); }
    public void stop() {
        setStopped(true);
    }

    private void setStarted(Boolean state) { this.started = state; }
    private void setPaused(Boolean state) { this.paused = state; }
    private void setStopped(Boolean state) { this.stopped = state; }
}

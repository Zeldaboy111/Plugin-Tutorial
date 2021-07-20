package timer.zeldaboy111.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import timer.zeldaboy111.Timer;

public class TimerStartEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() { return handlers; }

    private final Timer timer;
    public TimerStartEvent(Timer timer) { this.timer = timer; }
    public Timer getTimer() { return timer; }
}

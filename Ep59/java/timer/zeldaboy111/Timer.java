package timer.zeldaboy111;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Timer {
    private static final ArrayList<Timer> timers = new ArrayList<>();
    private static final Timer clazz = new Timer(0);
    public static final Timer.TimerManager manager = clazz.new TimerManager();

    private int time,defaultTime, requiredPlayerCount;
    private Boolean deleted;
    static {
        clazz.delete();
    }

    /**
     *  Constructor that will make a Timer that does not start at a certain amount of players but only starts when {@link #start()} is called.
     * @param time
     */
    public Timer(int time) {
        this.time = time;
        this.defaultTime = time;
        deleted = false;
        requiredPlayerCount = -1;
        timers.add(this);
    }

    /**
     *  Constructor that will make a Timer that does start when {@param requiredPlayerCount} players are online and stops when this is not the case. It can be force-started by using {@link #start()}.
     * @param time
     * @param requiredPlayerCount
     */
    public Timer(int time, int requiredPlayerCount) {
        this.time = time;
        this.defaultTime = time;
        this.deleted = false;
        this.requiredPlayerCount = requiredPlayerCount;
        timers.add(this);
        tryStartTimer();
    }

    /**
     *  Gets the current time.
     * @return
     */
    public int getTime() { return time; }
    /**
     *  Gets the default time from the timer.
     * @return
     */
    public int getDefaultTime() { return defaultTime; }
    /**
     *  Gets the required amount of players to start the timer.
     * @return
     */
    public int getRequiredPlayerCount() { return requiredPlayerCount; }

    /**
     *  Method that will set {@link #time} to {@link #defaultTime}
     */
    public void reset() {
        time = defaultTime;
        //TODO Calls event
    }

    /**
     *  Sets {@link #time} to {@param time} if it is more than or equal to one and less than or equal to {@link #defaultTime}
     * @param time
     */
    public void setTime(int time) {
        if(time > -1 && time <= defaultTime) this.time = time;
    }

    /**
     *  Sets {@link #defaultTime} to {@param time} and sets {@link #time} to {@link #defaultTime} if it is more than {@link #defaultTime}
     * @param defaultTime
     */
    public void setDefaultTime(int defaultTime) {
        this.defaultTime = defaultTime;
        if(time > defaultTime) this.time = defaultTime;
    }

    /**
     *  Tries to start the timer
     */
    public void tryStartTimer() {
        if(deleted) return;
        else if(requiredPlayerCount < 1 || Bukkit.getServer().getOnlinePlayers().size() >= requiredPlayerCount) {
            start();
        } else if(requiredPlayerCount > 0) {
            reset();
            stop();
        }
    }

    /**
     *  Starts the timer
     */
    public void start() {
        if(deleted) return;
        manager.add(this);
        //TODO event
    }

    /**
     *  Stops the timer
     */
    public void stop() {
        if(deleted) return;
        manager.remove(this);
        //TODO event
    }

    /**
     *  This method should never be called. It will update the time from the timer.
     */
    public void tick() {
        if(deleted) return;
        time--;
        //TODO Event
    }

    public void delete() {
        //TODO method
    }

    private class TimerManager {
        private ArrayList<Timer> timers;
        private Boolean started;
        private TimerManager() {
            timers = new ArrayList<>();
            started = false;
        }
        public void add(Timer timer) {
            if(!timers.contains(timer)) timers.add(timer);
            tryStartTimers();
        }
        public void remove(Timer timer) {
            if(timers.contains(timer)) timers.remove(timer);
        }
        private void tryStartTimers() {
            if(mustStartTimers()) startTimers();
        }
        private Boolean mustStartTimers() {
            if(started != null && started == true) return false;
            else return timers.size() > 0;
        }
        private Boolean mustTimerTick(Timer timer) {
            return timer != null && timer.getTime() > 1;
        }
        private synchronized void startTimers() {
            if(started || timers.isEmpty()) return;
            started = true;
            new BukkitRunnable() {
                @Override
                public void run() {
                    ArrayList<Timer> newTimers = new ArrayList<>(), stoppedTimers = new ArrayList<>();
                    for(Timer timer : timers) {
                        timer.tick();
                        if(mustTimerTick(timer)) newTimers.add(timer);
                        else stoppedTimers.add(timer);
                    }
                    timers = newTimers;
                    stopTimers(stoppedTimers);
                    if(timers.isEmpty()) {
                        started = false;
                        cancel();
                        return;
                    }
                }
            }.runTaskTimer(Main.plugin, 20, 20);
        }
        private void stopTimers(ArrayList<Timer> stoppedTimers) {
            for(Timer timer : stoppedTimers) {
                timer.stop();
                timer.reset();
            }
        }

    }

}

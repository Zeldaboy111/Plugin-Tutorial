package timer.zeldaboy111;

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
    public void reset() { time = defaultTime; }


    private class TimerManager {

    }

}

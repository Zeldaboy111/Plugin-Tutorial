package tutorial.zeldaboy111.scoreboard;

import org.bukkit.entity.Player;

public interface IScoreboardManager {

    /**
     *  Creates a scoreboard for the player
     * @param p
     */
    void insertPlayer(Player p);

    /**
     *  Removes the scoreboard from the player
     * @param p
     */
    void removePlayer(Player p);

    /**
     *  Updates the scoreboard for the player
     * @param player
     */
    void updateBoard(Player player);

    /**
     *  Updates the team in the scoreboard for all online players
     * @param iTeam
     */
    void updateBoard(IScoreboardTeam iTeam);

    /**
     *  Gets you a IScoreboardTeam via weight
     * @param weight
     * @return
     */
    IScoreboardTeam getTeamFromScoreboard(int weight);


}

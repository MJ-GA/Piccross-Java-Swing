package piccross.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Model class for the piccross game server.
 * @author Mohamed Jouini
 *
 */
public class GameServerModel {

	// list of connected players
	private List<Player> players;
	
	// list of random game configurations submitted by clients
	private List<String> configPatterns;
	
	// flag to indicate whether server will be finalized on last client
	// connection closed
	private boolean finalize;
	
	Random rand = new Random();
	
	/**
	 * Class constructor that initialises the model with empty list
	 * of players and game configurations.
	 */
	public GameServerModel() {
		players = new ArrayList<Player>();
		configPatterns = new ArrayList<String>();
		finalize = false;
	}
	
	/**
	 * Add a connected player to the list
	 * @param p The Player to add
	 */
	public synchronized void addPlayer(Player p) {
		players.add(p);
	}
	
	/**
	 * Remove a player from the system
	 * @param p The Player to remove
	 */
	public synchronized  void removePlayer(Player p) {
		
		players.remove(p);
		isLastClient();
		
	}
	
	/**
	 * Add a new game configuration pattern to the list.
	 */
	public synchronized void addGameConfiguration(String pattern) {
		configPatterns.add(pattern);
	}
	
	/**
	 * Get the number of players connected to server
	 * @return The number of players
	 */
	public int numOfPlayers() {
		return players.size();
	}
	/**
	 * Get the list of players
	 * @return the list of players
	 */
	public List<Player> getPlayers() {
		return players;
	}
	
	/**
	 * Get a game configuration pattern randomly selected from the
	 * list of game configurations.
	 * @return A random game configuration pattern.
	 */
	public String getGameConfiguration() {
		if(configPatterns.isEmpty()) {
			return GameConfiguration.DEFAULT_PATTERN;
		}
		String pattern = configPatterns.get(rand.nextInt(configPatterns.size()));
		return pattern;
	}
	
	/**
	 * Set the finalize field
	 * @param finalize The boolean flag to set the finalize
	 */
	public synchronized void setFinalize(boolean finalize) {
		this.finalize = finalize;
	}
	/**
	 * Get the finalize field
	 * @return true if finalize is checked, else return false
	 */
	public boolean isFinalize() {
		return finalize;
	}
	
	/**
	 * Get the scores of all players in a formatted string.
	 * @return The formatted scores of all players
	 */
	public String getPlayerScores() {
		String s = "Game Results:\n";
		for(Player p : players) {
			s += p.toString() + "\n";
		}
		return s;
	}
	
	public void isLastClient() {
		if(numOfPlayers()==0 && isFinalize()) 
			System.exit(0);
	}
	
}

package piccross.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Model class for the piccross game client.
 * @author Mohamed Jouini.
 *
 */
public class GameClientModel {
	// current game configuration
	private GameConfiguration currGameConfig;
	
	// list of all new game scores not yet sent to server
	private List<Score> newScores;
	
	/**
	 * Class constructor that creates a new client model.
	 */
	public GameClientModel() {
		currGameConfig = null;
		newScores = new ArrayList<Score>();
	}
	
	/**
	 * Get the current game configuration
	 * @return The current GameConfiguration
	 */
	public GameConfiguration getCurrGameConfig() {
		return currGameConfig;
	}
	/**
	 * Add a new score to the list
	 * @param score the Score to add
	 */
	public void addScore(Score score) {
		newScores.add(score);
	}
	/**
	 * Get the list of all new game scores
	 * @return the list of new game scores
	 */
	public List<Score> getNewScores() {
		return newScores;
	}
	
	/**
	 * Set the current game configuration.
	 * @param currGameConfig The current GameConfiguration
	 */
	public void setCurrGameConfig(GameConfiguration currGameConfig) {
		this.currGameConfig = currGameConfig;
	}
	
}

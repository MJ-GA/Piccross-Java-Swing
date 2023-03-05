package piccross.model;

import java.awt.Color;
import java.util.Random;


/**
 * This class stores configuration details of the specific
 * game to be played by the user.
 * 
 * @author Mohamed Jouini.
 *
 */
public class GameConfiguration {

	/** default dimension of the game */
	static final int DEFAULT_DIMENSION = 5;
	/** default input game pattern */
	static final String DEFAULT_PATTERN = "00100,00100,11111,01110,01010";
	
	/** color constants for board squares */
	public static final Color color1 = Color.GREEN;
	public static final Color color2 = Color.YELLOW;
	public static final Color color3 = Color.RED;
	public static final Color color4 = Color.BLACK;
	
	/** game dimension */
	private int dimension;
	/** input pattern */
	private String pattern;
	
	/** random number generator */
	static Random rand = new Random();
	
	/**
	 * Get a default configuration of the game.
	 * @return The default configuration
	 */
	public static GameConfiguration getDefaultConfiguation() {
		GameConfiguration gameConfig = new GameConfiguration();
		gameConfig.setDimension(DEFAULT_DIMENSION);
		gameConfig.setPattern(DEFAULT_PATTERN);
		return gameConfig;
	}
	
	/**
	 * Get a random configuration of the game.
	 * @return The random configuration
	 */
	public static GameConfiguration getRandomConfiguration() {
		GameConfiguration gameConfig = new GameConfiguration();
		gameConfig.setDimension(DEFAULT_DIMENSION);
		gameConfig.setPattern(randomPattern(gameConfig.getDimension()));
		return gameConfig;
	}
	/**
	 * Instantiate a GameConfiguration instance from a binary string
	 * pattern.
	 * @param s the binary string game pattern
	 * @return The GameConfiguration from the string pattern
	 */
	public static GameConfiguration fromString(String s) {
		int d = s.split(",").length;
		GameConfiguration gameConfig = new GameConfiguration();
		gameConfig.setDimension(d);
		gameConfig.setPattern(s);
		return gameConfig;
	}
	
	/**
	 * Set the dimension of the game grid.
	 * @param dimension The dimension of the grid
	 */
	public void setDimension(int dimension) {
		this.dimension = dimension;
	}
	
	/**
	 * Set the input pattern binary string for the game
	 * square pattern.
	 * 
	 * @param pattern The pattern binary string
	 */
	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
	/**
	 * Get the dimension of the game.
	 * @return The dimension of the game
	 */
	public int getDimension() {
		return dimension;
	}
	
	/**
	 * Get the input binary string pattern of the game.
	 * @return The input game pattern string
	 */
	public String getPattern() {
		return pattern;
	}
	
	/**
	 * Generate a random game pattern in binary string format based on
	 * given dimension.
	 * 
	 * @param dimension The dimension of the game board
	 * @return The random pattern
	 */
	private static String randomPattern(int dimension) {
		String pattern = "";
		for(int i = 0; i < dimension; i++) {
			for(int j = 0; j < dimension; j++) {
				if(rand.nextInt() % 2 == 0) {
					pattern += "1";
				}
				else {
					pattern += "0";
				}
			}
			pattern += ",";
		}
		pattern = pattern.substring(0, pattern.length()-1);
		return pattern;
	}
}

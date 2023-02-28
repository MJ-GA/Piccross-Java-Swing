package piccross.model;

public class Score {
	public int points;
	public int timeTaken;
	
	/**
	 * Class constructor that takes parameters.
	 * @param points points of the game 
	 * @param timeTaken time taken in seconds of the game
	 */
	public Score(int points, int timeTaken) {
		this.points = points;
		this.timeTaken = timeTaken;
	}
	
	/**
	 * Get the points of the game
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}
	/**
	 * Get the time taken in the game
	 * @return the time taken in seconds
	 */
	public int getTimeTaken() {
		return timeTaken;
	}
	
	@Override
	public String toString() {
		return "Points=" + points + ", TimeTaken=" + timeTaken + "s";
	}
}
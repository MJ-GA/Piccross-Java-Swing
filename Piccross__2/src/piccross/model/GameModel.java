package piccross.model;

/**
 * This class implements the model for storing game configuration data
 * as well as the grid of squares to reflect user actions.
 * @author Mohamed Jouini
 *
 */
public class GameModel {

	/** current configuration */
	private GameConfiguration gameConfig;
	
	/** 2d array to store input pattern */
	private boolean[][] inputGrid;
	
	/** 2d array to store user selections */
	private boolean[][] playGrid;
	
	/** current score of the game */
	private int score;
	
	/** boolean flag to indicate whether mark is set or not */
	private boolean markSet;
	
	/** number of cells already played in the game */
	private int numCellsPlayed;
	
	/**
	 * Class constructor that creates a default game
	 * configuration and populate the board grid.
	 */
	public GameModel() {
		gameConfig = GameConfiguration.getDefaultConfiguation();
		score = 0;
		numCellsPlayed = 0;
		markSet = false;
		createGrid();
	}
	
	/**
	 * Class constructor that takes a game configuration as
	 * parameter
	 */
	public GameModel(GameConfiguration gameConfig) {
		this.gameConfig = gameConfig;
		score = 0;
		numCellsPlayed = 0;
		markSet = false;
		createGrid();
	}
	
	/**
	 * Get the current score of the game
	 * @return The current score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * Set the current score of the game.
	 * @param score The new score
	 */
	public void setScore(int score) {
		this.score = score;
	}
	
	/**
	 * Set the number of board cells already played.
	 * 
	 * @param numCellsPlayed The number of cells already played
	 */
	public void setNumCellsPlayed(int numCellsPlayed) {
		this.numCellsPlayed = numCellsPlayed;
	}
	
	/**
	 * Get the number of board cells already played.
	 * 
	 * @return The number of board cells already played
	 */
	public int getNumCellsPlayed() {
		return numCellsPlayed;
	}
	

	/**
	 * Get the current game configuration.
	 * 
	 * @return The current game configuration
	 */
	public GameConfiguration getGameConfig() {
		return gameConfig;
	}
	/**
	 * Get the boolean array grid of the input game pattern.
	 * 
	 * @return The input pattern boolean grid array
	 */
	public boolean[][] getInputGrid() {
		return inputGrid;
	}
	/**
	 * Set the mark check box
	 * @param markSet A boolean indicating whether to set the mark
	 */
	public void setMark(boolean markSet) {
		this.markSet = markSet;
	}
	/**
	 * Unset the mark check box
	 * @return True if mark is set, else return False
	 */
	public boolean isMarkSet() {
		return markSet;
	}

	
	
	/**
	 * Get the dimension of the current game board.
	 * @return The current grid dimension of the board
	 */
	public int getBoardDimension() {
		return gameConfig.getDimension();
	}
	
	/**
	 * Get the hint label for a given row of the game pattern. 
	 * @param row The row of the game board
	 * @return The string label for the row
	 */
	public String getRowLabel(int row) {
		String label = "(";
		int D = gameConfig.getDimension();
		int oneCount = 0;
		
		for(int j = 0; j < D; j++) {			
			if(inputGrid[row][j]) {
				oneCount++;
			}
			else if(oneCount > 0) {
				label += oneCount+",";
				oneCount = 0;
			}
		}
		if(oneCount > 0) {
			label += oneCount;
		}
		if(label.charAt(label.length()-1) == ',') {
			label = label.substring(0, label.length()-1);
		}
		label += ")";
		return label;
	}
	
	/**
	 * Get the hint label for a given column of the game pattern. 
	 * @param col The column of the game board
	 * @return The string label for the col
	 */
	public String getColLabel(int col) {
		String label = "(";
		int D = gameConfig.getDimension();
		int oneCount = 0;

		for(int i = 0; i < D; i++) {
			if(inputGrid[i][col]) {
				oneCount++;
			}
			else if(oneCount > 0) {
				label += oneCount+",";
				oneCount = 0;
			}
		}
		if(oneCount > 0) {
			label += oneCount;
		}
		if(label.charAt(label.length()-1) == ',') {
			label = label.substring(0, label.length()-1);
		}
		label += ")";
		return label;
	}
	/**
	 * Mark the cell at given row,column played.
	 *  
	 * @param cellRow The row index of the cell
	 * @param cellCol The column index of the cell
	 */
	public void markCellPlayed(int cellRow, int cellCol) {
		playGrid[cellRow][cellCol] = true;
	}
	/**
	 * Check whether a cell is already selected in the game.
	 * @param cellRow The row index of the cell
	 * @param cellCol The column index of the cell
	 * @return  True if cell is already played, else return false
	 */
	public boolean isCellPlayed(int cellRow, int cellCol) {
		return playGrid[cellRow][cellCol];
	}
	
	/**
	 * Initialize the board grid of squares based on current
	 * game configuration.
	 */
	private void createGrid() {
		int D = gameConfig.getDimension();
		String pattern = gameConfig.getPattern();
		String[] patterns = pattern.split(",");
		
		inputGrid = new boolean[D][D];
		playGrid = new boolean[D][D];
		
		for(int i = 0; i < D; i++) {
			for(int j = 0; j < D; j++) {
				if(patterns[i].charAt(j) == '0') {
					inputGrid[i][j] = false;
				}
				else {
					inputGrid[i][j] = true;
				}
				playGrid[i][j] = false;
			}
		}
	}
}

package piccross.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;

import piccross.model.GameModel;
import piccross.model.Score;
import piccross.view.GameView;

/**
 * The controller class that is responsible for start/restart the game
 * and handling all user interactions with the game view.
 * 
 * @author Mohamed Jouini.
 *
 */
public class GameController implements ActionListener {

	/** view of the game */
	private GameView gameView;
	/** model of the game */
	private GameModel gameModel;
	
	/** the timer */
	private Timer timer;
	
	/** number of seconds elapsed in current game start */
	private int secondElapsed;
	
	/** list of scores of all games played */
	private List<Score> scores;
	
	/**
	 * Class constructor that initializes the controller with the
	 * game view and model.
	 * 
	 * @param gameView The view of the game
	 * @param gameModel The model of the game
	 */
	public GameController(GameView gameView, GameModel gameModel) {
		this.gameModel = gameModel;
		this.gameView = gameView;
		gameView.setupGameBoard(gameModel);
		secondElapsed = 0;
		scores = new ArrayList<Score>();
		gameView.addActionListener(this);
	}
	/**
	 * Get the list of score of all games played.
	 * @return the list of scores
	 */
	public List<Score> getScores() {
		return scores;
	}
	
	/**
	 * Start the game by displaying the view and starting the
	 * timer.
	 */
	public void startGame() {
		gameView.setVisible(true);	
		timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				secondElapsed++;
				gameView.updateTimer(secondElapsed);
			}
		}, 0, 1000);
	}

	/**
	 * Handle all UI interaction events in the GameView
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		// process Mark checkbox event
		if(src instanceof JCheckBox) {
			boolean isChecked = ((JCheckBox)src).isSelected();
			gameModel.setMark(isChecked);
			if(isChecked) {
				gameView.addLogMessage("Mark set;");				
			}
			else {
				gameView.addLogMessage("Mark reset;");
			}
		}
		// process button click event
		else if(src instanceof JButton) {
			String actionCmd = ((JButton)src).getActionCommand();
			if(actionCmd.equals("Reset")) {
				gameView.addLogMessage("Game reset;");
			}
			else {
				gameView.addLogMessage("Pos " + actionCmd + " clicked;");
				String[] posData = actionCmd.split(",");
				// get the row, col index of cell selected
				int cellRow = Integer.parseInt(posData[0]) - 1;
				int cellCol = Integer.parseInt(posData[1]) - 1;
				// process the selection if cell already not selected earlier
				if(!gameModel.isCellPlayed(cellRow, cellCol)) {
					boolean[][] inputGrid = gameModel.getInputGrid();
					boolean isMarkSet = gameModel.isMarkSet();
					// Check if square selected was correct or wrong
					boolean correctSelection = (!isMarkSet && inputGrid[cellRow][cellCol]);
					if(correctSelection) {
						gameModel.setScore(gameModel.getScore() + 1);
						gameView.showCorrectSquareSelection(cellRow, cellCol, inputGrid);
						gameView.updatePoints(gameModel.getScore());
					}
					if(!correctSelection) {
						correctSelection = (isMarkSet && !inputGrid[cellRow][cellCol]);
						if(correctSelection) {
							gameModel.setScore(gameModel.getScore() + 1);
							gameView.updatePoints(gameModel.getScore());
							gameView.showCorrectSquareSelection(cellRow, cellCol, inputGrid);
						}
					}
					if(!correctSelection) {
						gameModel.setScore(gameModel.getScore() - 1);
						if(gameModel.getScore() < 0) {
							gameModel.setScore(0);
						}
						gameView.updatePoints(gameModel.getScore());
						gameView.showWrongSquareSelection(cellRow, cellCol, inputGrid);
					}
					// update board view with selected square
					gameModel.markCellPlayed(cellRow, cellCol);
					// update number of cells played
					gameModel.setNumCellsPlayed(gameModel.getNumCellsPlayed() + 1);
					// Check if game ended
					int numCells = gameModel.getBoardDimension();
					int totalCells = numCells*numCells;
					int perfectScore = totalCells;
					if(gameModel.getNumCellsPlayed() == totalCells) {
						// add the current score to the list
						scores.add(new Score(gameModel.getScore(), secondElapsed));
						// check if game perfect win
						if(gameModel.getScore() == perfectScore) {
							gameView.showPerfectGameMessage();
						}
						else {
							gameView.showLostGameMessage();
						}
						// stop the timer
						timer.cancel();
					}
				}
			}
		}
		// process menu choices selection event
		else if(src instanceof JMenuItem) {
			String actionCmd = ((JMenuItem)src).getActionCommand();
			if(actionCmd.equals("New")) {
				gameView.addLogMessage("Menu item new clicked;");
			}
			else if(actionCmd.equals("Solution")) {
				gameView.addLogMessage("Soluton displayed;");
			}
			else if(actionCmd.equals("Exit")) {
				System.exit(0);
			}
		}
		
	}
}

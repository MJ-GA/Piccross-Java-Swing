package piccross.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import piccross.model.GameConfiguration;
import piccross.model.GameModel;

/**
 * This class implements the view of the game by creating and
 * laying out all the individual UI components of the game.
 * 
 * @author Mohamed Jouini.
 *
 */
public class GameView extends JFrame {

	private JCheckBox markCheckBox;
	private JTextField txtFieldPoints;
	private JTextArea txtAreaLog;
	private JTextField txtFieldTime;
	private JButton btnReset;
	
	private JPanel centrePanel;
	private JButton[][] gridButton;

	JMenuItem newItem;
	JMenuItem solItem;
	JMenuItem exitItem;
	
	// parent frame of game view
	private JFrame parent;
		
	private ActionListener listener;
	
	/**
	 * Class constructor that initialises all the game view
	 * UI components and layout them in the frame.
	 */
	public GameView(JFrame parent) {
		super("Piccross");
		this.parent = parent;
		setLayout(new BorderLayout());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		setSize(new Dimension(800, 700));
		
		centrePanel = new JPanel();
		centrePanel.setLayout(new BorderLayout());
		
		JPanel rightPanel = createRightPanel();
		add(centrePanel, BorderLayout.CENTER);
		add(rightPanel, BorderLayout.EAST);
		
		createMenuOptions();
				
		setLocationRelativeTo(null);
	}
	/**
	 * Set up the board panel with the given game model.
	 * @param gameModel The GameModel storing game data 
	 */
	public void setupGameBoard(GameModel gameModel) {
		int D = gameModel.getBoardDimension();
		centrePanel.removeAll();
		JPanel gridPanel = new JPanel(new GridLayout(D+1, D+1));
		gridButton = new JButton[D][D];
		
		for(int i = 0; i < D+1; i++) {
			for(int j = 0; j < D+1; j++) {
				if(i == 0 && j == 0) {
					gridPanel.add(createMarkPanel());
					continue;
				}
				if(i == 0) {
					String colLabel = gameModel.getColLabel(j-1);
					JLabel lbl = new JLabel(colLabel);
					lbl.setHorizontalAlignment(SwingConstants.CENTER);
					gridPanel.add(lbl);
					continue;
				}
				if(j == 0) {
					String rowLabel = gameModel.getRowLabel(i-1);
					JLabel lbl = new JLabel(rowLabel);
					lbl.setHorizontalAlignment(SwingConstants.CENTER);
					gridPanel.add(lbl);
					continue;
				}
				JButton btnCell = new JButton();
				btnCell.setBackground(Color.white);
				btnCell.setActionCommand(i+","+j);
				gridPanel.add(btnCell);
				gridButton[i-1][j-1] = btnCell;
			}
		}
		updatePoints(gameModel.getScore());
		txtAreaLog.setText("");
		centrePanel.add(gridPanel, BorderLayout.CENTER);
		this.revalidate();
	}
	/**
	 * Register action listener for all UI interaction events.
	 * 
	 * @param listener The ActionListener to register
	 */
	public void addActionListener(ActionListener listener) {
		this.listener = listener;
		markCheckBox.addActionListener(listener);
		btnReset.addActionListener(listener);
		int D = gridButton.length;
		for(int i = 0; i < D; i++) {
			for(int j = 0; j < D; j++) {
				gridButton[i][j].addActionListener(listener);
			}
		}
		newItem.addActionListener(listener);
		solItem.addActionListener(listener);
		exitItem.addActionListener(listener);
	}
	
	/**
	 * Update the seconds passed of the timer in the view.
	 * 
	 * @param currentTime The number of seconds passed.
	 */
	public void updateTimer(int currentTime) {
		txtFieldTime.setText(currentTime + "s");
	}
	
	/**
	 * Update the player game points in the view.
	 * 
	 * @param points The game points.
	 */
	public void updatePoints(int points) {
		txtFieldPoints.setText(points + "");
	}
	/**
	 * Update the board cell with correct selection color.
	 * @param cellRow The row index of the cell
	 * @param cellCol The column index of the cell
	 */
	public void showCorrectSquareSelection(int cellRow, int cellCol, boolean[][] inputGrid) {
		if(inputGrid[cellRow][cellCol]) {
			gridButton[cellRow][cellCol].setBackground(GameConfiguration.color1);
		}
		else {
			gridButton[cellRow][cellCol].setBackground(GameConfiguration.color2);
		}
	}
	
	/**
	 * Update the board cell with wrong selection color.
	 * @param cellRow The row index of the cell
	 * @param cellCol The column index of the cell
	 */
	public void showWrongSquareSelection(int cellRow, int cellCol, boolean[][] inputGrid) {
		gridButton[cellRow][cellCol].setBackground(GameConfiguration.color3);
	}
	
	/**
	 * Add a log message in the game history text area.
	 * 
	 * @param message The message to add.
	 */
	public void addLogMessage(String message) {
		txtAreaLog.append(message + "\n");
	}
	
	/**
	 * Show the pattern solution to the user board.
	 * 
	 * @param inputGrid The boolean grid of the solution
	 */
	public void showSolution(boolean[][] inputGrid) {
		int D = inputGrid.length;
		for(int i = 0; i < D; i++) {
			for(int j = 0; j < D; j++) {
				if(inputGrid[i][j]) {
					gridButton[i][j].setBackground(GameConfiguration.color1);
				}
				else {
					gridButton[i][j].setBackground(GameConfiguration.color2);
				}
			}
		}
	}
	
	/**
	 * Display perfect game banner to user
	 */
	public void showPerfectGameMessage() {
		try {
			Image winnerImg = ImageIO.read(getClass().getResource("/piccross/images/gamepicwinner.png"));
			JLabel winnerLabel = new JLabel(new ImageIcon(winnerImg));
			JOptionPane.showMessageDialog(this, winnerLabel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Display lost game banner to user
	 */
	public void showLostGameMessage() {
		try {
			Image winnerImg = ImageIO.read(getClass().getResource("/piccross/images/gamepicend.png"));
			JLabel winnerLabel = new JLabel(new ImageIcon(winnerImg));
			JOptionPane.showMessageDialog(this, winnerLabel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Create a panel having the mark checkbox.
	 * @return The panel containing the mark checkbox
	 */
	private JPanel createMarkPanel() {
		JPanel p = new JPanel();
		p.setBorder(new EmptyBorder(10, 10, 10, 10));
		markCheckBox = new JCheckBox("Mark");
		p.add(markCheckBox, BorderLayout.CENTER);
		return p;
	}
	
	/**
	 * Create the menu options and at the top
	 */
	private void createMenuOptions() {
		JMenuBar menubar = new JMenuBar();
		JMenu gameMenu = new JMenu("Game");
		gameMenu.setMnemonic(KeyEvent.VK_G);
		
		newItem = new JMenuItem("New", new ImageIcon(getClass().getResource("/piccross/images/piciconnew.gif")));
		newItem.setMnemonic(KeyEvent.VK_N);
		gameMenu.add(newItem);
		
		solItem = new JMenuItem("Solution", new ImageIcon(getClass().getResource("/piccross/images/piciconsol.gif")));
		solItem.setMnemonic(KeyEvent.VK_S);
		gameMenu.add(solItem);
		
		exitItem = new JMenuItem("Exit", new ImageIcon(getClass().getResource("/piccross/images/piciconext.gif")));
		exitItem.setMnemonic(KeyEvent.VK_X);
		gameMenu.add(exitItem);
		
		JMenu helpMenu = new JMenu("Help");
		helpMenu.setMnemonic(KeyEvent.VK_H);
		
		menubar.add(gameMenu);
		menubar.add(helpMenu);
		
		setJMenuBar(menubar);
	}
	
	
	/**
	 * Create a panel containing the logo, points, log message
	 * area and the timer.
	 * 
	 * @return The panel containing the controls
	 */
	private JPanel createRightPanel() {
		JPanel p = new JPanel();
		p.setBorder(new EmptyBorder(5,5,5,5));
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		try {
			Image logoImage = ImageIO.read(getClass().getResource("/piccross/images/piccrossLogo.png"));
			JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
			p.add(logoLabel);
		} catch (IOException e) {
			System.out.println(e);
		}
		JPanel pointPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		pointPanel.add(new JLabel("Points: "));
		txtFieldPoints = new JTextField(5);
		pointPanel.add(txtFieldPoints);
		p.add(pointPanel);
				
		txtAreaLog = new JTextArea(25, 5);
		txtAreaLog.setEditable(false);
		p.add(txtAreaLog);
		
		JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		timePanel.add(new JLabel("Time: "));
		txtFieldTime = new JTextField(5);
		timePanel.add(txtFieldTime);
		p.add(timePanel);
		
		btnReset = new JButton("Reset");
		p.add(btnReset);
		
		return p;
	}
	
	

	
}

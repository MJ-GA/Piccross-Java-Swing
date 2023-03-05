package piccross.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import piccross.controller.DrawGameController;
import piccross.controller.GameController;
import piccross.model.GameConfiguration;
import piccross.model.GameModel;

/**
 * A subclass of JFrame to implement the UI of the Draw Game window.
 * @author Mohamed Jouini.
 *
 */
public class DrawView extends JFrame {

	private JButton[][] btnSquares;
	private JButton btnSave;
	
	// model to store the draw game grid
	private GameModel gameModel;
	
	/**
	 * Class constructor that creates the DrawGame instance with given paremeters.
	 * @param parent the parent window
	 * @param gameModel the game model
	 */
	public DrawView(GameModel gameModel) {
		super("Create Game");
		this.gameModel = gameModel;
		
		getContentPane().setLayout(new BorderLayout());
		
		createGrid();
		createControls();
				
		setSize(600,600);
	}
	
	public void highlightSelectedSquare(int i, int j, boolean selected) {
		if(selected) {
			btnSquares[i][j].setBackground(GameConfiguration.color4);
		}
		else {
			btnSquares[i][j].setBackground(Color.white);
		}
		this.revalidate();
	}
	
	public void addActionListener(ActionListener listener) {
		int d = gameModel.getDrawGameDimension();
		for(int i = 0; i < d; i++) {
			for(int j = 0; j < d; j++) {
				btnSquares[i][j].addActionListener(listener);
			}
		}
		btnSave.addActionListener(listener);
	}
	
	private void createGrid() {
		int d = gameModel.getDrawGameDimension();
		JPanel p = new JPanel(new GridLayout(d, d));
		btnSquares = new JButton[d][d];
		for(int i = 0; i < d; i++) {
			for(int j = 0; j < d; j++) {
				btnSquares[i][j] = new JButton();
				btnSquares[i][j].setBackground(Color.white);
				btnSquares[i][j].setActionCommand(i+","+j);
				p.add(btnSquares[i][j]);
			}
		}
		getContentPane().add(p, BorderLayout.CENTER);
	}
	
	private void createControls() {
		int d = gameModel.getDrawGameDimension();
		JPanel p = new JPanel(new FlowLayout());
		btnSave = new JButton("Save");
		p.add(btnSave);
		getContentPane().add(p, BorderLayout.SOUTH);
	}
	
	
}

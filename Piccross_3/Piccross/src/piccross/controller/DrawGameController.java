package piccross.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;

import javax.swing.JButton;

import piccross.model.GameClientModel;
import piccross.model.GameConfiguration;
import piccross.model.GameModel;
import piccross.view.DrawView;
import piccross.view.GameClientView;

/**
 * The controller class for managing the GameModel and
 * DrawView class.
 * 
 * @author Mohamed Jouini.
 *
 */
public class DrawGameController implements ActionListener {

	private DrawView drawView;
	private GameModel gameModel;
	private GameClientView clientView;
	private GameClientModel clientModel;
	
	public DrawGameController(DrawView drawView, GameClientView clientView, GameModel gameModel, GameClientModel clientModel) {
		this.drawView = drawView;
		this.gameModel = gameModel;
		this.clientView = clientView;
		this.clientModel = clientModel;
		drawView.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		String actionCmd = ((JButton)src).getActionCommand();
		if(actionCmd.equals("Save")) {
			GameConfiguration drawGameConfiguration = gameModel.getDrawGameConfiguration();
			clientModel.setCurrGameConfig(drawGameConfiguration);
			
			clientView.setVisible(true);
			clientView.setEnablePlay(true);
			drawView.dispose();			
		}
		else {
			String[] data = actionCmd.split(",");
			int i = Integer.parseInt(data[0]);
			int j = Integer.parseInt(data[1]);
			boolean selected = !gameModel.getDrawCellMarked(i, j);
			gameModel.setDrawCellMarked(i, j, selected);
			drawView.highlightSelectedSquare(i, j, selected);
		}
	}
	
}

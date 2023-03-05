package piccross.server;

import piccross.controller.GameServerController;
import piccross.model.GameServerModel;
import piccross.view.GameServerView;

/**
 * The main driver class that creates the game server controller, view and model
 * and display the view to user.
 * 
 * @author Mohamed Jouini
 *
 */
public class GameServer {

	public static void main(String[] args) {
		// instantiate the server view
		GameServerView gameServerView = new GameServerView();
		// instantiate the server model
		GameServerModel gameServerModel = new GameServerModel();
		// instantiate the server controller and run the server
		new GameServerController(gameServerView, gameServerModel);
		// display the server view
		gameServerView.setLocationRelativeTo(null);
		gameServerView.setVisible(true);		
	}

}

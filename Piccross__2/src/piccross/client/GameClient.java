package piccross.client;

import piccross.controller.GameClientController;
import piccross.model.GameClientModel;
import piccross.view.GameClientView;

/**
 * The main driver class for the client program that creates the client controller
 * , client view, client model and starts the client view.
 * 
 * @author Mohamed Jouini
 *
 */
public class GameClient {

	public static void main(String[] args) {
		// instantiate client view
		GameClientView gameClientView = new GameClientView();
		// instantiate client model
		GameClientModel gameClientModel = new GameClientModel();
		// instantiate client controller
		GameClientController clientController = new GameClientController(gameClientView, gameClientModel);
		// start the client
		clientController.startClient();
	}

}

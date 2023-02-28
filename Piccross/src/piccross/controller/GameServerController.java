package piccross.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JCheckBox;

import piccross.model.GameServerModel;
import piccross.model.Player;
import piccross.server.ClientHandler;
import piccross.view.GameServerView;

/**
 * The server controller class for managing the GameServerModel and
 * GameServerView class.
 * 
 * @author Mohamed Jouini
 *
 */
public class GameServerController implements ActionListener {

	// server socket
	private ServerSocket servSocket;	
	// game server view
	private GameServerView gameServerView;
	// game server model
	private GameServerModel gameServerModel;
	
	/**
	 * Class constructor that initialises the controller with view and model.
	 * @param gameServerView The server view
	 * @param gameServerModel The server model
	 */
	public GameServerController(GameServerView gameServerView, GameServerModel gameServerModel) {
		this.gameServerView = gameServerView;
		this.gameServerModel = gameServerModel;
		gameServerView.addActionListener(this);
	}
	
	/**
	 * Start the server to accept connections from clients.
	 */
	public void startServer() {
		// get the port from server view
		int port = gameServerView.getServerPort();		
		if(port > 0) {
			try {
				// create server socket with given port
				gameServerView.addLogMessage("Done.");
				gameServerView.addLogMessage("port=" + port);
				servSocket = new ServerSocket(port);
				gameServerView.addLogMessage("Waiting for clients to connect...");
				// run the server in separate thread
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// loop to accept multiple client connections
						while(true) {
							try {
								// get the next client connection
								Socket clientSock = servSocket.accept();
								// start server thread to handle the client
								ClientHandler handler = new ClientHandler(clientSock, gameServerModel, gameServerView);
								
								new Thread(handler).start();
								
								gameServerView.addLogMessage("Accepted new client connection...");
								gameServerView.addLogMessage("Number of clients: " + gameServerModel.numOfPlayers());
							} catch (IOException e) {
								gameServerView.showError(e.toString());
							}
						}
					}
				}).start();
			} catch (IOException e) {
				gameServerView.showError(e.toString());
			}
		}
	}
	
	/**
	 * Stop the server by terminating all existing client 
	 * connections.
	 */
	public void endServer() {
		// disconnect all existing players
		for(Player p : gameServerModel.getPlayers()) {
			p.disconnect();
		}
		// close the server socket
		try {
			servSocket.close();
			servSocket = null;
		} catch (IOException e) {
			gameServerView.showError(e.toString());
		}
		// terminate the server program
		System.exit(0);
	}

	/**
	 * Handle all the UI interaction events in server view.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		
		if(src instanceof JCheckBox) {
			JCheckBox chbox = (JCheckBox)src;
			gameServerModel.setFinalize(chbox.isSelected());
		}
		else {
			String actionCmd = ((JButton)src).getActionCommand();
			if(actionCmd.equals("Execute")) {
				gameServerView.addLogMessage("Server execution...");
				startServer();	
			}
			else if(actionCmd.equals("End")) {
				endServer();
			}
			else if(actionCmd.equals("Results")) {
				String playerScores = gameServerModel.getPlayerScores();
				gameServerView.showMessage(playerScores);
			}
		}
	}
	
}

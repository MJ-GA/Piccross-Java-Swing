package piccross.controller;

import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import piccross.model.GameClientModel;
import piccross.model.GameConfiguration;
import piccross.model.GameModel;
import piccross.model.Protocol;
import piccross.model.Score;
import piccross.view.DrawView;
import piccross.view.GameClientView;
import piccross.view.GameView;

/**
 * The client controller class for managing the GameClientModel and
 * GameClientView class.
 * 
 * @author Mohamed Jouini.
 *
 */
public class GameClientController implements ActionListener {

	// ID of this client received from server
	private int clientId;
	// socket connection with server
	private Socket sock;
	// output stream to socket
	private PrintWriter sockOut;
	// input stream to socket
	private BufferedReader sockIn;
	// reference to client view 
	private GameClientView gameClientView;
	// reference to client model 
	private GameClientModel gameClientModel;	
	//New game config
	GameConfiguration newgameConfig ;
	
	/**
	 * Class constructor that creates the client controller
	 * 
	 * @param gameClientView the GameClientView instance
	 * @param gameClientModel the GameClientModel instance
	 */
	public GameClientController(GameClientView gameClientView, GameClientModel gameClientModel) {
		this.gameClientModel = gameClientModel;
		this.gameClientView = gameClientView;
		this.sock = null;
		gameClientView.addActionListener(this);
	}
	
	/**
	 * Start the client program by displaying the view
	 */
	public void startClient() {
		gameClientView.setLocationRelativeTo(null);
		gameClientView.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object src = e.getSource();
		if(src instanceof JButton) {
			String actionCmd = ((JButton)src).getActionCommand();
			
			// ---- CONNECT ----
		 if(actionCmd.equals("Connect")) {				
				// get server from view text field
				String server = gameClientView.getServer();
				// get port from view text field
				int port = gameClientView.getPort();
				// validate server name is not empty
				if(server.isEmpty() || port < 0) {
					gameClientView.showError("Invalid server/port specified!");
					return;
				}
				// get user name from view text field and validate its not empty
				String user = gameClientView.getUser();
				if(user.isEmpty()) {
					gameClientView.showError("User name is not specified!");
					return;
				}
				// log connect message
				gameClientView.addLogMessage("Connect...");
				gameClientView.addLogMessage("User=" + user);
				gameClientView.addLogMessage("Server=" + server);
				gameClientView.addLogMessage("Port=" + port);
				// connect to server
				try {
					sock = new Socket(server, port);
				} catch (UnknownHostException e1) {
					gameClientView.showError(e1.toString());
					return;
				} catch (IOException e1) {
					gameClientView.showError(e1.toString());
					return;
				}
				gameClientView.setEnableConnect(false);
				gameClientView.setEnableEnd(true);
				gameClientView.setEnableDrawGame(true);
				// create output/input stream to connected socket
				try {
					sockOut = new PrintWriter(sock.getOutputStream());
					sockIn = new BufferedReader(new InputStreamReader(sock.getInputStream()));
					gameClientView.addLogMessage("Connection established to server...");
					// send the user name to server
					sockOut.println(user);
					sockOut.flush();
					// receive the player ID from server
					String response = sockIn.readLine();
					clientId = Integer.parseInt(response);
					gameClientView.addLogMessage("Received player id = " + clientId);
					// enable all buttons
					gameClientView.setEnableNewGame(true);
					gameClientView.setEnableReceiveGame(true);
					gameClientView.setEnableSendData(true);
					return;
				} catch (IOException e1) {
					gameClientView.showError(e1.toString());
					return;
				}								
			}
			// ---- END -----
			else if(actionCmd.equals("End")) {
				// send QUIT message to server
				String message = Protocol.createQuitMessage(clientId);
				sockOut.println(message);
				sockOut.flush();
				gameClientView.showMessage("You are disconnected from server. Press OK to Quit");
				System.exit(0);
			}
			// ---- NEW GAME -----
			else if(actionCmd.equals("New Game")) {
				// get a random game configuration
				newgameConfig = GameConfiguration.getRandomConfiguration();
				gameClientView.addLogMessage("New Game created..." +newgameConfig.getPattern());
				gameClientView.setEnableSendGame(true); 
			}
			else if(actionCmd.equals("Send Game")) {
				// get a random game configuration
				
				gameClientView.addLogMessage("game configuration " + newgameConfig.getPattern() + " sent to server.");
				 // send game configuration to server
					String message = Protocol.createSendGameMessage(clientId, newgameConfig.getPattern());
					sockOut.println(message);
					sockOut.flush();
			}
		
			// ---- SEND DATA ----
			else if(actionCmd.equals("Send Data")) {
				gameClientView.addLogMessage("Send Data...");
				// get the current list of scores from model
				List<Score> scores = gameClientModel.getNewScores();
				// if empty show error message and cancel
				if(scores.isEmpty()) {
					String errMsg = "You did not play any recent game or last score\n"+
				                    "has already been sent!";
					gameClientView.showError(errMsg);
					gameClientView.addLogMessage("Aborted...no data to send.");
				}
				// else send the scores data to server
				else {
					// prepare message with score to send
					String message = Protocol.createSendDataMessage(clientId, scores);
					// send message to server
					sockOut.println(message);
					sockOut.flush();
					gameClientView.addLogMessage("New game scores sent to server...");
				}
			}
			// ---- RECEIVE GAME ---
			else if(actionCmd.equals("Receive Game")) {
				// prepare message for receive game and send to server
				String message = Protocol.createReceiveGameMessage(clientId);
				sockOut.println(message);
				sockOut.flush();
				// receive the game configuration from server
				try {
					String response = sockIn.readLine();
					String[] fields = response.split(Protocol.FIELD_SEPARATOR);
					String pattern = fields[2];
					gameClientView.showMessage("received game configuration " + pattern);
					// save the game configuration to model
					GameConfiguration gameConfig = GameConfiguration.fromString(pattern);
					gameClientModel.setCurrGameConfig(gameConfig);
					// enable the play button in view
					gameClientView.setEnablePlay(true);
				} catch (IOException e1) {
					gameClientView.showError(e1.toString());
				}
			}
			// ---- PLAY ----
			else if(actionCmd.equals("Play")) {
				gameClientView.setEnablePlay(false);
				gameClientView.addLogMessage("Play...");
				// get current received game configuration
				GameConfiguration currGameConfig = gameClientModel.getCurrGameConfig();
				// create the GameModel
				GameModel gameModel = new GameModel(currGameConfig);
				// create the GameView
				GameView gameView = new GameView(gameClientView);
				// create the GameController
				GameController gameController = new GameController(gameView, gameModel);
				gameController.startGame();
				gameClientView.addLogMessage("Starting game with configuration " + currGameConfig.getPattern());
				gameClientView.setVisible(false);
				gameView.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						// get the list of score of all games played
						List<Score> currScores = gameController.getScores();
						// add to the list of scores
						for(Score s : currScores) {
							gameClientModel.addScore(s);
						}
						gameClientView.setVisible(true);
					}
				});				
			}
		 // -- DRAW GAME -- 
			else if(actionCmd.equals("Draw Game")) {
				gameClientView.addLogMessage("Draw Game...");
				Object[] dimensions = {"1","2","3","4","5","6","7","8","9","10"};
				Object selection = JOptionPane.showInputDialog(null, "Create Frame", "Select game dimension:", JOptionPane.PLAIN_MESSAGE, null, dimensions, dimensions[0]);
				if(selection != null) {
					int dimension = Integer.parseInt((String)selection);
					GameModel gameModel = new GameModel();
					gameModel.setDrawGameDimension(dimension);
					DrawView drawView = new DrawView(gameModel);					
					DrawGameController controller = new DrawGameController(drawView, gameClientView, gameModel, gameClientModel);
					gameClientView.setVisible(false);
					drawView.setLocationRelativeTo(null);
					drawView.setVisible(true);
					
				}
				
			}
		}
	}
	
}

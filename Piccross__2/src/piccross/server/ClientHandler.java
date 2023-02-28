package piccross.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import piccross.model.GameServerModel;
import piccross.model.Player;
import piccross.model.Protocol;
import piccross.model.Score;
import piccross.view.GameServerView;
/**
 * Server thread that handles communication with a specific client
 * connection.
 * 
 * @author Mohamed Jouini
 *
 */
public class ClientHandler implements Runnable {

	// the client socket connection
	private Socket clientSock;
	// server game model
	private GameServerModel serverModel;
	// server view
	private GameServerView serverView;
	// player being handled 
	private Player player;
	
	/**
	 * Class constructor that creates a new ClientHandler with given
	 * parameters.
	 * 
	 * @param clientSock The client socket connection
	 * @param serverModel The game server model
	 */
	public ClientHandler(Socket clientSock, GameServerModel serverModel, GameServerView serverView) throws IOException {
		this.clientSock = clientSock;
		this.serverModel = serverModel;
		this.serverView = serverView;
		try {
			// create output/input streams to client socket
			PrintWriter sockOut = new PrintWriter(clientSock.getOutputStream());
			BufferedReader sockIn = new BufferedReader(new InputStreamReader(clientSock.getInputStream()));
			// read the player name from socket
			String name = sockIn.readLine();
			// get the next player id from model
			int nextPlayerID = serverModel.numOfPlayers();
			// send player ID to client
			sockOut.println(nextPlayerID+"");
			sockOut.flush();
			// create Player instance and add to model
			player = new Player(nextPlayerID, name, clientSock, sockOut, sockIn);
			serverModel.addPlayer(player);
		} catch (IOException e) {
			throw e;
		}
	}
	
	@Override
	public void run() {
		// loop to read incoming client messages
		while(true) {
			try {
				// get the next message from socket
				String message = player.readMessage();
				// display the message to view log
				serverView.addLogMessage(message);
				String[] fields = message.split(Protocol.FIELD_SEPARATOR);
				String protocol = fields[1];
				
				// --- QUIT message ---
				if(protocol.equals(Protocol.PROTO_ID_QUIT)) {
					// remove the player from model
					serverModel.removePlayer(player);
					serverView.addLogMessage("player ID=" + player.getId() + " disconnected...");
						
					// stop this thread
					break;
				}
				// --- SEND GAME ----
				else if(protocol.equals(Protocol.PROTO_ID_SENDGAME)) {
					// save the game configuration sent by client to model
					String pattern = fields[2];
					serverView.addLogMessage("game configuration " + pattern + " received from player id = " + player.getId());
					serverModel.addGameConfiguration(pattern);
					serverView.addLogMessage("game configuration " + pattern + " saved.");
				}
				// --- RECEIVE GAME ----
				else if(protocol.equals(Protocol.PROTO_ID_RECEIVEGAME)) {
					// get a random game configuration from model
					String pattern = serverModel.getGameConfiguration();
					String response = Protocol.createSendGameMessage(player.getId(), pattern);
					player.sendMessage(response);
					serverView.addLogMessage("Sent game configuration " + pattern + " to player id=" + player.getId());
				}
				// --- SEND DATA ----
				else if(protocol.equals(Protocol.PROTO_ID_SENDDATA)) {
					// extract the player score data from message
					String[] dataList = fields[2].split(";");
					for(String s : dataList) {
						String[] data = s.split(Protocol.DATA_SEPARATOR);
						int points = Integer.parseInt(data[0]);
						int timeTaken = Integer.parseInt(data[1]);
						player.addScore(new Score(points, timeTaken));
						serverView.addLogMessage("player id=" + player.getId() + " score saved.");
					}										
				}
			} catch (IOException e) {		
				// Socket IO Error, remove the player from model
				serverModel.removePlayer(player);
				serverView.addLogMessage("Communication failed with player ID=" + player.getId() + " ...");
			}
		}
	}
	
}

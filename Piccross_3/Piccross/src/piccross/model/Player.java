package piccross.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Model class to store details of player name, network connection and
 * scores of all games played.
 * @author Mohamed Jouini.
 */
public class Player {
	// ID of the player
	private int id;
	// name of the player
	private String name;
	// client socket connection
	private Socket sock;
	// list of all scores of the player
	private List<Score> scores;
	// output stream to socket
	private PrintWriter sockOut;
	// input stream to socket
	private BufferedReader sockIn;
	
	
	/**
	 * Class constructor that creates a new Player object with given
	 * parameters.
	 * @param id unique id of the player
	 * @param name the name of the player
	 * @param sock network connection of the player
	 * @param sockOut Output stream to sock
	 * @param sockIn Input stream to sock
	 */
	public Player(int id, String name, Socket sock, PrintWriter sockOut, BufferedReader sockIn) {
		this.id = id;
		this.name = name;
		this.sock = sock;
		this.sockOut = sockOut;
		this.sockIn = sockIn;
		this.scores = new ArrayList<Score>();		
	}
	/**
	 * Get the ID of the player
	 * @return the ID of the player
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Add a new score for the player.
	 * @param score the score to add
	 */
	public void addScore(Score score) {
		scores.add(score);
	}
	
	/**
	 * Send a message to the socket connection of the player.
	 * @param message The message to send
	 */
	public void sendMessage(String message) {
		sockOut.println(message);
		sockOut.flush();
	}
	
	/**
	 * Read a message from the socket connection of the player
	 * @return The message read
	 */
	public String readMessage() throws IOException {
		String message = sockIn.readLine();
		return message;
	}
	
	/**
	 * Disconnect the player socket connection. 
	 */
	public void disconnect() {
		if(sock != null) {
			try {
				sock.close();
				if(sockOut != null) {
					sockOut.close();
				}
				if(sockIn != null) {
					sockIn.close();
				}				
			} catch (IOException e) {
			}
		}
	}
	
	@Override
	public int hashCode() {
		return id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		if(obj == null) {
			return false;
		}
		if(!(obj instanceof Player)) {
			return false;
		}
		Player other = (Player)obj;
		return other.id == this.id;
	}
	
	@Override
	public String toString() {
		String s = "";
		for(Score score : scores) {
			s += String.format("Player[%d]: Name=%s, %s\n", id, name, score.toString());
		}
		return s;
	}
}

package piccross.model;

import java.util.List;

/**
 * Utility class related to message protocols exchanged between
 * client and server.
 * 
 * @author Mohamed Jouini
 *
 */
public class Protocol {
	// field separator
	public static final String FIELD_SEPARATOR = "#";
	// data separator
	public static final String DATA_SEPARATOR = ",";
	// protocol id string for Receive Game
	public static final String PROTO_ID_RECEIVEGAME = "RECEIVEGAME";
	// protocol id string for Send Game
	public static final String PROTO_ID_SENDGAME = "SENDGAME";
	// protocol id string for client end
	public static final String PROTO_ID_QUIT = "QUIT";
	// protocol id string for server shutdown
	public static final String PROTO_ID_SHUTDOWN = "SHUTDOWN";
	// protocol id string for Send Data
	public static final String PROTO_ID_SENDDATA = "SENDDATA";
	
	
	public static String createSendGameMessage(int playerId, String gamePattern) {
		String message = playerId + FIELD_SEPARATOR + PROTO_ID_SENDGAME + FIELD_SEPARATOR + gamePattern;
		return message;
	}
	
	public static String createReceiveGameMessage(int playerId) {
		String message = playerId + FIELD_SEPARATOR + PROTO_ID_RECEIVEGAME;
		return message;
	}
	
	public static String createSendDataMessage(int playerId, List<Score> scores) {
		String data = "";
		for(Score score : scores) {
			data += score.getPoints() + DATA_SEPARATOR + score.getTimeTaken() + ";";
		}
		String message = playerId + FIELD_SEPARATOR + PROTO_ID_SENDDATA + FIELD_SEPARATOR + data;
		return message;
	}
	
	public static String createQuitMessage(int playerId) {
		String message = playerId + FIELD_SEPARATOR + PROTO_ID_QUIT;
		return message;
	}
	
}

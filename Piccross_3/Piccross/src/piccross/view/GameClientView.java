package piccross.view;

import java.awt.event.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GameClientView extends JFrame {

	
	// client window UI components.
	private JTextField txtUser;
	private JTextField txtServer;
	private JTextField txtPort;
	private JButton btnEnd;
	private JButton btnConnect;
	private JButton btnNewGame;
	private JButton btnReceiveGame;
	private JButton btnSendData;
	private JButton btnPlay;
	private JButton btnSendGame;
	private JButton btnDrawGame;
	private JTextArea txtLogArea;
	
	/**
	 * Class constructor that initialises the UI components
	 * of the client window.
	 */
	public GameClientView() {
		super("Piccross Client");
		getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
		Container cont = getContentPane();
		cont.setLayout(new BoxLayout(cont, BoxLayout.Y_AXIS));
		
		createLogo();
		createControls();
		createLogArea();
		
		pack();
	}
	/**
	 * Add button action listener to all the UI buttons.
	 * @param listener The ActionListener to register with
	 */
	public void addActionListener(ActionListener listener) {
		btnConnect.addActionListener(listener);
		btnEnd.addActionListener(listener);
		btnSendData.addActionListener(listener);
		btnReceiveGame.addActionListener(listener);
		btnPlay.addActionListener(listener);
		btnNewGame.addActionListener(listener);
		btnSendGame.addActionListener(listener);
		btnDrawGame.addActionListener(listener);
	
	}
	
	/**
	 * Enable/Disbale the connect button.
	 * @param enable boolean flag to indicate whether enable or disable
	 */
	public void setEnableConnect(boolean enable) {
		btnConnect.setEnabled(enable);
	}
	
	/**
	 * Enable/Disbale the end button.
	 * @param enable boolean flag to indicate whether enable or disable
	 */
	public void setEnableEnd(boolean enable) {
		btnEnd.setEnabled(enable);
	}
	
	/**
	 * Enable/Disbale the play button.
	 * @param enable boolean flag to indicate whether enable or disable
	 */
	public void setEnablePlay(boolean enable) {
		btnPlay.setEnabled(enable);
	}
	/**
	 * Enable/Disbale the new game button.
	 * @param enable boolean flag to indicate whether enable or disable
	 */
	public void setEnableNewGame(boolean enable) {
		btnNewGame.setEnabled(enable);
	}
	
	public void setEnableSendGame(boolean enable) {
		btnSendGame.setEnabled(enable);
	}
	/**
	 * Enable/Disbale the receive game button.
	 * @param enable boolean flag to indicate whether enable or disable
	 */
	public void setEnableReceiveGame(boolean enable) {
		btnReceiveGame.setEnabled(enable);
	}
	
	/**
	 * Enable/Disbale the draw game button.
	 * @param enable boolean flag to indicate whether enable or disable
	 */
	public void setEnableDrawGame(boolean enable) {
		btnDrawGame.setEnabled(enable);
	}
	/**
	 * Enable/Disbale the send data button.
	 * @param enable boolean flag to indicate whether enable or disable
	 */
	public void setEnableSendData(boolean enable) {
		btnSendData.setEnabled(enable);
	}
	
	/**
	 * Show an information popup dialog.
	 * @param message The message to show
	 */
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message, "Message", JOptionPane.INFORMATION_MESSAGE);
	}
	
	/**
	 * Show an error popup dialog.
	 * @param error The error message to show
	 */
	public void showError(String error) {
		JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Get the user name from the view text fields.
	 * @return The user name
	 */
	public String getUser() {
		String username = txtUser.getText();
		return username;
	}
	
	/**
	 * Get the server hostname from the view text fields.
	 * @return The server hostname or IP
	 */
	public String getServer() {
		String hostname = txtServer.getText();
		return hostname;
	}
	
	/**
	 * Get the server port number from the view text fields.
	 * @return The port number
	 */
	public int getPort() {
		int port = -1;
		try {
			port = Integer.parseInt(txtPort.getText());
		}catch(NumberFormatException e) {
		}
		return port;
	}
	
	/**
	 * Add a new message to the log area.
	 * @param message The message to add
	 */
	public void addLogMessage(String message) {
		txtLogArea.append(message + "\n");
	}
	
	/**
	 * Create the logo header at the top of the server window
	 */
	private void createLogo() {
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		Image logoImage = null;
		try {
			logoImage = ImageIO.read(getClass().getResource("/piccross/images/piccrossclient.png"));
		} catch (IOException e) {
			System.out.println("ERROR: " + e.getMessage());
		}
		JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
		p.add(logoLabel, BorderLayout.CENTER);
		
		getContentPane().add(p);
	}
	
	/**
	 * Create the control buttons of the server
	 */
	private void createControls() {
		JPanel p = new JPanel();
		p.setLayout(new FlowLayout());
		
		p.add(new JLabel("User: "));
		txtUser = new JTextField(7);
		p.add(txtUser);
		
		p.add(new JLabel("Server: "));
		txtServer = new JTextField(10);
		txtServer.setText("localhost");
		p.add(txtServer);
		
		txtPort = new JTextField(6);
		txtPort.setText("12345");
		p.add(txtPort);
		
		btnConnect = new JButton("Connect");
		p.add(btnConnect);
			
		btnEnd = new JButton("End");
		btnEnd.setEnabled(false);
		p.add(btnEnd);
		getContentPane().add(p);				
		
		p = new JPanel();
		
		btnNewGame = new JButton("New Game");
		btnNewGame.setEnabled(false);
		p.add(btnNewGame);
		btnDrawGame = new JButton("Draw Game");
		btnDrawGame.setEnabled(false);
		p.add(btnDrawGame);
		btnSendGame = new JButton("Send Game");
		btnSendGame.setEnabled(false);
		p.add(btnSendGame);
		btnReceiveGame = new JButton("Receive Game");
		btnReceiveGame.setEnabled(false);
		p.add(btnReceiveGame);
		btnSendData = new JButton("Send Data");
		btnSendData.setEnabled(false);
		p.add(btnSendData);
		btnPlay = new JButton("Play");
		btnPlay.setEnabled(false);
		p.add(btnPlay);
		getContentPane().add(p);
		
		getContentPane().add(p);
	}
	
	/**
	 * Create the log message area box
	 */
	private void createLogArea() {
		txtLogArea = new JTextArea(7, 30);
		JScrollPane sp = new JScrollPane(txtLogArea);
		getContentPane().add(sp);
	}
	
	
}

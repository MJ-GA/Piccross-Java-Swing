package piccross.view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class GameServerView extends JFrame {
	// help message
		
	// UI components
	private JTextField txtPort;
	private JButton btnExecute;
	private JButton btnResults;
	private JButton btnEnd;
	private JCheckBox chbxFinalize;
	private JTextArea txtLogArea;
	
	/**
	 * Class constructor that initialises the UI components
	 * of the server window.
	 */
	public GameServerView() {
		super("Piccross Server");
		getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
		Container cont = getContentPane();
		cont.setLayout(new BoxLayout(cont, BoxLayout.Y_AXIS));
		
		createLogo();
		createControls();
		createLogArea();
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	
	/**
	 * Add a listener for all UI components interaction events.
	 * @param listener The ActionListener to register with all action events
	 */
	public void addActionListener(ActionListener listener) {
		btnExecute.addActionListener(listener);
		btnEnd.addActionListener(listener);
		btnResults.addActionListener(listener);
		chbxFinalize.addActionListener(listener);
	}
	
	/**
	 * Get the server port number from the view text field.
	 * @return The port number
	 */
	public int getServerPort() {
		int port = 0;
		try {
			port = Integer.parseInt(txtPort.getText());
		} catch(NumberFormatException e) {
			showError("Please enter a valid port number!");
		}
		return port;
	}
	
	/**
	 * Show a popup diaog with information message.
	 * @param message The message to display
	 */
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
	
	/**
	 * Show a popup diaog with information message.
	 * @param message The message to display
	 */
	public void showError(String error) {
		JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
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
			logoImage = ImageIO.read(getClass().getResource("/piccross/images/piccrossserver.png"));
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
		
		p.add(new JLabel("Port: "));
		txtPort = new JTextField(7);
		txtPort.setText("12345");
		p.add(txtPort);
		btnExecute = new JButton("Execute");
		p.add(btnExecute);
		btnResults = new JButton("Results");
		p.add(btnResults);
		chbxFinalize = new JCheckBox("Finalize");
		p.add(chbxFinalize);
		btnEnd = new JButton("End");
		p.add(btnEnd);
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

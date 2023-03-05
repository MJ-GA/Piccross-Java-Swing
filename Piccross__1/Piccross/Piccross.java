

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.*;
/**
 * This is the PICCROSS main class from which I will instantiate the GameController object .
 * @author Mohamed Jouini 040994664
 *
 */
public class Piccross {
/**
 * In this main method I will create a splash screen for 3 seconds with a picture that shows my name on it 
 * then close it and open the main frame by calling the GameController constructor
 * @param args
 */
public static void main (String[] args) {
	
	//Jframe to be used for the splash screen 
	JFrame frame0 = new JFrame ();
	try {
		//Creating a label to display the piccross logo Image with identification 
		JLabel splash = new JLabel();
		ImageIcon img = new ImageIcon("p1.png");
		// a JPanel to contain the label 
		JPanel p = new JPanel();
		//Setting the panel to be able to show the ImageIcon
		splash.setIcon(img);
		splash.setFont(new Font("Serif", Font.BOLD,15));
		
		p.setLayout(new BorderLayout(5,5));
		p.add(splash,BorderLayout.CENTER);
		p.setBackground(new Color(255,160,122) );
		
	    frame0.add(p);
		frame0.setVisible(true);
		frame0.setTitle("PICCROSS");
		frame0.setSize(1080,720);
		frame0.setResizable(false);
		//Waiting for 3 seconds before minimizing the splash window
		Thread.sleep(3000);
		//Minimize the splash window frame and make it invisible  
		frame0.setSize(0,0);
		frame0.setVisible(false);
		
	}catch (Exception e) {}
	
	// The main frame starts here 
	GameController frame = new GameController ();
}

}
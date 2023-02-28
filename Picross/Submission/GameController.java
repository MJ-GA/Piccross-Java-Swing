

import javax.swing.BorderFactory;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.*;

import java.awt.Image;
import java.awt.event.*;
/**
 * GameController Class , in which the GUI features will be implemented including the inner Controller class 
 * @author Mohamed Jouini 040994664
 *
 */
public class GameController extends JFrame {
	//Static variables in order to be able to access it from all scopes 
	static JTextArea textarea; // right panel textarea
	static boolean checked =false; //boolean variable for the mark checkbox
	
	GameController () {
	
		JCheckBox mark = new JCheckBox  (); // top left panel checkbox
		JLabel markLabel = new JLabel("Mark"); //label for the checkbox
		
		JTextField scoreField = new JTextField (); // textfield for scores
		JLabel scoreLabel = new JLabel("Score: "); //the label for score textfield
		
		
		JTextField timeField = new JTextField (); //the textfield for time 
		JLabel timeLabel = new JLabel("Time: "); // the label for the time textfield
		
		JButton resetBtn = new JButton ("Reset"); // Reset button
	
		JLabel label = new JLabel(); //
		
		JLabel logo = new JLabel(); //Label for the logo
		ImageIcon image = new ImageIcon ("Picross.png"); // logo image
		logo.setIcon(image);
		
		JButton btn ; //  a button to be used for the main boad gridlayout
		
		//Right panel textArea settings
		textarea = new JTextArea ();
		textarea.setEditable(false);
		textarea.setPreferredSize(new Dimension());
		textarea.setLineWrap(true);
		textarea.setWrapStyleWord(true);
		
		//Right panel image at the top
		
		this.setIconImage(image.getImage());
		//Main frame settings 
		this.add(label);
		this.setTitle("PICCROSS");
		this.setSize(1080,720);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		
		//Instantiating the different panels in order to use it in the border layout later
		JPanel panel0 = new JPanel();
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		JPanel panel4 = new JPanel();
		JPanel panel5 = new JPanel();
		
		//Setting the background color for every panel
		panel0.setBackground(new Color(255,160,122));
		panel1.setBackground(new Color(251,206,177));
		panel2.setBackground(new Color(251,206,177));
		panel3.setBackground(new Color(251,206,177));
		panel4.setBackground(Color.magenta);
		panel5.setBackground(Color.LIGHT_GRAY);
		
		//Setting the main frame layout as a border layout
		this.setLayout(new BorderLayout(5,5));
				
		//Panel sizes 
		panel0.setPreferredSize(new Dimension(100,50));
		panel1.setPreferredSize(new Dimension(100,100));
		panel2.setPreferredSize(new Dimension(100,100));
		panel3.setPreferredSize(new Dimension(155,100));
		panel4.setPreferredSize(new Dimension(100,100));
		panel5.setPreferredSize(new Dimension(100,100));
		
		//Joining the top panel and Top left panel together using a JSplitPane
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                panel0, panel1);
		//Using the split panel instead for NORTH position
		this.add(splitPane,BorderLayout.NORTH);
		//Setting panels positions accordingly
		this.add(panel2,BorderLayout.WEST);
		this.add(panel3,BorderLayout.EAST);
		this.add(panel5,BorderLayout.CENTER);
		
		
		
		
		// Adding the mark checkbox and label to the top left panel
		panel0.add(markLabel);
		panel0.add(mark);
		mark.addActionListener(new Controller());
		mark.setActionCommand("Mark");
		
		//Settings for the fields and components
		logo.setIcon(image);
		logo.setVerticalAlignment(JLabel.TOP);
		logo.setHorizontalAlignment(JLabel.RIGHT);
		panel3.add(logo);
		
		scoreLabel.setFont(new Font("Serif", Font.BOLD,15));
		scoreField.setPreferredSize(new Dimension(75,25));
		scoreField.setEditable(false);
		panel3.add(scoreLabel);
		panel3.add(scoreField);
		
		textarea.setPreferredSize(new Dimension(150,300));
		panel3.add(textarea);
		
		timeLabel.setFont(new Font("Serif", Font.BOLD,15));
		timeField.setPreferredSize(new Dimension(75,25));
		timeField.setEditable(false);
		panel3.add(timeLabel);
		panel3.add(timeField);
	
		panel3.add(resetBtn);
		resetBtn.addActionListener(new Controller());
		    
		resetBtn.setActionCommand("Reset");
		
		
		//creating a gridlayout for the main board by creating multiple buttons with an action listener
		panel5.setLayout(new GridLayout(5,5,7,7));
		for (int i =0  ; i<25 ; i++) {
			btn = new JButton(String.valueOf(i));
			btn.setBorder(BorderFactory.createEtchedBorder());
		    btn.setBackground(new java.awt.Color(204, 255, 204));
		    btn.setForeground(new java.awt.Color(204, 255, 204));
		    panel5.add(btn);
		    //Add an action listener via the Controller class to all the buttons that has been added to the panel
		    btn.addActionListener(new Controller());
		    // define a custom short action command for the button
		    btn.setActionCommand("Square");
		}
	
		// left and top panel labels
		JLabel numbersLeft ; 
		JLabel numbersTop ;
	
		
		// Setting a grid layout at the left panel to show numbers
		panel2.setLayout(new GridLayout(5,1,1,1));
		for (int i =0  ; i<5 ; i++) {
		numbersLeft = new JLabel("(1,1)");
		numbersLeft.setFont(new Font("Serif", Font.BOLD, 25));
		panel2.add(numbersLeft);
		}
		// Setting a grid layout at the top panel to show numbers
		panel1.setLayout(new GridLayout(1,5,1,1));
		for (int i =0  ; i<5 ; i++) {
			numbersTop = new JLabel("(1)");
			numbersTop.setFont(new Font("Serif", Font.BOLD, 20));
			panel1.add(numbersTop);
			}
	}
	
	/**
	 * 
	 * @author Mohamed Jouini 040994664
	 * this is the controller class which will implement the listeners 
	 */
	 class Controller implements ActionListener {
		 /**
		  * Here i will implement the Action listener ,  keep track of the click events print the output to the right panel textarea
		  * each time one of the events takes place
		  */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		 String action = e.getActionCommand();
	        if (action.equals("Square")) {
	        	textarea.append("Square clicked \n");
	            System.out.println("Square is pressed!");
	        } else  if (action.equals("Reset")) {
	            System.out.println("Reset is pressed!");
	            textarea.setText("");
	        } else  if (action.equals("Mark")) {
	            System.out.println("Mark is pressed!");
	            //Check whether the box is checked or not in order to print the correct output
	            if(checked) {
	            textarea.append("Mark unchecked \n"); 
	            checked=false;}
	            else {
	            checked=true;
	            textarea.append("Mark checked \n");}
	            ;}
	}
}			
}
	

package teamProject;

import javax.swing.JFrame;
import javax.swing.JPanel;
import edu.princeton.cs.algs4.Stack;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.CardLayout;

/**
 * This class is what creates our GUI.
 * It extends JFrame and instantiates 4 additional classes.
 * It then adds these objects to the bgPanel JPanel.
 * It requires the objects created in Clinic, which it 
 * takes in as a stack of objects.
 * 
 * @author Aaron Carlisle
 * @author Hayden Pack
 *
 */
@SuppressWarnings("serial")
public class AppWindow extends JFrame{

	/**
	 * Global variables for our application including 
	 * colors, fonts, width, height, etc.
	 */
	private static String programTitle = "Triage Simulator"; // The title of our program.
	private int windowWidth = 1151; // The horizontal dimensions of our program.
	private int windowHeight = 648; // The vertical dimensions of our program
	String font = "Century Gothic";	// Universal font for our program.
	String header = "Admit Patient"; // The title of currently selected menu page. Default is admit.
	String authors = "Aaron Carlisle // Hayden Pack"; // Author names.
	
	// Colors that are used in our program for the overall theme.
	private static final Color white = new Color(246, 244, 238);
	private static final Color teal = new Color(83, 166, 166);

	
	/*
	 * These variables are used with CardLayout API to allow 
	 * the the selected menu item to "flip" to the corresponding
	 * card JPanel.
	 */
	final static String ADMITPANEL = "Card for admit panel";
	final static String QUEUEPANEL = "Card for queue panel";
	final static String ABOUTPANEL = "Card for about panel";	
	
	/**
	 * Creates the application window and makes it visible.
	 * @param clinicList the Stack of all our objects created in the Clinic class.
	 */
	public AppWindow(Stack<Object> clinicList) 
	{	
		/**
		 * Set default parameters and behaviors.
		 */
		super(programTitle); // Sets the window title.
		setSize(windowWidth, windowHeight); // Sets default window size.
		setResizable(false); // Prevents user from changing window size.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Sets default close behavior.
		getContentPane().setLayout(null);  // Sets layout to Absolute.
		
		/**
		 * Creates the main JPanel that's used
		 * to contain all the other panels in
		 * our program.
		 */
		JPanel bgPanel = bg(); // The main JPanel.
		getContentPane().add(bgPanel); // Adds the bgPanel to the frame.
		
		/*
		 * Builds the header panel which displays the title
		 * of the currently selected menu page. By default,
		 * it is set to Admit Patient at runtime. This is not
		 * used to update the header.
		 */
		JPanel headerPanel = new JPanel();
		headerPanel.setBounds(318, 78, 818, 133);
		headerPanel.setBackground(teal);
		bgPanel.add(headerPanel);
		headerPanel.setLayout(null);
			
		JLabel headerLabel = new JLabel(header);
		headerLabel.setForeground(SystemColor.text);
		headerLabel.setFont(new Font(font, Font.PLAIN, 28));
		headerLabel.setBounds(29, 11, 439, 111);
		headerPanel.add(headerLabel);	

		
		// Create author titles that are displayed at the top of the program.
		buildAuthors(bgPanel);
		
		/**
		 * Creates the three main content panels which are 
		 * selectable from the menu. AdmitPanel and QueuePanel
		 * both require the clinicList Stack as a parameter.
		 */
		AdmitPanel admitPanel = new AdmitPanel(clinicList);
		QueuePanel queuePanel = new QueuePanel(clinicList);	
		AboutPanel aboutPanel = new AboutPanel();		
		
		
		/**
		 *  Creates an additional content panel thats sole function is
		 *  to house the three main content panels. It is set using a 
		 *  CardLayout. The CardLayout features  stack like behavior 
		 *  to display the panel that's at the top. The top panel 
		 *  can be manually changed. This is how we display what the
		 *  user has selected from our menu.
		 */
		JPanel contentPanel = new JPanel(new CardLayout());
		contentPanel.setBounds(318, 211, 818, 398);		
		contentPanel.add(admitPanel, ADMITPANEL);
		contentPanel.add(queuePanel, QUEUEPANEL);
		contentPanel.add(aboutPanel, ABOUTPANEL);
		bgPanel.add(contentPanel);
		CardLayout cl = (CardLayout)(contentPanel.getLayout());
		
		
		
		/*
		 * Instantiates the MenuPanel class and adds it to the 
		 * main bgPanel. This class represents the menu column
		 * in our program. It requires both the contentPanel and
		 * CardLayout as parameters. It changes the headerLabel
		 * based on the selected page. Finally, it uses the queuePanel
		 * to setup a dynamic refresh. This refreshes the provider
		 * queues each time the Queue menu button is selected.
		 */
		MenuPanel menuPanel = new MenuPanel(contentPanel, cl,
				headerLabel, queuePanel);
		bgPanel.add(menuPanel);			
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() 
	{
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates the main background panel that 
	 * contains all the additional panels for our program.
	 * @return bgPanel the JPanel that contains everything
	 */
	private JPanel bg()
	{
		JPanel bgPanel = new JPanel();
		bgPanel.setBounds(0, 0, windowWidth - 16, windowHeight - 39);
		bgPanel.setBackground(white);
		bgPanel.setLayout(null);
		return bgPanel;
	}
	
	/**
	 * Creates the label that displays the program's creators.
	 * @param bgPanel the main bg panel that contains all additional panels.
	 */
	private void buildAuthors(JPanel bgPanel) 
	{
		JLabel authorLabel = new JLabel(authors);
		authorLabel.setForeground(SystemColor.textText);
		authorLabel.setFont(new Font(font, Font.PLAIN, 18));
		authorLabel.setBounds(348, 0, 310, 78);
		bgPanel.add(authorLabel);
		
	}

}

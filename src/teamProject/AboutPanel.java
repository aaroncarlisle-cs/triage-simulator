package teamProject;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JTextPane;

/**
 * The class AboutPanel extends a JPanel and is used
 * in combination with a CardLayout to represent one
 * of the menu pages that the user can choose. It's primary
 * purpose is to display a reflection that we've written for
 * this assignment.
 * 
 * @author Aaron Carlisle
 * @author Hayden Pack
 *
 */
@SuppressWarnings("serial")
public class AboutPanel extends JPanel 
{
	private String font = "Century Gothic"; // Global font
	private static final Color white = new Color(246, 244, 238); // Color white from theme
	JTextPane textPane;
	StringBuilder aboutInfo;
	
	
	AboutPanel()
	{
		// Default window behavior
		setBounds(317, 211, 818, 398); // Sets panel size and location to be below header.
		setLayout(null); // Sets layout to absolute.
		setBackground(white); // Sets panel background.
		
		// Set the text for our About page
		aboutInfo = getAboutInfo();
		
		// Default behavior for the text pane
		textPane = new JTextPane();
		textPane.setFont(new Font(font, Font.PLAIN, 11));
		textPane.setEditable(false);
		textPane.setBounds(35, 30, 754, 328);
		textPane.setText(aboutInfo.toString());
		add(textPane);
				
	}

	// Our reflection
	private StringBuilder getAboutInfo() 
	{
		StringBuilder aboutInfo = new StringBuilder();

		// First paragraph
		aboutInfo.append("     This project’s difficulty lies in its learning curve. We’ve "
				+ "implemented many of the concepts taught in class and combined them "
				+ "all into a realistic final product. Reading about these skills versus "
				+ "implementing them are two separate tasks. That’s why our final goal "
				+ "required a lot of analysis and refactoring to reach. Maintaining good "
				+ "coding habits was also paramount to this program’s success. Making Javadoc "
				+ "comments and keeping all classes cohesive was difficult at first. "
				+ "However, this allowed much smoother development later on, when our "
				+ "program started getting more complex.\n\n");
		
		// Second paragraph
		aboutInfo.append("     There are many things other than coding which contributed to "
				+ "Triage Simulator’s success. Good teamwork, communication, initial "
				+ "designs, and workload splitting all helped development greatly. That "
				+ "is the purpose of this program. It helped both teammates refine critical "
				+ "coding and non-coding skills that will help them in the workforce.\n\n");
		
		// Lessons learned
		aboutInfo.append("Lessons learned:\n");
		aboutInfo.append("-Implementing priority queues in a realistic scenario\n");
		aboutInfo.append("-Managing directed graphs in a real-life use case\n");
		aboutInfo.append("-Unstanding time and space complexity to write efficient code\n");
		aboutInfo.append("-Create high-cohesion classes that work well together\n");
		aboutInfo.append("-Commenting and sorting code in a human-readable way\n");
		aboutInfo.append("-Debugging using the console\n");
		aboutInfo.append("-Creating an understandable GUI for our application");
		
		return aboutInfo;
	}

}

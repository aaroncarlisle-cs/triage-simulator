package teamProject;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel
{
	// Global colors
	private static final Color darkBlue = new Color(0, 58, 91);
	private static final Color lightBlue = new Color(38, 117, 166);
	private static final Color white = new Color(246, 244, 238);

	
	/*
	 * These variables are used with CardLayout API to allow 
	 * the the selected menu item to "flip" to the corresponding
	 * card JPanel.
	 */
	final static String ADMITPANEL = "Card for admit panel";
	final static String QUEUEPANEL = "Card for queue panel";
	final static String ABOUTPANEL = "Card for about panel";
	
	// Global font
	String font = "Century Gothic";
	
	public MenuPanel(JPanel contentPanel, CardLayout cl, 
			JLabel headerLabel, QueuePanel queuePanel) 
	{
		// Default behaviors
		setBackground(darkBlue);
		setBounds(0, 0, 318, 609);
		setLayout(null);
		
		/*
		 * Creates the header of the menu. It displays the program's
		 * name as a JLabel and is followed by a JSeparator.
		 */
		JLabel labelTitle = new JLabel("Triage Simulator");
		labelTitle.setForeground(SystemColor.text);
		labelTitle.setFont(new Font("Century Gothic", Font.PLAIN, 28));
		labelTitle.setBounds(51, 39, 257, 84);
		add(labelTitle);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(lightBlue);
		separator.setBounds(10, 142, 298, 5);
		add(separator);
		
		// Create the Admit Patient button
		JPanel admitButton = new JPanel();
		admitButton.setBackground(lightBlue);
		admitButton.setBounds(0, 180, 318, 62);
		add(admitButton);
		admitButton.setLayout(null);
		
		JLabel admitLabel = new JLabel("Add Patient");
		admitLabel.setForeground(white);
		admitLabel.setFont(new Font(font, Font.PLAIN, 20));
		admitLabel.setBounds(51, 0, 264, 62);
		admitButton.add(admitLabel);
		
		// Create the Priority Queue Button
		JPanel queueButton = new JPanel();
		queueButton.setBackground(darkBlue);
		queueButton.setBounds(0, 242, 318, 62);
		add(queueButton);
		queueButton.setLayout(null);
		
		JLabel queueLabel = new JLabel("Priority Queue");
		queueLabel.setForeground(white);
		queueLabel.setFont(new Font(font, Font.PLAIN, 20));
		queueLabel.setBounds(51, 0, 264, 62);
		queueButton.add(queueLabel);
		
		// Create the about button
		JPanel aboutButton = new JPanel();
		aboutButton.setBackground(darkBlue);
		aboutButton.setBounds(0, 303, 318, 62);
		add(aboutButton);
		aboutButton.setLayout(null);
		
		JLabel aboutLabel = new JLabel("About");
		aboutLabel.setForeground(white);
		aboutLabel.setFont(new Font(font, Font.PLAIN, 20));
		aboutLabel.setBounds(51, 0, 264, 62);
		aboutButton.add(aboutLabel);
		
		/*
		 * Each event listener changes the background of
		 * the button to indicate it is currently selected.
		 * It also changes the non-selected buttons to 
		 * dark blue. The event listeners also set the headerLabel
		 * to match the menu selection and use the CardLayout
		 * to display the chosen panel.
		 */
		admitButton.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				admitButton.setBackground(lightBlue);
				queueButton.setBackground(darkBlue);
				aboutButton.setBackground(darkBlue);	
				cl.show(contentPanel, ADMITPANEL);
				headerLabel.setText("Admit Patient");
			}
		});
		
		/*
		 * Each time the queue button is selected, the 
		 * corresponding queues that are printed must
		 * be refreshed with queuePanel.refreshQueue()
		 */
		queueButton.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				admitButton.setBackground(darkBlue);
				queueButton.setBackground(lightBlue);
				aboutButton.setBackground(darkBlue);
				cl.show(contentPanel, QUEUEPANEL);
				headerLabel.setText("Priority Queue");
				queuePanel.refreshQueue();
			}
		});
		
		aboutButton.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				admitButton.setBackground(darkBlue);
				queueButton.setBackground(darkBlue);
				aboutButton.setBackground(lightBlue);
				cl.show(contentPanel, ABOUTPANEL);
				headerLabel.setText("About");
			}
		});		
	}

}

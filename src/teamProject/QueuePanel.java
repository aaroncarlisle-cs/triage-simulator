package teamProject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.Stack;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextPane;

/**
 * The class queuePanel extends a JPanel and is used
 * in combination with a CardLayout to represent one
 * of the menu pages that the user can choose. It's primary
 * purpose is for the view the queue of patients admitted to the 
 * hospital.
 * 
 * @author Aaron Carlisle
 * @author Hayden Pack
 *
 */
@SuppressWarnings("serial")
public class QueuePanel extends JPanel
{
	private String font = "Century Gothic";
	private static final Color white = new Color(246, 244, 238);
	private JComboBox<Provider> providerBox;
	private JTextPane textPane;
	
	QueuePanel(Stack<Object> clinicList)
	{
		// Default window behavior
		setBounds(317, 211, 818, 398);
		setBackground(white); // Sets panel background.
		
		// Creates a JLabel that prompts the user to select the Provider.
		String namePrompt = "Provider:";
		setLayout(null);
		JLabel nameLabel = new JLabel(namePrompt);
		nameLabel.setBounds(35, 45, 140, 68);
		nameLabel.setForeground(SystemColor.textText);
		nameLabel.setFont(new Font(font, Font.PLAIN, 18));
		add(nameLabel);
		
		/**
		 * 
		 * The following  class is a custom made renderer to be used
		 * with the JComboBox. It allows the displaying of getName() for
		 * the listed providers instead of the default toString(). 
		 * Additionally,it allows the default index to be set to -1 in 
		 * which case a placeholder is shown in the ComboBox 
		 * that disappears when clicked on.
		 *
		 *@param prompt The placeholder message for the combo box
		 */
		class ProviderRenderer extends BasicComboBoxRenderer {
			private String prompt;
			
			public ProviderRenderer(String prompt)
			{
				this.prompt = prompt;
			}
		
			  @SuppressWarnings("rawtypes")
			public Component getListCellRendererComponent(JList list, Object value,
			      int index, boolean isSelected, boolean cellHasFocus) {
			    super.getListCellRendererComponent(list, value, index, isSelected,
			        cellHasFocus);

			    Provider pro = (Provider) value;

			    if (index == -1 && value == null) {
			      setText(prompt);
			    } else {
			      setText(pro.getName());
			    }
			    return this;
			  }
		}
		
		/*
		 * This JComboBox represents a list of providers. The providers
		 * are obtained by looping through clinicList and adding objects
		 * that match the Provider class.
		 * 
		 * This ComboBox also uses a custom renderer to list each provider
		 * without overriding their toString methods. The renderer also
		 * allows the default selected index of -1 to be a placeholder
		 * which disappears when clicked on.
		 */
		
		providerBox = new JComboBox<Provider>();
		providerBox.setBounds(178, 69, 178, 28);
		providerBox.setBackground(SystemColor.text);
		for (Object w : clinicList)
		{
			if(w.getClass().equals(Provider.class)) providerBox.addItem((Provider) w);
		}
		providerBox.setRenderer(new ProviderRenderer("<Select Provider>"));
		providerBox.setSelectedIndex(-1);
		add(providerBox);
		
		textPane = new JTextPane();
		textPane.setFont(new Font(font, Font.PLAIN, 11));
		textPane.setEditable(false);
		textPane.setBounds(35, 124, 754, 234);
		textPane.setText("Patient\t\tInjury\tInsurance\t\tBill\tProcessing\n\n");
		add(textPane);
		
		
		providerBox.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				refreshQueue();
			}
		});
				
	}

	/*
	 * This method is used to update and display the queue.
	 * It is called each time the queuePanel is placed into view 
	 * with a menu selection. This is what allows user admitted patients 
	 * to be viewed in real time when the user switches between 
	 * the different menu panels.
	 */
	public void refreshQueue()
	{
		int index = providerBox.getSelectedIndex();
		if (index != -1)
		{	
			StringBuilder sb = new StringBuilder();
			sb.append("Patient\t\tInjury\tInsurance\t\tBill\tProcessing\n\n");
			MaxPQ<Patient> waitList = ((Provider) providerBox.getSelectedItem()).getWaitingList();
			
			for(Patient p : waitList)
			{	
				String name = p.getName();
				Provider prov = (Provider) providerBox.getSelectedItem();
				if (name.length() <= 12) sb.append(name + "\t\t");
				else sb.append(name + "\t");
				
				sb.append(p.getInjury().getType() + "\t");
				
				String insurance;
				if (p.hasInsurance()) 
				{
					insurance = p.getInsurance().getName();
					if (insurance.length() <= 12) sb.append(insurance + "\t\t");
					else sb.append(insurance + "\t");
				}
				else sb.append("None \t\t");
				
				String bill = String.format("%.2f", prov.getBill(p));
				sb.append("$ " + bill + "\t");
				
				if (p.hasInsurance()) 
				{
					int delay = prov.getDaysToProcessInsurance(p.getInsurance());
					sb.append(delay);
					if (delay > 1) sb.append(" days\n");
					else sb.append(" day\n");
				}
				else sb.append("Immediate\n");
		}
		textPane.setText(sb.toString());
		}
	}
}

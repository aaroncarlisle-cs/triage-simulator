package teamProject;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.Stack;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

@SuppressWarnings("serial")
/**
 * The class AdmitPanel extends a JPanel and is used
 * in combination with a CardLayout to represent one
 * of the menu pages that the user can choose. It's primary
 * purpose is for the user to admit patients to the hospital.
 * The patients are passed into associated provider's priority
 * queue.
 * 
 * @author Aaron Carlisle
 * @author Hayden Pack
 *
 */

public class AdmitPanel extends JPanel
{
	/*
	 * Global variables that are used for the 
	 * panels theme.
	 */
	private String font = "Century Gothic"; // Global font
	private static final Color white = new Color(246, 244, 238); // Color white from theme
	
	/*
	 * Creates a new panel that represents the Admit Patient
	 * page on the menu. Uses the clinicList Stack to populate
	 * input fields, run comparisons, and query graph connections.
	 */
	public AdmitPanel(Stack<Object> clinicList)
	{

		// Default window behavior
		setBounds(317, 211, 818, 398); // Sets panel size and location to be below header.
		setLayout(null); // Sets layout to absolute.
		setBackground(white); // Sets panel background.
		
		// Creates a JLabel that prompts the user to enter the patient's name.
		String namePrompt = "Patient Name:";
		JLabel nameLabel = new JLabel(namePrompt);
		nameLabel.setForeground(SystemColor.textText);
		nameLabel.setFont(new Font(font, Font.PLAIN, 18));
		nameLabel.setBounds(35, 45, 140, 68);
		add(nameLabel);
		

		// Creates a text field where the user can input the patient's name.
		JTextField nameField = new JTextField();
		nameField.setBackground(SystemColor.text);
		nameField.setBounds(178, 69, 178, 28);
		add(nameField);
		nameField.setColumns(10);
		

		// Prompts the user for the injury type.
		String injuryPrompt = "Injury Type:";
		JLabel injuryLabel = new JLabel(injuryPrompt);
		injuryLabel.setForeground(SystemColor.textText);
		injuryLabel.setFont(new Font(font, Font.PLAIN, 18));
		injuryLabel.setBounds(385, 45, 140, 68);
		add(injuryLabel);
	
		
		/*
		 * This JComboBox represents a list of injuries. The injuries
		 * are obtained by looping through clinicList and adding objects
		 * that match the Injury class.
		 * 
		 * This ComboBox also uses a custom renderer to list each injury
		 * without overriding their toString methods. The renderer also
		 * allows the default selected index of -1 to be a placeholder
		 * which disappears when clicked on.
		 */
		JComboBox<Injury> injuryBox = new JComboBox<Injury>();
		for (Object w : clinicList)
		{
			if(w.getClass().equals(Injury.class)) injuryBox.addItem((Injury) w);
		}
		injuryBox.setBackground(SystemColor.text);
		injuryBox.setBounds(500, 68, 178, 28);
		injuryBox.setRenderer(new InjuryRenderer("<Select Injury>"));
		injuryBox.setSelectedIndex(-1);
		add(injuryBox);
		
		// Prompts the user to input the patient's insurance
		String insurancePrompt = "Insurance:";
		JLabel insuranceLabel = new JLabel(insurancePrompt);
		insuranceLabel.setForeground(Color.BLACK);
		insuranceLabel.setFont(new Font(font, Font.PLAIN, 18));
		insuranceLabel.setBounds(35, 110, 140, 68);
		add(insuranceLabel);
		
		
		// Same behavior as above using Insurance objects
		JComboBox<Insurance> insuranceBox = new JComboBox<Insurance>();
		for (Object w : clinicList)
		{
			if(w.getClass().equals(Insurance.class)) insuranceBox.addItem((Insurance) w);
		}
		insuranceBox.setBackground(SystemColor.text);
		insuranceBox.setBounds(178, 130, 178, 28);
		insuranceBox.setRenderer(new InsuranceRenderer("<Select Insurance>"));
		insuranceBox.setSelectedIndex(-1);
		add(insuranceBox);
		
		// Prompts the user to select the Provider.
		String providerPrompt = "Provider:";
		JLabel providerLabel = new JLabel(providerPrompt);
		providerLabel.setForeground(Color.BLACK);
		providerLabel.setFont(new Font(font, Font.PLAIN, 18));
		providerLabel.setBounds(385, 110, 140, 68);
		add(providerLabel);
		
		/*
		 * The provider works differently. It is unselectable until the 
		 * user has chosen an injury type. After the injury is chosen,
		 * the eventListener wipes this box of all previous options.
		 * It then repopulates it with only the providers 
		 * that treat the selected injury type. 
		 */
		JComboBox<Provider> providerBox = new JComboBox<Provider>();
		providerBox.setBackground(SystemColor.text);
		providerBox.setBounds(500, 130, 178, 28);
		providerBox.setRenderer(new ProviderRenderer("<Select Provider>"));
		providerBox.setSelectedIndex(-1);
		providerBox.setEnabled(false);
		add(providerBox);		
		
		/*
		 * This is the submit button for creating a patient. 
		 * If invalid parameters are selected, the actionListener
		 * generates errors which are printed in the GUI for the user
		 * to fix.
		 */
		JButton addPatientButton = new JButton("Submit");
		addPatientButton.setBounds(364, 328, 89, 23);
		add(addPatientButton);
		
		// This is a label that provides user feedback about submitting patient information.
		JLabel errorLabel = new JLabel("");
		errorLabel.setFont(new Font(font, Font.PLAIN, 11));
		errorLabel.setBounds(35, 97, 546, 254);
		add(errorLabel);
		
		
		/**
		 * Action Listeners
		 */
		
		/*
		 * Once the user has chosen an injury, the provider box
		 * is dynamically adjusted to contain only the providers
		 * that will treat the chosen injury.
		 */
		injuryBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) 
			{	
				providerBox.removeAllItems();
				if (injuryBox.getSelectedIndex() != -1)
				{
					for (Object w : clinicList)
					{
						if (w.getClass().equals(Provider.class))		
						{
							if (((Provider) w).canTreat((Injury) injuryBox.getSelectedItem()))
							{
								providerBox.addItem((Provider) w);
							}
						}
					}
				}
				providerBox.setEnabled(true);	
			}
		});
		
		/*
		 * When the user clicks the Submit button, the validatePatient 
		 * method is called. It uses the user input selections to
		 * determine if a patient will be created or not.
		 */
		addPatientButton.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				errorLabel.setText
				(validatePatient(nameField, injuryBox, insuranceBox, providerBox).toString());
			}
		});
			
	
	}
	
	/**
	 * The validate patient class takes in the user selected
	 * parameters and checks them for validity. A StringBuilder
	 * is used to dynamically generate user feedback. If the 
	 * selections are valid, a patient is created and enqueued
	 * for the chosen provider. Insurance will be checked and
	 * set to null if the provider does not accept the user's
	 * insurance.
	 * @param nameField The patient's entered name
	 * @param injuryBox The selected injury
	 * @param insuranceBox The selected insurance
	 * @param providerBox The selected Provider
	 * @param newPatients
	 */
	private StringBuilder validatePatient(JTextField nameField, JComboBox<Injury> injuryBox,
			JComboBox<Insurance> insuranceBox, JComboBox<Provider> providerBox) 
	{
		// Global variables
		StringBuilder sb = new StringBuilder();
		sb.append("<HTML>");
		String name = nameField.getText();
		Injury injury = (Injury) injuryBox.getSelectedItem();
		Insurance insurance = (Insurance) insuranceBox.getSelectedItem();
		Provider provider  = (Provider) providerBox.getSelectedItem();
		
		// Check if nameField is empty
		if (nameField.getText().isEmpty()) sb.append("Patient's name cannot be empty!<BR>");
		// Check if injury has been chosen.
		if (injuryBox.getSelectedIndex() == -1) sb.append("You must select the injury type!<BR>");
		// Check if insurance has been chosen.
		if (insuranceBox.getSelectedIndex() == -1) sb.append("You must select the patient's insurance!<BR>");
		// Check if provider has been chosen.
		if (providerBox.getSelectedIndex() == -1) sb.append("You must select the provider!<BR>");
		sb.append("</HTML>");
		
		// If none of the above apply, condition will be true
		if (!nameField.getText().isEmpty() && injury != null 
				&& insurance != null && provider != null)
		{
			// Check to see if provider accepts insurance.
			if (provider.acceptsInsurance(insurance))
			{
				// enqueue the patient into the provider's wait list
				provider.enqueuePatient(new Patient(name, injury, insurance));
				
				// FOR TESTING: Print provider's current queue
				MaxPQ<Patient> queueList = provider.getWaitingList();
				System.out.println(provider.getName() + "'s Queue:");
				for (Patient p : queueList) 
				{
					System.out.println(p);
				}
				
				// Success message that the patient has been successfully added.
				sb.setLength(0);
				sb.append(name + " has been sucessfully added to "+ provider.getName() + "'s waiting list!");
				
				// Resets the Admit Patient form to default values.
				nameField.setText("");
				injuryBox.setSelectedIndex(-1);
				insuranceBox.setSelectedIndex(-1);
				providerBox.setSelectedIndex(-1);
			}
			// The provider does not accept the patient's insurance
			else 
			{
				// Patient is enqueued with null insurance and must pay full price.
				provider.enqueuePatient(new Patient(name, injury, null));
				
				// Inform user that patient has been queued and will be billed full price.
				sb.setLength(0);
				sb.append("<HTML>");
				sb.append(name + " has been sucessfully added to "+ provider.getName() + "'s waiting list!");
				sb.append("<BR>" + provider.getName() + " does not accept " + insurance.getName() 
				+ " insurance!<BR>");
				sb.append(name + " will be billed the full price due immediately!<BR>"
						+ "</HTML>");
				
				// Resets the Admit Patient form to default values.
				nameField.setText("");
				injuryBox.setSelectedIndex(-1);
				insuranceBox.setSelectedIndex(-1);
				providerBox.setSelectedIndex(-1);
			}
		}
		return sb;
	}
	
	/**
	 * 
	 * The following three classes are custom made renderers to be used
	 * with the JComboBoxes. They allow the displaying of getName() for
	 * provider and insurance, and getType() for injuries. Additionally,
	 * they allow the default index to be set to -1 in which case a 
	 * placeholder is shown in the ComboBox and disappears when 
	 * clicked on.
	 *
	 *@param prompt The placeholder message for the combo box
	 */
	// Injury renderer
	class InjuryRenderer extends BasicComboBoxRenderer {
		private String prompt;
		public InjuryRenderer(String prompt)
		{
			this.prompt = prompt;
		}
		@SuppressWarnings("rawtypes")
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
		    super.getListCellRendererComponent(list, value, index, isSelected,
		        cellHasFocus);  
		
		    Injury injury = (Injury) value;
		    if (value == null && index == -1) {
		      setText(prompt);
		    } else {
		     
		      setText(injury.getType());
		    }
		    return this;
		  }
	}
	
	// Same logic as above but using Insurance
			class InsuranceRenderer extends BasicComboBoxRenderer {
				private String prompt;
				
				public InsuranceRenderer(String prompt)
				{
					this.prompt = prompt;
				}
				
				  @SuppressWarnings("rawtypes")
				public Component getListCellRendererComponent(JList list, Object value,
				      int index, boolean isSelected, boolean cellHasFocus) {
				    super.getListCellRendererComponent(list, value, index, isSelected,
				        cellHasFocus);

				    Insurance ins = (Insurance) value;

				    if (index == -1 && value == null) {
				      setText(prompt);
				    } else {
				      setText(ins.getName());
				    }
				    return this;
				  }
			}
			
	// Same logic as above but using Provider
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
}

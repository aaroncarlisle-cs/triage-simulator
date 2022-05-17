package teamProject;
/**
 * The Patient class represents a hospital patient
 * with a name, injury type, and insurance type.
 * Once a Provider has been chosen and submitted through
 * the main launcher, the class is instantiated
 * and added into that Provider's MaxPQ().
 * 
 * @author Aaron Carlisle
 * @author Hayden Pack
 */
public class Patient implements Comparable<Patient>
{
	private String name; // name = The patient's name
	private Injury inj; // inj = the Injury the patient is trying to get treated
	private Insurance ins; // ins = the insurance company the patient has. Null if none
	
	/**
	 * Gathers user input to create the patient object and 
	 * enqueue it into the MaxPQ().
	 * @param name the patient's name as input by the user
	 * @param injuryID the Injury.getID() for the selected injury
	 * @param insID the Insurance.getID() for the selected insurance
	 */

	public Patient(String name, Injury inj, Insurance ins)
	{
		this.name = name;
		this.inj = inj;
		this.ins = ins;
	}



	
	//==ALL OF OUR PUBLIC GETTERS==
	
	/**
	 * Retrieves legal name of the patient, for display reasons.
	 * @return The Patient's full legal name, last name first, separated by a comma and space.
	 */
	public String getName() 
	{
		return name;
	}

	/**
	 * Retrieves a pointer to an instance of the Injury class,
	 * which represents the patient's specific Injury.
	 * @return The patient's Injury.
	 */
	public Injury getInjury() 
	{
		return this.inj;
	}

	/**
	 * Retrieves a pointer to an instance of the Insurance class,
	 * which represents the patient's insurance plan.
	 * 
	 * This has a chance of returning null! Check for Insurance
	 * using hasInsurance().
	 * @return The patient's Insurance.
	 */
	public Insurance getInsurance() 
	{
		return ins;
	}
	
	/**
	 * Does the patient have an insurance plan?
	 * @return True if yes, false if no.
	 */
	public Boolean hasInsurance()
	{
		return ins != null;
	}

	//==END GETTERS==
	
	@Override
	public int compareTo(Patient o) 
	{
		//When comparing, make decisions based off of injury priority.
		Integer ourPriority = (int)this.inj.getPriority(); //Get our priority, and compare ours
		return ourPriority.compareTo(o.inj.getPriority()); //against the other patient's priority.
	}
	
	public String toString()
	{
		String phrase = "Patient with name " + this.name
				+ ". Their Injury ID is " + this.inj.getID()
				+ ", and ";
		
		//If they have insurance, add their insurance ID.
		if(this.ins != null) phrase += "their insurance ID is " + this.ins.getID();
		
		//If they do not have insurance, display that.
		else phrase += "they do not have an insurance policy.";
		
		return phrase;
	}

}
package teamProject;
/**
 * The Injury class is the type of affliction attached to a specific person.
 * In this program, each injury has an inherent treatment cost, regardless of provider.
 * (Although, some doctors may or may not accept insurance to reduce the price.)
 * 
 * Injuries differ in severity, although multiple injuries might tie.
 * Each injury can be handled by a doctor that knows how to treat its type.
 * 
 * @author Aaron Carlisle
 * @author Hayden Pack
 */
public class Injury
{
	
	//Our fields, for internal use only.
	private int id; //A unique id, generated at instantiation to identify this specific injury.
	private String type; //Cut, burn, etc.
	private double costInDollars; //The cost, in dollars, for treatment.
	private int priority; //How severe the injury is.
	
	/**
	 * Create a new Injury with the specified parameters
	 * @param id The Clinic ID of this Injury.
	 * Cannot be a duplicate, even across other classes!
	 * @param type The visible name/type of the injury. Cut, burn, etc.
	 * @param costInDollars The base dollar amount this injury takes to treat.
	 * @param priority The higher the priority, the more life threatening.
	 */
	public Injury (String type, double costInDollars, int priority)
	{
		this.id = Clinic.newClinicID();
		this.type = type;
		this.costInDollars = costInDollars;
		this.priority = priority;
	}
	
	//==OUR PUBLIC GETTERS FOR OUR FIELDS==
	
	/**
	 * Retrieves the unique ID for this specific injury.
	 * @return The injury's ID.
	 */
	public int getID()
	{
		return this.id;
	}
	
	/**
	 * Retrieves the injury's visible label (cut, burn, etc).
	 * @return The injury type/label.
	 */
	public String getType()
	{
		return this.type;
	}
	
	/**
	 * Retrieves the base cost to have this injury treated, in dollars.
	 * @return The dollar cost of treating this injury.
	 */
	public double getCostInDollars()
	{
		return this.costInDollars;
	}
	
	/**
	 * Retrieves the priority of the injury.
	 * A higher priority means the injury is more life-threatening.
	 * @return The injury priority.
	 */
	public int getPriority()
	{
		return this.priority;
	}
	
	//==END GETTERS==
	
	public String toString()
	{
		return "Injury with ID " + this.id
				+ ". It's labelled as '" + this.type + "'. "
				+ "It costs $" + this.costInDollars + " to fix "
				+ "and has a priority score of " + this.priority + ".";
	}
	
}

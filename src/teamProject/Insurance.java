package teamProject;
/**
 * The Insurance class marks an agency that reduces the price of treating an Injury.
 * The monthly premium for insurance isn't any of this program's business.
 * We only care about the deductable each Patient has, and their coverage.
 * 
 * TODO If we allow doctors to only accept certain insurance,
 * we can probably use getName for that?
 * 
 * @author Aaron Carlisle
 * @author Hayden Pack
 */
public class Insurance
{

	//Our private fields for internal use only.
	private int id; //Uniquely identify each individual company.
	private String name; //The name of the institution.
	private double coverage; //The percent of bills the plan will pay, excluding deductible.
	private double deductibleInDollars; //The amount the Patient must pay before the plan reduces the rest.
	
	/**
	 * Create a new Insurance provider with the specified status.
	 * @param id The Clinic ID of this Insurance.
	 * Cannot be a duplicate, even across other classes!
	 * @param name The visible company name.
	 * @param coverage The percent of expenses the insurance company will pay.
	 * @param deductibleInDollars The dollar amount the Patient must pay before Insurance kicks in.
	 */
	public Insurance(String name, double coverage, double deductibleInDollars)
	{
		this.id = Clinic.newClinicID();
		this.name = name;
		this.coverage = coverage;
		this.deductibleInDollars = deductibleInDollars;
	}
	
	//==OUR PUBLIC GETTERS FOR OUR FIELDS==
	
	/**
	 * Retrieves the unique ID of this specific insurance policy.
	 * @return The insurance policy ID.
	 */
	public int getID()
	{
		return this.id;
	}
	
	/**
	 * Retrieves the legal name of the insurance company.
	 * Used for display purposes.
	 * @return The insurance company's name.
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Retrieves the fraction of the bill
	 * the insurance provider will pay.
	 * Does not affect the deductible payment.
	 * @return The percent of bills the insurance will pay.
	 */
	public double getCoverage()
	{
		return this.coverage;
	}
	
	/**
	 * Retrieves the payment that must always be made,
	 * before insurance reduces the rest of the bill.
	 * @return The deductible payment, in dollars.
	 */
	public double getDeductibleInDollars()
	{
		return this.deductibleInDollars;
	}
	
	//==END GETTERS==
	
	public String toString()
	{
		return "Insurance company with ID " + this.id
				+ ". Their name is " + this.name
				+ ". Their coverage percent is " + this.coverage * 100
				+ " and their deductable is $" + this.deductibleInDollars + ".";
	}
	
}

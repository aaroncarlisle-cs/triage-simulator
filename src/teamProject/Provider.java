package teamProject;

//import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.MaxPQ;
/**
 * The Provider class represents a professional, or agency of professionals,
 * that treat an Injury. Each Provider has a set of Injuries they can handle.
 * 
 * The cost of treatment is decided by the injury AND the Provider's fee,
 * which is reduced if the Provider accepts the Patient's Insurance.
 * It is possible to get treatment without insurance,
 * but impossible if the Provider can't handle said Injury.
 * 
 * TODO We don't have a system in place for Providers to handle difference Insurance.
 * 
 * @author Aaron Carlisle
 * @author Hayden Pack
 */
public class Provider
{

	//Our private fields for internal use only..
	private int id; //Uniquely identify each specific Provider.
	private String name; //The provider's display name.
	private double feeInDollars; //Additional price on top of the injury's cost.
	private MaxPQ<Patient> waitingList; //A priority queue of patients waiting for treatment
								//from this specific provider
	
	/**
	 * Create a new health care Provider with the specified status.
	 * @param id The Clinic ID of this Provider.
	 * Cannot be a duplicate, even across other classes!
	 * @param name The visible name of the provider, either a person or group.
	 * @param feeInDollars The dollar fee this provider charges for service.
	 */
	public Provider(String name, double feeInDollars)
	{
		this.id = Clinic.newClinicID();
		this.name = name;
		this.feeInDollars = feeInDollars;
		this.waitingList = new MaxPQ<Patient>();
	}
	
	//==OUR PUBLIC GETTERS FOR OUR FIELDS==
	
	/**
	 * Retrieves the ID code for this specific service provider.
	 * @return The provider's ID.
	 */
	public int getID()
	{
		return this.id;
	}
	
	/**
	 * Retrieves the visible name of the provider,
	 * whether it be an individual or a team.
	 * @return The provider's display name.
	 */
	public String getName()
	{
		return this.name;
	}
	
	/**
	 * Retrieves the fee, in dollars, the Provider charges to give service.
	 * @return The provider's dollar fee.
	 */
	public double getFeeInDollars()
	{
		return this.feeInDollars;
	}
	
	public MaxPQ<Patient> getWaitingList()
	{
		return waitingList;
		
	}
	
	/**
	 * Calculates the total amount of money the Patient will pay out of pocket.
	 * Takes the injury cost, provider's fee, and insurance into account.
	 * 
	 * @return The total out-of-pocket expense for the Patient, in dollars.
	 */
	public double getBill(Patient pat)
	{
		Injury inj = pat.getInjury();
		Insurance ins = pat.getInsurance(); //Can be null!
		
		//If they have insurance and we accept it, there is a formula:
		if(pat.hasInsurance() && this.acceptsInsurance(ins))
		{
			//They must pay the deductible no matter what.
			double deductible = ins.getDeductibleInDollars();
			
			//The rest of the bill (not the deductible) gets reduced by insurance.
			double insurancePayment = inj.getCostInDollars() + 
					this.feeInDollars - deductible;
			
			//If the insurance pays 0.8 (80%) of the bill,
			//the patient will pay the other 20%, for example.
			insurancePayment *= (1 - ins.getCoverage());
			
			//Return the untouched deductible plus the reduced rest of the bill.
			return deductible + insurancePayment;
			
		}
		
		//If they don't have insurance, or we don't accept theirs,
		//they must pay the cost of treatment plus our fee.
		return inj.getCostInDollars() + this.feeInDollars;
	}
	
	/**
	 * Can this Provider treat a given Injury?
	 * @param inj An Injury
	 * @return True if yes, false if no.
	 */
	public Boolean canTreat(Injury inj)
	{
		return Clinic.isConnected(this.getID(), inj.getID());
	}
	
	/**
	 * Can this Provider treat a given Patient's Injury?
	 * @param Pat A Patient
	 * @return True if yes, false if no.
	 */
	public Boolean canTreat(Patient pat)
	{
		return Clinic.isConnected(this.getID(), pat.getInjury().getID());
	}
	
	/**
	 * Does this Provider accept the given insurance company?
	 * @param ins A patient's Insurance policy.
	 * @return True if it's accepted here, false if not.
	 */
	public Boolean acceptsInsurance(Insurance ins)
	{
		return Clinic.isConnected(this.getID(), ins.getID());
	}
	
	/**
	 * Returns the delay, in days, that insurance processing will take
	 * with this specific health care Provider. Please use
	 * acceptsInsurance() first to avoid exceptions!
	 * @param ins The given Insurance agency to test the delay of.
	 * @return The number of days until this Provider can fully process
	 * and bill the given insurance.
	 */
	public Integer getDaysToProcessInsurance(Insurance ins)
	{
		//We measure the days by counting each edge.
		//One edge = one day.
		if(!this.acceptsInsurance(ins))
			throw new UnsupportedOperationException("We do not accept that insurance.");
		return Clinic.distanceBetween(this.getID(), ins.getID());
		
	}
	
	//==END GETTERS==
	
	//==OUR PRIORITY QUEUE HANDLER METHODS==
	
	/**
	 * Signs a patient up for this Provider's waiting list.
	 * The waiting list is determined by first-come-first-serve,
	 * but people with more severe injuries will be treated first.
	 * Be sure this Provder can treat this injury first!
	 * Otherwise, an exception will be thrown.
	 * @param pat The patient to enqueue.
	 */
	public void enqueuePatient(Patient pat)
	{
		if(!this.canTreat(pat)) throw new UnsupportedOperationException("We cannot treat that patient.");
		waitingList.insert(pat);
	}
	
	/**
	 * Discharge the patient at the front of the queue.
	 */
	public Patient dischargePatient()
	{
		return waitingList.delMax();
	}
	
	public String toString()
	{
		return "Health care Provider with ID " + this.id +
				". Their name is " + this.name +
				" and their fee is "+ this.feeInDollars + ". "
				+ "Their waiting list is " + waitingList.size()
				+ " people long.";
	}
	
	
}

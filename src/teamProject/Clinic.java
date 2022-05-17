package teamProject;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedDFS;
import edu.princeton.cs.algs4.MaxPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.ArrayList;
import java.util.Iterator;

import edu.princeton.cs.algs4.DepthFirstDirectedPaths;

public class Clinic {

	//The digraph we'll use to track all the program's connections info.
	private static Digraph exampleClinic = new Digraph(11);
	//So far there are 4 injuries + three insurances + three providers + one intermediary
	
	private static int currentClinicID = 0;
	
	
	public static AppWindow initialize()
	{
		Injury injury1 = new Injury("Burn", 100, 4);	
		Injury injury2 = new Injury("Snake Bite", 500, 5);
		Injury injury3 = new Injury("Bruise", 50, 2);
		Injury injury4 = new Injury("Fracture", 150, 4); //Fracture ties with Burn in priority
		
		
		System.out.println("CREATING DIFFERENT INJURIES:");
		System.out.println(injury1.toString());
		System.out.println(injury2.toString());
		System.out.println(injury3.toString());
		System.out.println(injury4.toString());
		System.out.println();
		
		//Make a few Insurance companies for testing
		Insurance ins1 = new Insurance("Red Armor", 0.8, 70);
		Insurance ins2 = new Insurance("United Fitness", 0.5, 100);
		Insurance ins3 = new Insurance("Medic-Aid", 0.3, 20);
		
		System.out.println("CREATING INSURANCE COMPANIES:");
		System.out.println(ins1.toString());
		System.out.println(ins2.toString());
		System.out.println(ins3.toString());
		System.out.println();
		
		//Make a few Providers for testing
		Provider prov1 = new Provider("Dr. Phil", 25);
		Provider prov2 = new Provider("Dr. Dre", 5);
		Provider prov3 = new Provider("Dr. Pepper", 10);
		
		System.out.println("CREATING HEALTHCARE PROVIDERS:");
		System.out.println(prov1.toString());
		System.out.println(prov2.toString());
		System.out.println(prov3.toString());
		System.out.println();
		
		//Connect these providers to the injuries they can treat.
		exampleClinic.addEdge(prov1.getID(), injury1.getID()); //Dr. Phil can treat all four
		exampleClinic.addEdge(prov1.getID(), injury2.getID());
		exampleClinic.addEdge(prov1.getID(), injury3.getID());
		exampleClinic.addEdge(prov1.getID(), injury4.getID());

		exampleClinic.addEdge(prov2.getID(), injury1.getID()); //Dr. Dre cannot treat 2
		exampleClinic.addEdge(prov2.getID(), injury3.getID());
		exampleClinic.addEdge(prov2.getID(), injury4.getID());
		
		exampleClinic.addEdge(prov3.getID(), injury2.getID()); //Dr. Pepper only treats 2 and 4
		exampleClinic.addEdge(prov3.getID(), injury4.getID());
		
		//Connect these providers to the insurances they accept.

		 /*Dr. Phil accepts all three agencies...
		 * However, he must rely on another company to process Insurance 3's bills.
		 * This creates a delay when using Insurance 3 with Dr. Phil.
		 */
		exampleClinic.addEdge(prov1.getID(), ins1.getID()); //Agency 1
		exampleClinic.addEdge(prov1.getID(), ins2.getID()); //Agency 2
		
		Insurance philInsurance3Intermediary = new Insurance("Intermediary", 0.0, 0.0); //Middleman
		exampleClinic.addEdge(prov1.getID(), philInsurance3Intermediary.getID()); //Connect Dr. Phil to Middleman
		exampleClinic.addEdge(philInsurance3Intermediary.getID(), ins3.getID()); //Connect Middleman to Agency 3
														
		
		exampleClinic.addEdge(prov2.getID(), ins1.getID()); //Dr. Dre accepts 1 and 3
		exampleClinic.addEdge(prov2.getID(), ins3.getID());
		
		exampleClinic.addEdge(prov3.getID(), ins3.getID()); //Dr. Pepper only accepts 3
		
		//Create some test patients
		Patient pat1 = new Patient("Armitage, Harlee", injury1, ins3);
		Patient pat2 = new Patient("Saunders, Kathy", injury4, ins1); //Saunders matches Armitage in priority
		Patient pat3 = new Patient("Mclean, Nick", injury2, null);
		
		System.out.println("CREATING PATIENTS:");
		System.out.println(pat1.toString());
		System.out.println(pat2.toString());
		System.out.println(pat3.toString());
		System.out.println();
		
		//We can't do this yet! We need looking up injury by id to work first.
		System.out.println("PUTTING ALL PATIENTS IN PROVIDER 0'S WAITING LIST:");
		prov1.enqueuePatient(pat1);
		prov1.enqueuePatient(pat2);
		prov1.enqueuePatient(pat3);
		System.out.println("Done. " + prov1.toString());
		System.out.println();
		
		System.out.println("LISTING Dr. Phil's WAITING LIST:");
		MaxPQ<Patient> queueList = prov1.getWaitingList();
		for (Patient p : queueList)
		{
			System.out.print(p);
		}
		// TODO Rewrite this section for new waiting list heap iterator
		/*
		 * 
		Patient patients[] = prov1.getWaitingList();
		for(var i = 0; i < patients.length; i++)
		{
			System.out.println(patients[i].toString());
			System.out.println("Their bill is $" + prov1.getBill(patients[i]));
			
			//Calculate the delay of their insurance, if they have it
			if(patients[i].hasInsurance() && prov1.acceptsInsurance(patients[i].getInsurance()))
			{
				var delay = prov1.getDaysToProcessInsurance(patients[i].getInsurance());
				 System.out.println("Has accepted insurance. The processing delay will be " + delay + " days.");
			}
			System.out.println();
		}
		*/
		
		System.out.println();
		System.out.println("This test proves our waiting list is stable.");
		System.out.println("Armitage and Saunders are of equal priority, "
				+ "yet Saunders did not but Armitage in line.");
		
		System.out.println();
		System.out.println("Let's see who Dr. Dre can treat!");
		System.out.println("Can he treat " + pat1.getName() + "? The answer: " + prov2.canTreat(pat1));
		System.out.println("Can he treat " + pat2.getName() + "? The answer: " + prov2.canTreat(pat2));
		System.out.println("Can he treat " + pat3.getName() + "? The answer: " + prov2.canTreat(pat3));
		
		/*
		 * Build a list of objects
		 */
		Stack<Object> clinicList = new Stack<Object>();
		clinicList.push(injury1);
		clinicList.push(injury2);
		clinicList.push(injury3);
		clinicList.push(injury4);
		clinicList.push(ins1);
		clinicList.push(ins2);
		clinicList.push(ins3);
		clinicList.push(prov1);
		clinicList.push(prov2);
		clinicList.push(prov3);		
		
		AppWindow window = new AppWindow(clinicList);
		return window;
	}
	
	/**
	 * Fetch a new unused ID.
	 * @return The new ID.
	 */
	public static int newClinicID()
	{
		//Use the currentClinicID and then move up the counter by one.
		return currentClinicID++;
	}
	
	/**
	 * Are the two ids connected to each other in the Clinic?
	 * @param id1 The first Clinic ID.
	 * @param id2 The second Clinic ID.
	 * @return True if there is at least one path connecting the two IDs.
	 * False if not.
	 */
	public static Boolean isConnected(int id1, int id2)
	{
		DirectedDFS dfs = new DirectedDFS(exampleClinic, id1);
		return dfs.marked(id2);
	}
	
	/**
	 * Calculate the number of edges between id1 and id2.
	 * @param id1 The first Clinic ID.
	 * @param id2 The second Clinic ID.
	 * @return The number of edges between the two.
	 */
	public static int distanceBetween(int id1, int id2)
	{
		DepthFirstDirectedPaths dfs = new DepthFirstDirectedPaths(exampleClinic, id1);
		Iterable<Integer> path = dfs.pathTo(id2);
		if(path == null) throw new NullPointerException("Invalid path to measure. Use isConnected() first!");
		
        //Now that we have our path, calculate the distance
        int distance = 0;
        Iterator<Integer> i = path.iterator();
        while(i.hasNext()) {
            if((int)i.next() != id1) distance++;
            }
		
		return distance;
	}
	
	public ArrayList<Injury> injuryList(ArrayList<Injury> i)
	{
		return i;
	}
	public ArrayList<Provider> providerList(ArrayList<Provider> i)
	{
		return i;
	}
	public ArrayList<Insurance> insuranceList(ArrayList<Insurance> i)
	{
		return i;
	}
	
}

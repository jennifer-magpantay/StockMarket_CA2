import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*
 * The system must create dynamically 100 investors. All of them must have: 
 * A unique ID 
 * A random budget between 1000 and 10000 
 * Any other attribute that you consider relevant to the context 
 * Data Pattern applied: Builder Class
 */

public class Investor implements Comparable<Investor>{

	//add private variables	
	private int id;
	private double budget;			

	//getters
	public int getId() {
		return id;
	}

	public double getBudget() {
		return budget;
	}

	//add a private constructor and implement in it the builder inner class
	private Investor(InvestorBuilder builder) {
		this.id = builder.id;
		this.budget = builder.budget;			
	}

	//add the builder class
	public static class InvestorBuilder{
		//add same variables
		private static int id;
		private static double budget;	
		
		ArrayList<Investor> investors = new ArrayList<Investor>();	

		public InvestorBuilder() {
			create();
		}

		//add a constructor + param
		public InvestorBuilder(int id, double budget) {
			this.id = id;
			this.budget = budget;
		}

		//add the build method to return a instance of a new object of the vclass 
		public Investor build() {
			return new Investor (this);
		}

		public void create() {		

			Investor inv;				

			//(int)(Math.random() * (max - min) + min);	
			//to build new object, consider the random values
			int minId= 1;
			int maxId = 500;
			double maxBud = 1000;
			double minBud = 10000;

			for (int i = 0;  i < 100; i++) {
				id = (int)(Math.random() * (maxId - minId) + minId);
				budget = (double)(Math.random() * (maxBud - minBud) + minBud);
				inv = new Investor.InvestorBuilder(id, budget).build(); //declaring the new object 
				investors.add(inv); 
				//System.out.println(toString()); //printing each element created - testing output				
			}		

			System.out.println("Number of elements registered: " + investors.size()); 
			System.out.println();

			//sorting and printing tge array
			Collections.sort(investors);
			System.out.println("List of Companies sorted by low to high budget:");
			System.out.println(investors);
			System.out.println();

			//reversing the sorted list		
			Collections.reverse(investors);
			System.out.println("Reversed list of Companies. Order by high to low budget:");
			System.out.println(investors);
			System.out.println();			
		}
	}

	@Override
	public String toString() {
		return "Investor ID:" +"\t"+ id +"\t"+ "Budget:" +"\t"+ budget + "\n";
	}

	@Override
	public int compareTo(Investor objlow) {	//from low-high
		if (this.budget < objlow.budget) { 	//(this.shares > objhigh.shares) { //returns high-low
			return -1; 
		} if (this.budget > objlow.budget) { //if (this.shares < objhigh.shares) //returns high-low
			return 1; 
		} 
		return 0;		
	}

}

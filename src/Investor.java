import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
	private int sharesBought;
	
	static ArrayList<Investor> investors = new ArrayList<Investor>();	

	//getters
	public int getId() {
		return id;
	}

	public double getBudget() {
		return budget;
	}
	
	public double getSharesBought() {
		return sharesBought;
	}

	//add a private constructor and implement in it the builder inner class
	private Investor(InvestorBuilder builder) {
		this.id = builder.id;
		this.budget = builder.budget;
		this.sharesBought = builder.sharesBought;
	}

	//add the builder class
	public static class InvestorBuilder{
		//add same variables
		private int id;
		static double budget;	
		static int sharesBought;

		public InvestorBuilder() {
				
		}

		//add a constructor + param
		public InvestorBuilder(int id, double budget, int sharesBought) {
			this.id = id;
			this.budget = budget;
			this.sharesBought = sharesBought;
		}

		//add the build method to return a instance of a new object of the vclass 
		public Investor build() {
			return new Investor (this);
		}	
		
		//add create()?
	}	
	
	//error on create()
	//void create method just works when is added to builder constructor	
	//just accept if create() is created outside of the builder class
	//once is created outside, it will request a local variable of the arraylist or a global one
	
	public ArrayList<Investor> create() {	

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
			sharesBought = 0;
			inv = new Investor.InvestorBuilder(id, budget, sharesBought).build(); //declaring the new object 
			investors.add(inv); 
			//System.out.println(toString()); //printing each element created - testing output				
		}
		
		System.out.println("Number of Investors registered: " + investors.size()); 
		System.out.println();
		System.out.println(investors);
		System.out.println();
		return investors;
	
		//unable to add sort() and reverse() - request local variable of arraylist e returns null		
	}

	public ArrayList<Investor> buy() {
		double minPrice = 10; ///min price value for each share
		
		//buying shares
		//first: pick up random investor from the arraylist
		//add Random method and apply it to and int that will define the index of the array, according to its size	
		Random r = new Random();		
		int randomIndex = r.nextInt(investors.size());
		//System.out.println(investors.get(randomIndex));
		
		//second, check if the investor has enought budget to buy any share, considering its min value/price
		if (budget < minPrice) {
			System.out.println("Short budget!");
			//if budget is lower than the min price, then remove from the list
			investors.remove(randomIndex);			
		}
		//otherwise, buy!!			
		System.out.println("Show me the money!!");
		System.out.println(investors.get(randomIndex));
		return investors;		
	}

	@Override
	public String toString() {
		return "Investor ID:" +"\t"+ id +"\t"+ "Budget:" +"\t"+ budget + "\t"+ "Shares Bought: " + sharesBought + "\n";
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

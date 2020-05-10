package investor;
import java.text.DecimalFormat;
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
	private float budget;
	private int sharesBought;
	
	public static ArrayList<Investor> investors = new ArrayList<Investor>();
	public static ArrayList<Investor> investorsCopy = new ArrayList<Investor>(); 
	
	//formating float numbers			
	DecimalFormat df = new DecimalFormat("#.00");	

	//getters
	public int getId() {
		return id;
	}

	public float getBudget() {
		return budget;
	}

	public int getSharesBought() {
		return sharesBought;
	}
	

	//setters
	public void setBudget(float budget) {
		this.budget = budget;
	}

	public void setSharesBought(int sharesBought) {
		this.sharesBought = sharesBought;
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
		private float budget;	
		private int sharesBought;
		

		public InvestorBuilder() { }

		//add a constructor + param
		public InvestorBuilder(int id, float budget, int sharesBought) {
			this.id = id;
			this.budget = budget;
			this.sharesBought = sharesBought;
		}

		//add the build method to return a instance of a new object of the vclass 
		public Investor build() {
			return new Investor (this);
		}

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
		float maxBud = 10000; //10000
		float minBud = 1000; //1000

		for (int i = 0;  i < 100; i++) {
			
			//generating values
			id = (int)(Math.random() * (maxId - minId) + minId);
			budget = (float)(Math.random() * (maxBud - minBud) + minBud);
			sharesBought = 0;
			
			//creating new objects
			inv = new Investor.InvestorBuilder(id, budget, sharesBought).build(); //declaring the new object 
			
			//saving into the array
			investors.add(inv); 
			//System.out.println(toString()); //printing each element created - testing output				
		}
		
		display();	
		return investors;
	}	
	
	public ArrayList<Investor> display(){
		
		//display the investors and total of budget
		//for loop according to the size to calculate the amount of shares registered
		int total = 0;				
		for (int i = 0; i < investors.size(); i++ ) {					
			total += investors.get(i).getBudget();				
		}	

		System.out.println();
		System.out.println(investors);
		System.out.println();		
		System.out.println("Number of Investors registered: " + investors.size()); 
		System.out.println("Total amount of budget: " + (df.format(total)));	
		System.out.println();
		
		return investors;
	}	

	public ArrayList<Investor> sort(){
		
		Collections.sort(Investor.investors);
		System.out.println(Investor.investors);	
		System.out.println();
		//sorting and printing a top list from low to high number of shares
		//System.out.println("Top 10 list of Investors sorted by low to high number of shares bought:\n");
		//System.out.println(Investor.investors);
		//for (int i = 0; i < 10; i++)
		//System.out.println(Investor.investors.get(i));

		//or just print according to the size of the array		
		//printing copy list with all deleted investors
		Collections.sort(Investor.investorsCopy);			
		System.out.println(Investor.investorsCopy);

		return Investor.investorsCopy;		
	}

	public ArrayList<Investor> reverse(){
		
		//reversing the sorted list		
		Collections.reverse(Investor.investors);
		System.out.println(Investor.investors);	

		System.out.println();		
		Collections.reverse(Investor.investorsCopy);			
		System.out.println(Investor.investorsCopy);
		return Investor.investors;
	}	

	@Override
	public String toString() {
		return "Investor ID:" + "\t" + id + "\t" + "Budget:" + "\t" + (df.format(budget)) + "\t" + "Shares Bought:" + sharesBought + "\n";
	}

	@Override
	public int compareTo(Investor obj) {	//from low-high
		if (this.sharesBought > obj.sharesBought) { 	//(this.shares > objhigh.shares) { //returns high-low
			return -1; 
		} if (this.sharesBought < obj.sharesBought) { //if (this.shares < objhigh.shares) //returns high-low
			return 1; 
		} 
		return 0;		
	}	

}

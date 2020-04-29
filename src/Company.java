import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/*
 * The system must create dynamically 100 companies. All of them must have: 
 * A unique ID 
 * A random number of shares (between 500 and 1000)
 * A random share price (between 10 and 100)
 * Any other attribute that you consider relevant to the context 
 */

public class Company implements Comparable<Company>{
	//add private variables	
	int id;
	int shares;
	double price;
	double profit;
	int sharesSold;
	boolean soldAtLeastOne;	
	
	public ArrayList<Company> companies = new ArrayList<Company>();
	
	//getters
	public int getId() {
		return id;
	}

	public int getShares() {
		return shares;
	}

	public double getPrice() {
		return price;
	}
	public double getProfit() {
		return profit;
	}

	public int getSharesSold() {
		return sharesSold;
	}

	public boolean isSoldAtLeastOne() {
		return soldAtLeastOne;
	}

	//setters
	public void setId(int id) {
		this.id = id;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Company() {			
		create();		
	}

	//add a private constructor and implement in it the builder inner class
	public Company (int id, int shares, double price) {
		this.id = id;
		this.shares = shares;
		this.price = price;	
		this.profit = profit;
		this.sharesSold = sharesSold;
		this.soldAtLeastOne = soldAtLeastOne;
	}

	public void create() {
		
		Company comp;
		
		Random r = new Random();
		//(int)(Math.random() * (max - min) + min);		

		//to build new object, consider the random values
		int minId= 1;
		int maxId = 500;
		int maxSh = 1000;
		int minSh = 500;
		double maxPr = 100;
		double minPr = 10;		
		
		//for loop to generate the random values for according to the size of the array? 
		//or a for loop < 100? //generates 100 elements
		for (int i = 0;  i < 100; i++) {
			id = (int)(Math.random() * (maxId - minId) + minId);
			shares = (int)(Math.random() * (maxSh - minSh) + minSh);
			price = (double)(Math.random() * (maxPr - minPr) + minPr);
			comp = new Company(id, shares, price); //declaring the new object
			companies.add(comp); //saving into the array
			//System.out.println(toString()); //printing each element created - testing output				
		}		

		System.out.println("Number of elements registered: " + companies.size());//printing the size of the array - testing 
		System.out.println();
		//System.out.println(toString()); //prints the last company
		
		//sorting and printing tge array
		Collections.sort(companies);
		System.out.println("List of Companies sorted by low to high number of shares:");
		System.out.println(companies);
		System.out.println();
		
		//reversing the sorted list		
		Collections.reverse(companies);
		System.out.println("Reversed list of Companies. Order by high to low number of shares:");
		System.out.println(companies);
		System.out.println();

		//error!! id number is not unique / there are repeated numbers when using min and max IDs/
		//using r.nextInt() brings negative numbers
		//set double for 2 decimal numbers?
		//APPLY BUILDER CLASS!!
	}
	
	//add method to print sort and reverse lists?
	//print just 20 with lowest shares?

	@Override
	public String toString() {
		return "Company ID:" +"\t"+ id +"\t"+ "Nmber of Shares:" +"\t"+ shares +"\t"+ "Price of Shares:" +"\t"+ price + "\n";
	}

	@Override
	public int compareTo(Company objlow) {	//from low-high
		if (this.shares < objlow.shares) { 	//(this.shares > objhigh.shares) { //returns high-low
			return -1; 
		} if (this.shares > objlow.shares) { //if (this.shares < objhigh.shares) //returns high-low
			return 1; 
		} 
		return 0;		
	}
}	




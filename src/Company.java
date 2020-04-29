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
	static int id;
	static int shares;
	static double price;
	static double profit;
	static int sharesSold;
	boolean soldAtLeastOne;		
	static ArrayList<Company> companies; 

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

	}

	//add a private constructor and implement in it the builder inner class
	public Company (int id, int shares, double price, int sharesSold, double profit) {
		this.id = id;
		this.shares = shares;
		this.price = price;	
		this.profit = profit;
		this.sharesSold = sharesSold;
		this.soldAtLeastOne = soldAtLeastOne;
	}

	public ArrayList<Company> create() {

		Company comp;
		companies = new ArrayList<Company>();

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
			sharesSold = 0;
			profit = 0;
			comp = new Company(id, shares, price, sharesSold, profit); //declaring the new object
			companies.add(comp); //saving into the array
			//System.out.println(toString()); //printing each element created - testing output				
		}		

		System.out.println("Number of Companies registered: " + companies.size());//printing the size of the array - testing 
		System.out.println();
		System.out.println(companies);
		System.out.println();
		//System.out.println(toString()); //prints the last company
		return companies;
	}
	
	public ArrayList<Company> selling() {
		int minShares = 1; ///min amount of shares
		//buying shares
		//first: pick up random investor from the arraylist
		//add Random method and apply it to and int that will define the index of the array, according to its size	
		Random r = new Random();		
		int randomIndex = r.nextInt(companies.size());
		//System.out.println(investors.get(randomIndex));
		
		//second, check if the investor has enought budget to buy any share, considering its min value/price
		if (shares < minShares) {
			System.out.println("Short budget!");
			//if budget is lower than the min price, then remove from the list
			companies.remove(randomIndex);			
		}
		//otherwise, buy!!			
		System.out.println("Show me the money!!");
		System.out.println(companies.get(randomIndex));
		return companies;		
	}

	public void capital() {
		//calculating the Capital of a company (profit)
		//profit = total of sharesSold * price
		//run the arraylist and apply the formula for each element/company
		//sort or reverse
	}
		
	//run create arraylist first!!
	public ArrayList<Company> sort(){
		//sorting and printing a top ten list from low to high number of shares
		Collections.sort(companies);
		System.out.println("Top 10 List of Companies sorted by low to high number of shares:\n");
		//System.out.println(companies);			
		for (int i = 0; i < 10; i++)
			System.out.println(companies.get(i));					
		return companies;			
	}

	public ArrayList<Company> reverse(){
		//reversing the sorted list		
		Collections.reverse(companies);
		System.out.println("Reversed list of Companies. Order by high to low number of shares:\n");
		//System.out.println(companies);
		for (int i = 0; i < 10; i++)
			System.out.println(companies.get(i));					
		return companies;
	}		

	//error!! id number is not unique / there are repeated numbers when using min and max IDs/
	//using random r.nextInt() brings negative numbers
	//set double for 2 decimal numbers? decimalformat?
	//APPLY BUILDER CLASS!!

	@Override
	public String toString() {
		return "Company ID:" +"\t"+ id +"\t"+ "Number of Shares:" +"\t"+ shares +"\t"+ "Price of Shares:" +"\t"+ price +
				"\t"+ "Number of Shares Sold:" +"\t"+ sharesSold +"\t"+ "Capital:" +"\t"+ profit + "\n";
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




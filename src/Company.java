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
	private int id; 
	private int shares;
	private double price;
	private double capital;
	private int sharesSold;

	static ArrayList<Company> companies = new ArrayList<Company>(); 

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

	public double getCapital() {
		return capital;
	}

	public int getSharesSold() {
		return sharesSold;
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

	public void setCapital(double capital) {
		this.capital = capital;
	}

	public void setSharesSold(int sharesSold) {
		this.sharesSold = sharesSold;
	}
	
	//add a private constructor and implement in it the builder inner class
	private Company(CompanyBuilder builder) {
		this.id = builder.id;
		this.shares = builder.shares;
		this.price = builder.price;	
		this.capital = builder.capital;
		this.sharesSold = builder.sharesSold;

	}
	
	//add the builder class
	public static class CompanyBuilder{
		private int id; 
		private int shares;
		private double price;
		private double capital;
		private int sharesSold;		
	
	public CompanyBuilder() {			

	}

	//add a private constructor and implement in it the builder inner class
	public CompanyBuilder(int id, int shares, double price, int sharesSold, double capital) {
		this.id = id;
		this.shares = shares;
		this.price = price;	
		this.capital = capital;
		this.sharesSold = sharesSold;		
	}

	//add the build method to return a instance of a new object of the vclass 
	public Company build() {
		return new Company (this);
	}
	
}
	
	//add create method 			
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
			capital = 0;
			comp = new Company.CompanyBuilder(id, shares, price, sharesSold, capital).build(); //declaring the new object
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
	
	//ERROR!! ID number is not unique
	//set double for 2 decimals? ("%.2f", double) will print 2 decimals number
	
	
	@Override
	public String toString() {
		return "Company ID:" +"\t"+ id +"\t"+ "Number of Shares:" +"\t"+ shares +"\t"+ "Price of Shares:" +"\t"+ price  +
				"\t"+ "Number of Shares Sold:" +"\t"+ sharesSold +"\t"+ "Capital:" +"\t"+ capital + "\n";
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





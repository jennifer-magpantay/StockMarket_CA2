package company;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.Random;
//research source: https://www.journaldev.com/1425/builder-design-pattern-in-java

/*
 * The system must create dynamically 100 companies. All of them must have: 
 * A unique ID 
 * A random number of shares (between 500 and 1000)
 * A random share price (between 10 and 100)
 * Any other attribute that you consider relevant to the context 
 * If a company sells 10 shares, the share price should double up - keeping double price every 10 shares sold by a company
 * If any 10 shares are sold (from any company), and a company hasn’t sold any, the price must reduce in 2% 
 * keeping reducing price (companies with less shares sold) every 10 shares sold (by any other company) 
 * Builder Pattern Applied
 */

public class Company implements Comparable<Company>{
	
	//add private variables	
	private int id; 
	private int shares;
	private float price;
	private float capital;
	private int sharesSold;
	private int counter;
	private int counterTrade;

	//adding arraylist to create all companies
	public static ArrayList<Company> companies = new ArrayList<Company>();
	public static ArrayList<Company> companiesCopy = new ArrayList<Company>(); 

	//formating float numbers //df.format(variable)		
	DecimalFormat df = new DecimalFormat("#.00");	

	//getters	
	public int getId() {
		return id;
	}

	public int getShares() {
		return shares;
	}

	public float getPrice() {
		return price;
	}

	public float getCapital() {
		return capital;
	}

	public int getSharesSold() {
		return sharesSold;
	}
	public int getCounter() {
		return counter;
	}
	public int getCounterTrade() {
		return counterTrade;
	}

	//setters
	public void setId(int id) {
		this.id = id;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setCapital(float capital) {
		this.capital = capital;
	}

	public void setSharesSold(int sharesSold) {
		this.sharesSold = sharesSold;
	}
	public void setCounter(int counter) {
		this.counter = counter;
	}
	public void setCounterTrade(int counterTrade) {
		this.counterTrade = counterTrade;
	}

	//add a private constructor and implement in it the builder inner class
	private Company(CompanyBuilder builder) {
		this.id = builder.id;
		this.shares = builder.shares;
		this.price = builder.price;	
		this.capital = builder.capital;
		this.sharesSold = builder.sharesSold;
		this.counter = builder.counter;
		this.counterTrade = builder.counterTrade;

	}

	//add the builder class
	public static class CompanyBuilder{
		private int id; 
		private int shares;
		private float price;
		private float capital;
		private int sharesSold;	
		private int counter;
		private int counterTrade;
		
		//empty constructor
		public CompanyBuilder() { }

		//add a private constructor and implement in it the builder inner class
		public CompanyBuilder(int id, int shares, float price, int sharesSold, float capital, int counter, int counterTrade) {
			this.id = id;
			this.shares = shares;
			this.price = price;	
			this.capital = capital;
			this.sharesSold = sharesSold;	
			this.counter = counter;
			this.counterTrade = counterTrade;
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
		
		//to get the random values: (int)(Math.random() * (max - min) + min);		

		//add min and max values for the random variables
		int minId= 1;
		int maxId = 500;
		int maxSh = 1000; //1000
		int minSh = 500; //500
		float maxPr = 100; //100
		float minPr = 10;	//10


		//for loop to generate the random values for according to the size of the array? 
		//or a for loop < 100? //generates 100 elements
		for (int i = 0;  i < 100; i++) {
			
			//generating values for each variable
			id = (int)(Math.random() * (maxId - minId) + minId);
			shares = (int)(Math.random() * (maxSh - minSh) + minSh);
			price = (float)(Math.random() * (maxPr - minPr) + minPr);
			sharesSold = 0;
			capital = 0;
			counter = 0;
			counterTrade = 0;
			
			//creating the new objects
			comp = new Company.CompanyBuilder(id, shares, price, sharesSold, capital, counter, counterTrade).build(); //declaring the new object
			
			//saving into the array
			companies.add(comp); 
			//System.out.println(toString()); //printing each element created - testing output			
		}
		
		//method to calculate and display info about the companies create: total companies, total shares, total price of shares, lower and higher share price
		display();
		return companies;
	}

	public ArrayList<Company> display(){
		
		int totalShares = 0;	//total shares from all companies
		float totalPrice = 0;   //total price of all shares
		int lowPrice = 0;       //lowest price share
		int highPrice = 0;       //highest price share
				
		for (int i = 0; i < companies.size(); i++ ) {					
			totalShares += companies.get(i).getShares();
			totalPrice += companies.get(i).getPrice();
			
			//add statements get the low and high prices
			if(companies.get(i).getPrice() < companies.get(lowPrice).getPrice()) {
				lowPrice = i;
			}

			if(companies.get(i).getPrice() > companies.get(highPrice).getPrice()) {
				highPrice = i;
			}		
		}	
		
		//display the arraylist with all companies
		System.out.println(companies);
		System.out.println();
		
		//then, displaying the results
		System.out.println("Number of Companies registered: " + companies.size());
		System.out.println("Total of shares registered to sell: " + totalShares);	
		System.out.println("Total value of the shares registered to sell: " + (df.format(totalPrice)));	
		System.out.println();
		System.out.println("Lowest price share: " + (df.format(companies.get(lowPrice).getPrice())));
		System.out.println("Highest price share: " + (df.format(companies.get(highPrice).getPrice())));	
		System.out.println();
		
		return companies;
	}	

	//run create arraylist first!!
	//for the reports, use the Comparator interface, Collections.sort() and reverse()
	public ArrayList<Company> sort(){
		
		Collections.sort(Company.companies);
		System.out.println(Company.companies);
		System.out.println();

		//sorting and printing a top list from low to high number of shares			
		//System.out.println("Top 10 list of Investors sorted by low to high number of shares bought:\n");
		//System.out.println(Investor.investors);
		//for (int i = 0; i < 10; i++)
		//System.out.println(Investor.investors.get(i));

		//printing copy list
		Collections.sort(Company.companiesCopy);
		System.out.println(Company.companiesCopy);			

		return Company.companies;			
	}

	public ArrayList<Company> reverse(){
		
		//reversing the sorted list		
		Collections.reverse(Company.companies);
		System.out.println(Company.companies);
		System.out.println();

		//printing copy list
		Collections.sort(Company.companiesCopy);
		System.out.println(Company.companiesCopy);					
		return Company.companies;
	}		

	@Override
	public String toString() {
		return "Company ID:" +"\t"+ id +"\t"+ "Number of Shares:" +"\t"+ shares +"\t"+ "Price of Shares:" +"\t"+ (df.format(price))  +
				"\t"+ "Number of Shares Sold:" +"\t"+ sharesSold +"\t"+ "Capital:" +"\t"+ (df.format(capital)) + "\t" + "Counter:" + counter + "\n";
	}

	@Override
	public int compareTo(Company obj) {	//from low-high
		if (this.capital > obj.capital) { 	//(this.shares > objhigh.shares) { //returns high-low
			return -1; 
		} if (this.capital < obj.capital) { //if (this.shares < objhigh.shares) //returns high-low
			return 1; 
		} 
		return 0;		
	}
}





package tradingDay;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import command.Trading;
import command.TradingOffCommand;
import company.Company;
import investor.Investor;

//invoker command class

/*
 * This class will simulate the stock market considering few points:
 * get both Company and Investor ArrayList;
 * BuyShare method: investor has to buy as many shares, one by one;
 * then, subtract budget from investor and add the share into numberOfSharesBought;
 * after, subtract from Company the share sold and add the value into capital;
 * 
 * Double up price: a company that has sold 10 shares, will have its price double up
 * Mark down price: every time 10 shares are sold, a company that has not sold any 
 * share - or with lower sales, will have its price reduce 2%; 
 * 
 */

public class TradingDay {

	//importing the arrayLists
	public static ArrayList<Company> companytr = Company.companies; //to print (Company.companies)
	public static ArrayList<Company> companyCopytr = Company.companiesCopy; //to print (Company.companies)
	
	public static ArrayList<Investor> investortr = Investor.investors; //to print (Investor.investors)
	public static ArrayList<Investor> investorCopytr = Investor.investorsCopy; //to print (Investor.investors)

	boolean validation;

	//formating float numbers			
	DecimalFormat df = new DecimalFormat("#.00");	

	int randomIndex = 0; 		
	int randomComp = 0; 	

	public TradingDay() {}

	Random r = new Random();

	//method that will simulate the stock market			
	public void buyShare() {
		
		//command pattern: off
		Trading trading = new Trading();
		TradingOffCommand off = new TradingOffCommand(trading);	
		
		//min amount of shares a company need to stay in the stock market
		int minShares = 1; 
		
		//getting the value of the min share price
		int minPrice = 0; 
		for (int i = 0; i < Company.companies.size(); i++ ) {							
			if(Company.companies.get(i).getPrice() < Company.companies.get(minPrice).getPrice()) {
				minPrice = i;
			}
		}

		System.out.println("\n********** Starting new negotiation **********\n");

		//buying shares: Investors
		//first: pick up random Investor from the arraylist (Random method)
		//second, check if the Investor has enough money to buy the share from the company. if yes, move on. if not, change company and investor
		//if a Investor has a bugdet lower than the cheapest share, then remove it from the list

		//picking up a invstor
		try {
			validation = false;
			do {

				//first, pick up randomly a investor
				randomIndex = r.nextInt(Investor.investors.size());	
				System.out.println("Checking investors");
				System.out.println(Investor.investors.get(randomIndex));
				//first, start with a true statement to keep going;	

				//second, check if the investor has enough money to buy the shares from the company. If yes, move on
				if (Investor.investors.get(randomIndex).getBudget() > Company.companies.get(minPrice).getPrice()) {
					validation = true;
					System.out.println("Hey! I want your shares");
					System.out.println(Investor.investors.get(randomIndex));					
				}

				//if the budget it is too low, then remove from the list				
				if(Investor.investors.get(randomIndex).getBudget() <= Company.companies.get(minPrice).getPrice()) {
					validation = false;
					System.out.println("Removing Investor: " + Investor.investors.get(randomIndex));
					System.out.println("Budget lower than " + Company.companies.get(minPrice).getPrice());
					investorCopytr.add(Investor.investors.get(randomIndex));
					Investor.investors.remove(randomIndex); 
					
					//something to notice: even the minPrice will change during the trading, and it will change according to the sales
					//if a company with the lowest price hits 10 sales, it will double up its price
					//than the 'minPrice' will double up as well
				}

			} while (validation == false);

		} catch(Exception e) {
			System.out.println("\nNo more investors available. End of the simulation\n");
			//finishing the trading and going back to menu
			off.execute();
		};

		//buying shares: Companies
		//first: pick up random Company from the arraylist (Random method)
		//second, check if the company has at least 1 share to sell
		//third, check if the company has already 10 shares sold. if yes, double up its price	
		//if a company has less than 1 share available, then remove if from the list

		//now, pick up a company share randomly		
		try {
			validation = false;
			do {
				System.out.println("Checking companies");	
				randomComp = r.nextInt(Company.companies.size());				
				System.out.println(Company.companies.get(randomComp));	

				//second, check if the Company has at least 1 share to sell. If yes, move on
				if(Company.companies.get(randomComp).getShares() >= minShares) {
					System.out.println("I have shares! Show me the money!!");
					System.out.println(Company.companies.get(randomComp));	
					validation = true;
				}

				//third, check if the Company has already 10 sales. If yes, double up price and move on
				if  (Company.companies.get(randomComp).getCounter() == 10) {	//==10
					validation = true;
					System.out.println("My shares are flying! I am doubling up my price");

					//set the new price
					float price = Company.companies.get(randomComp).getPrice() * 2;
					Company.companies.get(randomComp).setPrice(price);

					//setting the counter to 0 again
					int counter = 0;
					Company.companies.get(randomComp).setCounter(counter);	
					System.out.println(Company.companies.get(randomComp));					
				}

				//if not, then pick up another investor? pick up another company? or both? //BOTH WORKS BETTER FOR LONG LOOPS //LESS ERRORS!
				//obs: if not pick a new company: falls into a infinite loop looking for a investor that matchs the company price?
				if (Investor.investors.get(randomIndex).getBudget() < Company.companies.get(randomComp).getPrice()) {
					validation = false; 
					//validation true: will pick just another company - allows negative values for budget //error!		
					System.out.println("Investor: Hey! You are too expensive! I am giving up. Next!\n");	//picking another investor

					//if wants to pick another company
					randomIndex = r.nextInt(Investor.investors.size());	
					System.out.println("Checking next investors");
					System.out.println(Investor.investors.get(randomIndex));														
				}

				//then, if the Company has less than 1 share (0), then remove from the list 
				if (Company.companies.get(randomComp).getShares() < minShares ) {
					validation = false;
					System.out.println("Removing: " + Company.companies.get(randomComp));
					System.out.println();
					companyCopytr.add(Company.companies.get(randomIndex));
					Company.companies.remove(randomComp); 					
				}

			} while (validation == false);	

		} catch(Exception e) {
			System.out.println("\nNo more shares available. End of the simulation\n");
			//finishing the simulation and going back to menu	
			off.execute();
		};

		System.out.println("Sales done! Company and Investor updated");	
		
		//updating values for companies and investors
		updateBuyAndSell();	

		//display the update of the trade		
		updateTrade();

	}

	public void updateBuyAndSell() {
		
		//from here make the selling process
		//Company
		//subtract 1 share, add 1 share sold, add the value of the price to capital
		int shares = Company.companies.get(randomComp).getShares() - 1;
		int sharesSold = Company.companies.get(randomComp).getSharesSold() + 1;
		float capital = sharesSold * Company.companies.get(randomComp).getPrice();	
		int counter = Company.companies.get(randomComp).getCounter() + 1;
		int counterTrade = Company.companies.get(randomComp).getCounterTrade() + 1;		

		//update list
		Company.companies.get(randomComp).setShares(shares);
		Company.companies.get(randomComp).setSharesSold(sharesSold);
		Company.companies.get(randomComp).setCapital(capital);
		Company.companies.get(randomComp).setCounter(counter);
		Company.companies.get(randomComp).setCounterTrade(counterTrade);

		//Investor
		//subtract from budget the price of share, add 1 share bought			
		int sharesBought = Investor.investors.get(randomIndex).getSharesBought() + 1;
		float budget =  Investor.investors.get(randomIndex).getBudget() - Company.companies.get(randomComp).getPrice(); 		

		//to update list
		Investor.investors.get(randomIndex).setBudget(budget);
		Investor.investors.get(randomIndex).setSharesBought(sharesBought);	

		//printing the updates				
		System.out.println(Company.companies.get(randomComp));	
		System.out.println(Investor.investors.get(randomIndex));		

	}

	public void updateTrade() {

		//updating the stock market according to the arrays
		int totalShares = 0;
		int totalSharesSold = 0;
		float totalBudget = 0;
		float totalBudgetSpent = 0;
		int totalTrade = 0;
		int lowShareSold = 0;

		for (int i = 0; i < Company.companies.size(); i++ ) {					
			totalShares += Company.companies.get(i).getShares();	
			totalSharesSold += Company.companies.get(i).getSharesSold();
			totalBudgetSpent += Company.companies.get(i).getCapital();
			totalTrade += Company.companies.get(i).getCounterTrade();

			if(Company.companies.get(i).getSharesSold() < Company.companies.get(lowShareSold).getSharesSold()) {
				lowShareSold = i;
			}
		}

		for (int i = 0; i < Investor.investors.size(); i++ ) {					
			totalBudget += Investor.investors.get(i).getBudget();			
		}

		System.out.println("\n********** UPDATING THE STOCK MARKET **********\n");
		System.out.println("Shares sold: " + totalSharesSold);
		System.out.println("Budget spent: " + (df.format(totalBudgetSpent)));	
		System.out.println();
		System.out.println("Shares available: " + totalShares);
		System.out.println("Budget available: " + (df.format(totalBudget)));	
		System.out.println();
		System.out.println("Number of trades: " + totalTrade);

		if (totalTrade == 10) {				
			System.out.println("Round of 10! Reducing price!");
			System.out.println(Company.companies.get(lowShareSold));			
			//reducing the price for the company with less shares sold atm			
			float price = Company.companies.get(lowShareSold).getPrice()-(Company.companies.get(lowShareSold).getPrice()*2/100); //price - (price*2/100); //-2% of the price
			Company.companies.get(lowShareSold).setPrice(price);

			System.out.println(Company.companies.get(lowShareSold));

			//setting to 0 the counter trade			
			for (int i = 0; i < Company.companies.size(); i++ ) {	
				Company.companies.get(i).setCounterTrade(0);								
			}			
		}

		System.out.println("------------------------------------------------------");
	}

	public void simulation() {

		//this method will run the buyShare() 10000 times, till the program stop because of the required conditions //FIRST ATTEMPTS WORKED!
		for (int i = 0;  i < 10000; i++) {			
			buyShare();				
		}	

		//do-while ERROR: running 2 by 2???
		//do{ buyShare(); } while (isMinBudget == false  || isMinShares == false); 

		System.out.println("Simulation finished\n");		
		updateTrade();
	}

}

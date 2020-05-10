import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

//invoker command class??

/*
 * This class will simulate the stock market considering few points:
 * get both Company and Investor ArrayList;
 * BuyShare method: investor has to buy as many shares, one by one;
 * then, subtract budget from investor and add the share into numberOfSharesBought;
 * after, subtract from Company the share sold and add the value into capital;
 * 
 * Double up price: a company that has sold 10 shares, will have its price double up
 * Mark down price: a company that has not sold any share, will have its price reduce 2%; 
 * 
 */
public class TradingDay {
		
	//importing the arrayLists
	ArrayList<Company> companytr = Company.companies; //to print (Company.companies)
	ArrayList<Company> companyCopytr = Company.companiesCopy; //to print (Company.companies)
	ArrayList<Investor> investortr = Investor.investors; //to print (Investor.investors)
	ArrayList<Investor> investorCopytr = Investor.investorsCopy; //to print (Investor.investors)
	
	Menu myMenu = new Menu();
	boolean validation;
	
	//formating float numbers			
	DecimalFormat df = new DecimalFormat("#.00");	

	public TradingDay() {			

	}

	Random r = new Random();

	//method that will simulate the stock market			
	public void buyShare() {
		
		Trading trading = new Trading();
		TradingOffCommand off = new TradingOffCommand(trading);		
				
		int randomIndex = 0; //r.nextInt(Investor.investors.size()); //leaving outside will active the random just once, when the method is executed		
		int randomComp = 0; // = r.nextInt(Company.companies.size());	
		
		int minShares = 1; ///min amount of shares
		
		int minPrice = 0; //getting the value of the min share price
		for (int i = 0; i < Company.companies.size(); i++ ) {							
			if(Company.companies.get(i).getPrice() < Company.companies.get(minPrice).getPrice()) {
				minPrice = i;
			}
		}

		//buying shares: Companies
		//first: pick up random Company from the arraylist (Random method)
		//second, check if the company has at least 1 share to sell
		//third, check if the company has already 10 shares sold. if yes, double up its price	
		//if a company has less than 1 share available, then remove if from the list

		//now, pick up a company share randomly		
		try {
			validation = false;
			do {		
				randomComp = r.nextInt(Company.companies.size());
				System.out.println("\n::Starting new negotiation");
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
					System.out.println(Company.companies.get(randomComp));
					
					//setting the counter to 0 again
					Company.companies.get(randomComp).setCounter(0);			
					
				}

				//then, if the Company has less than 1 share (0), then remove from the list 
				if (Company.companies.get(randomComp).getShares() < minShares ) {
					validation = false;
					System.out.println("Removing: " + Company.companies.get(randomComp));
					companyCopytr.add(Company.companies.get(randomIndex));
					Company.companies.remove(randomComp); 					
				}

			} while (validation == false);	
			
		} catch(Exception e) {
			System.out.println("\nNo more shares available. End of the simulation\n");
			//finishing the simulation and going back to menu	
			off.execute();
		};
		
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
				if (Investor.investors.get(randomIndex).getBudget() >= Company.companies.get(randomComp).getPrice()) {
					validation = true;
					System.out.println("Hey! I want your shares");
					System.out.println(Investor.investors.get(randomIndex));					
				}

				//if not, then pick up another investor? pick up another company? or both? //BOTH WORKS BETTER FOR LONG LOOPS
				//obs: if not pick a new company: falls into a infinite loop looking for a investor that matchs the company price?
				if (Investor.investors.get(randomIndex).getBudget() < Company.companies.get(randomComp).getPrice()) {
					validation = false;
					System.out.println("Hey! You are too expensive! I am giving up. Next!\n");	

					//if wants to pick another company
					randomComp = r.nextInt(Company.companies.size());
					System.out.println("Checking next Company");
					System.out.println(Company.companies.get(randomComp));	

					//false: will pick up another investor - no negative values for budget //less errors!
					//true: will pick just another company - allows negative values for budget //error!	
									
				}
				//if the budget it is too low, then remove from the list				
				if(Investor.investors.get(randomIndex).getBudget() <= Company.companies.get(minPrice).getPrice()) {
					validation = false;
					System.out.println("Removing Investor: " + Investor.investors.get(randomIndex));
					System.out.println("Budget lower than " + Company.companies.get(minPrice).getPrice());
					investorCopytr.add(Investor.investors.get(randomIndex));
					Investor.investors.remove(randomIndex); 	
					//something to notice: even the minPrice will change during the trading, and it will change according to the sales
					//if a company with the lowest price hist 10 sales, it will double up its price
					//than the 'minPrice' will double up as well
				}
							
			} while (validation == false);

		} catch(Exception e) {
			System.out.println("\nNo more investors available. End of the simulation\n");
			//finishing the trading and going back to menu
			off.execute();
		};

		//from here make the selling process
		//Company
		//subtract 1 share, add 1 share sold, add the value of the price to capital
		int shares = Company.companies.get(randomComp).getShares() - 1;
		int sharesSold = Company.companies.get(randomComp).getSharesSold() + 1;
		float capital = sharesSold * Company.companies.get(randomComp).getPrice();	
		int counter = Company.companies.get(randomComp).getCounter() + 1;

		//update list
		Company.companies.get(randomComp).setShares(shares);
		Company.companies.get(randomComp).setSharesSold(sharesSold);
		Company.companies.get(randomComp).setCapital(capital);
		Company.companies.get(randomComp).setCounter(counter);

		//Investor
		//subtract from budget the price of share, add 1 share bought			
		int sharesBought = Investor.investors.get(randomIndex).getSharesBought() + 1;
		float budget =  Investor.investors.get(randomIndex).getBudget() - Company.companies.get(randomComp).getPrice(); 		

		//to update list
		Investor.investors.get(randomIndex).setBudget(budget);
		Investor.investors.get(randomIndex).setSharesBought(sharesBought);	
		
		//printing the updates
		System.out.println("Sales done! Company and Investor updated");
		System.out.println(Company.companies.get(randomComp));	
		System.out.println(Investor.investors.get(randomIndex));
		
		//display the update of the trade		
		display();	
		
		}
	
	public void display() {
		
		//updating the stock market according to the arrays
		int totalShares = 0;
		float totalBudget = 0;
		for (int i = 0; i < Company.companies.size(); i++ ) {					
			totalShares += Company.companies.get(i).getShares();			
		}

		for (int i = 0; i < Investor.investors.size(); i++ ) {					
			totalBudget += Investor.investors.get(i).getBudget();			
		}
		
		System.out.println("********** UPDATING THE STOCK MARKET **********");
		System.out.println("Shares available: " + totalShares);
		System.out.println("Budget available: " + (df.format(totalBudget)));	
		System.out.println();

	}
	
	public void simulation() {

		//this method will run the buyShare() 20000 times, till the program stop because of the required conditions //FIRST ATTEMPTS WORKED!
		//10000 times was not enough to get no investors or no shares to sold
		for (int i = 0;  i < 20000; i++) {			
			buyShare();				
		}	

		//do-while ERROR: running 2 by 2???
		//do{ buyShare(); } while (isMinBudget == false  || isMinShares == false); 

		System.out.println("Simulation finished\n");		
		display();

		//the method works! reports as well! BUT...
		//investors are finishing with negative budget values
		//change the condition/statement for companies? if the budget is > price //works in parts.... bug here!!
	}

	//to implement!!
	public void reducePrice() {
		//when a company do not complete any sale, then reduce 2% of its price
		//get Company Arraylist
		int share = 0;
		double price = 10;
		double down = price - (price*2/100);

		if (share == 0) { //if the number of shares is equal to zero, then reduce
			System.out.println(down);
		}	
		if (share > 0) {//if the number of shares is greater than 0, then keep the price
			System.out.println(price);
			//update the arraylist!
		}

	}

}

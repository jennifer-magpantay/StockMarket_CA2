package tradingDay;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import command.Trading;
import command.TradingOffCommand;
import company.Company;
import investor.Investor;
//research source: 
//research source: 

//Trading Day is the invoker class of the command pattern.
//TradingDay is a Concrete class, which will implement the subject

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


public class TradingDay implements Subject{
	
	//variables to be used on the observer pattern	
	private List<Observer> observers;
    private String message;
    private boolean changes;
		
	//importing the arrayLists
	public static ArrayList<Company> companytr = Company.companies; //to print (Company.companies)
	public static ArrayList<Company> companyCopytr = Company.companiesCopy; //to print (Company.companies)
	
	public static ArrayList<Investor> investortr = Investor.investors; //to print (Investor.investors)
	public static ArrayList<Investor> investorCopytr = Investor.investorsCopy; //to print (Investor.investors)

	boolean validation;

	//formating float numbers //df.format(variable)		
	DecimalFormat df = new DecimalFormat("#.00");	

	int randomIndex = 0; 		
	int randomComp = 0; 
	
	//constructor
	public TradingDay() {
		
		this.observers=new ArrayList<>();	
	}

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
		
		//updating values for companies and investors after the selling/buying process
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
	
	public void displayNews() {
		
		//this method is to create the client as observer and send a message when the market is updated
		//updating values of the market is run apart from displayNews
		
		//create subject
	    TradingDay tr = new TradingDay();
	    //create observers
	    //for the trading, create just on client, that will be the 'viewer' of the trade transactions
	    Observer client = new TradingNews("Client");
	   
	    //register observers to the subject
	    tr.register(client);
	   
	    //attach observer to subject
	    client.setSubject(tr);
	    
	    //check if any update is available, then, send message
	    client.update();	    
	    tr.postMessage("CHECK IT OUT THE LAST UPDATES OF THE STOCK MARKET");
	}


	public void updateTrade() {
		
		System.out.println("******************** STOCK MARKET UPDATES ********************");

        //now send message to notify the client
        displayNews();
        System.out.println();

		//updating the stock market according to the arrays
        //it will return: total of shares sold, total of budget spent, shares & budget available (if any) and total trades
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
	
		System.out.println("Shares sold: " + totalSharesSold);
		System.out.println("Budget spent: " + (df.format(totalBudgetSpent)));	
		System.out.println();
		System.out.println("Shares available: " + totalShares);
		System.out.println("Budget available: " + (df.format(totalBudget)));	
		System.out.println();
		System.out.println("Number of trades: " + totalTrade);
		System.out.println();
		
		//reducing price
		//for every 10 shares sold (by any company) reduce the price of share from the company with 0 or less shares sold
		//so, if trade == 10, then, reduce de price, set the update and set the trade to 0 again
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
		
	}
	
	//this is the method that will run, over and over the buySghare() method
	public void simulation() {

		//this method will run the buyShare() 10000 times, till the program stop because of the required conditions //FIRST ATTEMPTS WORKED!
		//it will stop when:
		//all companies has no more shares (share = 0)
		//all investors has no more budget (budget = 0) or not enough budget to buy the share with low value
		for (int i = 0;  i < 10000; i++) {			
			buyShare();				
		}	

		//do-while ERROR: running 2 by 2???
		//do{ buyShare(); } while (validation == false); 

		System.out.println("Simulation finished\n");
		
		//once is finished, display the updates of the trading
		updateTrade();
	}
	
	//adding implementing methods from Subject interface
	@Override
	public void register(Observer obj) {

		//first, check if the list of observer is null		
		if(obj == null) {			
			throw new NullPointerException("Null Observer List");
		}

		//if not, and if the observer is not already on the list, them add
		if(!observers.contains(obj)) {
			observers.add(obj);
		}	
		
	}

	@Override
	public void unregister(Observer obj) {
		
		//in case need to remove observer from the list		
				observers.remove(obj);
	}

	@Override
	public void notifyObservers() {

		List<Observer> observersLocal = null;
		
		//if there is no changes, than, stop the application                
        if (!changes) {
            return;
        }
        
        //otherwise, add to the list and call the update          
        observersLocal = new ArrayList<>(this.observers);
          
        this.changes = false;
        
        for (Observer obj : observersLocal) {                 
        	obj.update();            
        }
		
	}

	@Override
	public Object getUpdate(Observer obj) {

		//once there are updates, then, print the message		
		return this.message;
	}
	
	//method to post message to notify the updates
		public void postMessage(String msg) {		
			System.out.println("Message Posted to Trading Day: " + msg);        
			
			this.message = msg;        
			this.changes = true;
			
			//if the boolean is true, then call the method to notify clients/observers from the list
			notifyObservers();
		}
}

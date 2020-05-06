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


	//clone
	//ArrayList<TradingDay> compClone = (ArrayList<TradingDay>) Company.companies.clone();
	//ArrayList<TradingDay> invClone = (ArrayList<TradingDay>) Investor.investors.clone();

	Menu myMenu = new Menu();

	boolean validation;

	public TradingDay() {			

	}

	Random r = new Random();

	//method that will simulate the stock market			
	public void buyShare() {	

		double minBudget = 10; ///min price value for each share					
		int randomIndex = 0; //r.nextInt(Investor.investors.size()); //leaving outside will active the random just once, when the method is executed

		int minShares = 1; ///min amount of shares
		int randomComp = 0; // = r.nextInt(Company.companies.size());	

		//buying shares
		//first: pick up random Company from the arraylist
		//add Random method and apply it to and it that will define the index of the array, according to its size	



		//second, check if the investor has enought budget to buy any share, considering its min value/price
		//getShares is not working since will request static values - static gives crazy results //FIXED!!

		//while(condition){ } //while the condition is true keep going
		//do { } while (condition == false); //while the condition is false, do it again

		//now, pick up a company share randomly		
		//second, check if the investor has enought budget to buy any share, considering its min value/price
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

				//third, check if the Company has already 10 sales. If yes, double up price //working!!
				else if  (Company.companies.get(randomComp).getSharesSold() == 10) {	
					validation = true;
					System.out.println("Double up price");
					//set the new price
					double price = Company.companies.get(randomComp).getPrice() * 2;
					Company.companies.get(randomComp).setPrice(price);								
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
			System.out.println("No more shares available. End of the simulation\n");
			//should finish and go back to menu for the reports
			//System.exit(0);
			Trading trading = new Trading();
			TradingOffCommand off = new TradingOffCommand(trading);
			off.execute();
		};

		//Investors
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
				else if (Investor.investors.get(randomIndex).getBudget() < Company.companies.get(randomComp).getPrice()) {
					validation = false;
					System.out.println("Hey! You are too expensive! I am giving up. Next!\n");	

					//if wants to pick another company
					randomComp = r.nextInt(Company.companies.size());
					System.out.println("Checking next Company");
					System.out.println(Company.companies.get(randomComp));	

					//false: will pick up another investor - no negative values for budget //less errors!
					//true: will pick just another company - allows negative values for budget //error!	
									
				}
				
				//if still keeps with companies with high price shares? how break the loop? 
				//or from there remove the investor? or remove company?
				//at same point, it will bug!

				//if the budget is lower or equal than the min price of a share (10), then 
				//remove from the array and add it to the copy-array 
				if (Investor.investors.get(randomIndex).getBudget() <= minBudget) {
					validation = false;
					System.out.println("Removing Investor: " + Investor.investors.get(randomIndex));
					investorCopytr.add(Investor.investors.get(randomIndex));
					Investor.investors.remove(randomIndex); 
					//once is removed, it wont be available for the reports!					
				}
				//Investor.investors.remove(randomIndex); 
				//add the investor to a new array list								
			} while (validation == false);

		} catch(Exception e) {
			System.out.println("No more investors available. End of the simulation\n");
			//should finish and go back to menu for the reports
			//System.exit(0);
			Trading trading = new Trading();
			TradingOffCommand off = new TradingOffCommand(trading);
			off.execute();
		};

		//from here make the selling process
		//Company: subtract 1 share, add 1 share sold, add the value of the price to capital
		int shares = Company.companies.get(randomComp).getShares() - 1;
		int sharesSold = Company.companies.get(randomComp).getSharesSold() + 1;
		double capital = sharesSold * Company.companies.get(randomComp).getPrice();		

		//update list
		//Company.companies.set(randomComp, shares); //error!
		Company.companies.get(randomComp).setShares(shares);
		Company.companies.get(randomComp).setSharesSold(sharesSold);
		Company.companies.get(randomComp).setCapital(capital);

		//Investor: subtract from budget the price of share, add 1 share bought			
		int sharesBought = Investor.investors.get(randomIndex).getSharesBought() + 1;
		double budget =  Investor.investors.get(randomIndex).getBudget() - Company.companies.get(randomComp).getPrice(); //not working

		//to update list
		Investor.investors.get(randomIndex).setBudget(budget);
		Investor.investors.get(randomIndex).setSharesBought(sharesBought);				

		//printing lists: testing
		//System.out.println();
		System.out.println("Sales done! Company and Investor updated");
		System.out.println(Company.companies.get(randomComp));	
		System.out.println(Investor.investors.get(randomIndex));		

	}


	public void simulation() {

		//this method will run the buyShare() 20000 times, till the program stop because of the required conditions //FIRST ATTEMPTS WORKED!
		//10000 times was not enough to get no investors or no shares to sold
		for (int i = 0;  i < 11000; i++) {			
			buyShare();	
		}	

		//do-while ERROR: running 2 by 2???
		//do{ buyShare(); } while (isMinBudget == false  || isMinShares == false); 

		System.out.println("Simulation finished\n");	

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

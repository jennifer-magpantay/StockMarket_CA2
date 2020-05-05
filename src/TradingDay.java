import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;

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

	boolean isMinBudget;
	boolean isMinShares;

	public TradingDay() {			

	}

	Random r = new Random();

	//method that will simulate the stock market			
	public void buyShare() {	

		//buying shares
		//first: pick up random investor from the arraylist
		//add Random method and apply it to and it that will define the index of the array, according to its size	

		double minBudget = 10; ///min price value for each share					
		int randomIndex = 0; //r.nextInt(Investor.investors.size()); //leaving outside will active the random just once, when the method is executed

		int minShares = 1; ///min amount of shares
		int randomComp = 0; // = r.nextInt(Company.companies.size());	


		//second, check if the investor has enought budget to buy any share, considering its min value/price
		//getShares is not working since will request static values - static gives crazy results //FIXED!!

		//while(condition){ } //while the condition is true keep going
		//do { } while (condition == false); //while the condition is false, do it again

		try {
			isMinBudget = false;

			do {	
				//pick up randomly a investor
				randomIndex = r.nextInt(Investor.investors.size());	
				System.out.println(Investor.investors.get(randomIndex));

				if (Investor.investors.get(randomIndex).getBudget() >= minBudget) {
					System.out.println("I have enough budget! I want shares!");
					System.out.println(Investor.investors.get(randomIndex));
					isMinBudget = true;
				}				
				//adding the condition inside while()		
				//if condition is different (budget >= minBudget), then move on
				//otherwise, if condition is the same, then do pick up another investor

				//if the budget is lower than the min price of a share (10), then 
				//remove?? 
				if (Investor.investors.get(randomIndex).getBudget() <= minBudget) {
					isMinBudget = false;
					System.out.println("Removing: " + Investor.investors.get(randomIndex));
					investorCopytr.add(Investor.investors.get(randomIndex));
					Investor.investors.remove(randomIndex); 
					//once is removed, it wont be available for the reports!					
				}
				//Investor.investors.remove(randomIndex); 
				//add the investor to a new array list								
			} while (isMinBudget == false);

		} catch(Exception e) {
			System.out.println("No more investors available. End of the simulation\n");
			//should finish and go back to menu for the reports
			//System.exit(0);
			myMenu.start();
		};

		//now, pick up a company share randomly		
		//second, check if the investor has enought budget to buy any share, considering its min value/price
		try {
			isMinShares = false;

			do {
				randomComp = r.nextInt(Company.companies.size());
				System.out.println(Company.companies.get(randomComp));
				
				if (Investor.investors.get(randomIndex).getBudget() > Company.companies.get(randomComp).getPrice()) {
					System.out.println("I have shares! Show me the money!!");
					System.out.println(Company.companies.get(randomComp));
					isMinShares = true;	
				}	

				if (Company.companies.get(randomComp).getShares() < minShares) {
					isMinShares = false;
					System.out.println("Removing: " + Company.companies.get(randomComp));
					companyCopytr.add(Company.companies.get(randomIndex));
					Company.companies.remove(randomComp); 					
				}

			} while (isMinShares == false);	

		} catch(Exception e) {
			System.out.println("No more shares available. End of the simulation\n");
			//should finish and go back to menu for the reports
			//System.exit(0);
			myMenu.start();
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
		System.out.println("Investor and Company updated");
		System.out.println(Investor.investors.get(randomIndex));
		System.out.println(Company.companies.get(randomComp));	
	
	}


	public void simulation() {

		//this method will run the buyShare() 20000 times, till the program stop because of the required conditions
		//10000 times was not enough to get no investors or no shares to sold
		for (int i = 0;  i < 20000; i++) {			
			buyShare();					
		}	

		//do-while ERROR: running 2 by 2???
		//do{ buyShare(); } while (isMinBudget == false  || isMinShares == false); 

		System.out.println("Simulation finished\n");	
		
		//the method works! reports as well! BUT...
		//investors are finishing with negative budget values
		//change the condition/statement for companies? if the budget is > price //YEP, WORKS!
	}

	public void doubleUpPrice() {
		//when a company achieve 10 sales, then double up
		//get Company Arraylist
		//testing
		int share = 5;
		double price = 10;
		double up = price *2;

		if (share < 10) { //if the number of shares is lower than 10, then keep the price
			System.out.println(price);
		}	
		if (share > 10) { //if the number of shares is greater than 10, then double up the price
			System.out.println(up);
			//update the arraylist!
		}
	}

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

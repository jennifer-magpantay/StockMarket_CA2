import java.util.ArrayList;
import java.util.Collection;
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
	ArrayList<Company> myComp = Company.companies; //to print (Company.companies)
	ArrayList<Investor> myInv = Investor.investors; //to print (Investor.investors)
	
	ArrayList<TradingDay> compClone = new ArrayList<TradingDay>();
	ArrayList<TradingDay> invClone;
	
	
	boolean isMinBudget;
	boolean isMinShares;

	public TradingDay() {			

	}
	
	Random r = new Random();
	
	public boolean isMinBudget(double minBudget) {								
			return minBudget >= 10;	
		}	
	
	public boolean isMinShares(double minShares) {								
		return minShares >= 1;	
	}	
			
	public void BuyShare() {	
		
		//buying shares
		//first: pick up random investor from the arraylist
		//add Random method and apply it to and it that will define the index of the array, according to its size	
		
		double minBudget = 10; ///min price value for each share					
		int randomIndex = r.nextInt(Investor.investors.size());
				
		int minShares = 1; ///min amount of shares
		int randomComp= r.nextInt(Company.companies.size());
				
		
		do {
			
		//second, check if the investor has enought budget to buy any share, considering its min value/price
		//getShares is not working since will request static values - static gives crazy results //FIXED!!
		
		try {		
			isMinBudget = false;
			do {					
				if (Investor.investors.get(randomIndex).getBudget() >= minBudget) { 
					isMinBudget = true;	
							
					System.out.println("I want shares!");
					System.out.println(Investor.investors.get(randomIndex));
				}
			} while (isMinBudget == false);			
		} catch (Exception e) {System.out.println("Short Budget");}
		
		//if the budget is lower than the min price of a share (10), then copy to a new list and call the next obj of the arraylist
		//Investor.investors.remove(randomIndex);
		//invClone.add(Investor.investors.get(randomIndex)); //ERROR ADD! 
		//OBS: the updated are recorded on myInv arraylist = use this one for the reports?		
		
		//now, pick up a company share randomly		
		//second, check if the investor has enought budget to buy any share, considering its min value/price
		try {	
			isMinBudget = false;
			do {				
				if (Company.companies.get(randomComp).getShares() >= minShares) { 
					isMinShares = true;		
							
					System.out.println("Show me the money!!");
					System.out.println(Company.companies.get(randomComp));
				}
			} while (isMinShares == false);
			//if the number of shares is lower than the min number (1), then remove from the list???
			//Company.companies.remove(randomComp);
		} catch (Exception e) {System.out.println("No shares to sell! Next");}		

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
		
		//ERROR
		//when apply static to the variables, the results are crazy - they shouldnt be static
		//how get the values of the variables without apply static to them? - getting the values according to the index!!
		//class.arraylist.get(index).getMethod(); ////class.arraylist.get(index).settMethod();
				
		//to update list
		//Investor.investors.set(randomIndex, budget);	
		Investor.investors.get(randomIndex).setBudget(budget);
		Investor.investors.get(randomIndex).setSharesBought(sharesBought);	
		
		//} while (isMinBudget|| isMinShares);

		//printing lists	
		System.out.println();
		System.out.println("Investor and Company updated");
		System.out.println(Investor.investors.get(randomIndex));
		System.out.println(Company.companies.get(randomComp));	
		
		} while (isMinBudget|| isMinShares);
		
		//arraylist updated
		System.out.println(myInv);
		System.out.println(myComp);
				
		//now that it is working as expected, how do it, over and over again till budget = 0 or shares = 0?
		//if instead remove a company that gets 0 share or 0 budget, I copy them to another arraylist? then, use this arraylist to generate my reports?
		
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

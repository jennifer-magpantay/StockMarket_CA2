import java.util.ArrayList;
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

	public TradingDay() {			

	}

	public void BuyShare() {	
		
		double minPrice = 10; ///min price value for each share		
		//buying shares
		//first: pick up random investor from the arraylist
		//add Random method and apply it to and int that will define the index of the array, according to its size	
		Random r = new Random();		
		int randomIndex = r.nextInt(Investor.investors.size());
		//System.out.println(investors.get(randomIndex));
		
		//second, check if the investor has enought budget to buy any share, considering its min value/price
		if (Investor.InvestorBuilder.budget< minPrice) {
			System.out.println("Short budget!");
			//if budget is lower than the min price, then remove from the list
			Investor.investors.remove(randomIndex);			
		}
		//otherwise, buy!!			
		System.out.println("Show me the money!!");
		System.out.println(Investor.investors.get(randomIndex));
		
		//now, pick up a company share randomly
		int minShares = 1; ///min amount of shares
		//buying shares
		//first: pick up random investor from the arraylist
		//add Random method and apply it to and int that will define the index of the array, according to its size	
		int randomComp= r.nextInt(Company.companies.size());
		//System.out.println(investors.get(randomIndex));
		
		//second, check if the investor has enought budget to buy any share, considering its min value/price
		if (Company.shares < minShares) {
			System.out.println("Short budget!");
			//if budget is lower than the min price, then remove from the list
			Company.companies.remove(randomComp);			
		}
		//otherwise, buy!!			
		System.out.println("Show me the money!!");
		System.out.println(Company.companies.get(randomComp));
		
		//from here make the selling process
		//Investor: subtract from budget the price of share, add 1 share bought		
		double budget = Investor.InvestorBuilder.budget - Company.price;
		int shareBought = Investor.InvestorBuilder.sharesBought + 1;
		Investor.investors.set(randomIndex, budget);	
		Investor.investors.set(randomIndex, shareBought);	
		
		//Company: subtract 1 share, add 1 share sold, add the value of the price to capital
		int shares = Company.shares - 1;
		int sharesSold = Company.sharesSold + 1;
		double profit = Company.sharesSold * Company.price;
		Company.companies.set(randomComp, shares);
		Company.companies.set(randomComp, sharesSold);
		Company.companies.set(randomComp, profit);//arraylist.set(index,value);
		//update the lists
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

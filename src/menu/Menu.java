package menu;
import java.util.Scanner;

import command.Trading;
import command.TradingOnCommand;
import command.TradingUpdateCommand;
import company.Company;
import investor.Investor;

//source singleton pattern: https://www.journaldev.com/1377/java-singleton-design-pattern-best-practices-examples#eager-initialization
/*
 * You are required to present the user with a menu to display the result of the simulation, with the following options:
 * Company with the highest capital (number of shares times (x) latest share price)  
 * Company with the lowest capital (number of shares times (x) latest share price) 
 * If there are more than one company at the top or bottom position, they all should be displayed in the result
 * Investor with the highest number of shares 
 * Investor with the lowest number of shares 
 * It there is more than one investor in any of the positions, they all should be displayed in the result.  
 * Desing Pattern applied: Builder, Command, Singleton and Observer
 */

public class Menu {
	
	private static final Menu instance = new Menu();

	private Menu() {}
	
	public static Menu getInstance() {
		return instance;
	}
	
	public void welcome() {
		
		System.out.println("------------------------------------------------------");
		System.out.println("******************** STOCK MARKET ********************\n");	
		System.out.println("Welcome to the Stock Market. Start the Tradind Day Simulation first to get the reports\n");
		start();
	}

	public void start() {	

		//add scanner method to read the user input
		Scanner userInput = new Scanner(System.in);		
		String option = null; //wont allow any null value (enter)

		Company comp = new Company.CompanyBuilder().build();
		Investor inv = new Investor.InvestorBuilder().build();
				
		Trading trading = new Trading();
		TradingOnCommand on = new TradingOnCommand(trading);
		TradingUpdateCommand update = new TradingUpdateCommand(trading);		
		
		//using the try/catch to the menu be displayed after each operation
		try {
			do {	
				System.out.println("------------------------------------------------------");
				System.out.println("******************** MENU OPTIONS ********************\n");
				System.out.println("1. Trading Day Simulation");				
				System.out.println("2. Companies with Highiest Capital");
				System.out.println("3. Companies with Lowest Capital");
				System.out.println("4. Investors with Highiest number of Shares");
				System.out.println("5. Investors with Lowest number of Shares");
				System.out.println("6. Display Trading Day final results");
				System.out.println();

				option = userInput.nextLine();
				switch (option) {
				case("1"):
					System.out.println("------------------------------------------------------");
				System.out.println("******************** TRADING DAY SIMULATION ********************\n");
				comp.create();
				inv.create();
				on.execute();
				break;

				case("2"):
					System.out.println("------------------------------------------------------");
				System.out.println("******************** COMPANIES WITH HIGHIEST CAPITAL ********************\n");
				comp.sort();
				break;

				case("3"):
					System.out.println("------------------------------------------------------");
				System.out.println("******************** COMPANIES WITH LOWEST CAPITAL ********************\n");
				System.out.println();
				comp.reverse();			
				break;

				case("4"):
					System.out.println("------------------------------------------------------");
				System.out.println("******************** INVESTORS WITH HIGHIEST NUMBER OF SHARES ********************\n");
				inv.sort(); 
				break;

				case("5"):
					System.out.println("------------------------------------------------------");
				System.out.println("******************** INVESTORS WITH LOWEST NUMBER OF SHARES ********************\n");
				inv.reverse();
				break;
				
				case("6"):
					System.out.println("------------------------------------------------------");
				System.out.println("******************** DISPLAY TRADING DAY FINAL RESULTS ********************\n");
				update.execute();
				break;

				default:
					System.out.println("Error reading input. Try again ");
					System.out.println();
				}
			} while (true);
		} catch (Exception e) {System.out.println("Error reading input");}
	}
}








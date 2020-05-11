package command;

import menu.Menu;
import tradingDay.TradingDay;
//research source command design pattern: https://www.devmedia.com.br/padrao-de-projeto-command-em-java/26456

//this is the request class of the command pattern
//here are implemented the methods we want to execute on our program and each class it will be using the methods
//for each method, create a class
public class Trading {
	
	TradingDay tr = new TradingDay();	
	Menu myMenu = Menu.getInstance();	
	
	//TradingOnCommand class
	public void on() {
		System.out.println("The Trading Day Simulation has started\n");
		//go to buy shares		
		tr.buyShare();
		tr.simulation();
		
	}
	
	//TradingOffCommand class
	public void off() {
		System.out.println("The Trading Day Simulation has finished\n");
		tr.updateTrade();
		myMenu.start();
		
	}
	
	//TradingUpdateCommand class
	public void update() {
		System.out.println("The Trading Day Simulation Results\n");
		tr.updateTrade();
		myMenu.start();
	}
			
}
		


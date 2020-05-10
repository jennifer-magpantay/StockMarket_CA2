package command;

import menu.Menu;
import tradingDay.TradingDay;

//request class
public class Trading {
	
	TradingDay tr = new TradingDay();	
	Menu myMenu = Menu.getInstance();	
	
	public void on() {
		System.out.println("The Trading Day Simulation has started\n");
		//go to buy shares		
		tr.buyShare();
		tr.simulation();		
	}
	
	public void off() {
		System.out.println("The Trading Day Simulation has finished\n");
		tr.updateTrade();
		myMenu.start();
		
	}
		
}

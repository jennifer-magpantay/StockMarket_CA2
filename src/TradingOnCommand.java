//concrete class
public class TradingOnCommand implements Command{
	//declaring request class
	Trading trading;

	public TradingOnCommand(Trading trading) {
		this.trading = trading;
	}

	//command interface method
	public void execute() {
		trading.on(); 
	}

}

//Simulation simulCommand = new Simulation(trading);

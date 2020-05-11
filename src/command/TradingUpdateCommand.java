package command;

//concrete class
public class TradingUpdateCommand {

	//declaring request class
	Trading trading;
	
	//passing the request class as parameter
	public TradingUpdateCommand(Trading trading) {
		this.trading = trading;
	}

	//defining the execute method
	public void execute() {
		trading.update(); 
	}

}

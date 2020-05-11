package command;
//research source command design pattern: https://www.devmedia.com.br/padrao-de-projeto-command-em-java/26456

//concrete class
public class TradingOnCommand implements Command{
	
	//declaring request class
	Trading trading;

	//passing the request class as parameter
	public TradingOnCommand(Trading trading) {
		this.trading = trading;
	}

	//command interface method
	public void execute() {
		trading.on(); 
	}
}

//Simulation simulCommand = new Simulation(trading);

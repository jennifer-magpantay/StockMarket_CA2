package command;
//research source command design pattern: https://www.devmedia.com.br/padrao-de-projeto-command-em-java/26456

//concrete class
public class TradingOffCommand implements Command{
	
	//declaring request class
	Trading trading;

	//passing the request class as parameter
	public TradingOffCommand(Trading trading) {
		this.trading = trading;
	}

	//defining the execute method
	public void execute() {
		trading.off();		
	}

}

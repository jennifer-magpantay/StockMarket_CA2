//concrete class
public class TradingOffCommand implements Command{
	//declaring request class
	Trading trading;

	public TradingOffCommand(Trading trading) {
		this.trading = trading;
	}

	public void execute() {
		trading.off();		
	}

}

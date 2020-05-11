package tradingDay;

//this is a concrete class that will be in charge to notify the list observer about any changes on the class (trading day)
//this class implements the observer
public class TradingNews implements Observer {

	private String name;
	private Subject tr;

	public TradingNews (String name) {
		this.name = name;
	}

	@Override
	public void update() {
		String msg = (String) tr.getUpdate(this);

		if(msg == null) {
			//if == null, prints empty?
			System.out.println();
		}
		else {

			System.out.println(name + " :: Consuming message :: " + msg);
		}

	}

	@Override
	public void setSubject(Subject sub) {
		this.tr = sub;		
	}

}
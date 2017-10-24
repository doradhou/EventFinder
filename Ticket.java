
public class Ticket implements Comparable<Ticket> {
	private double price; // Ticket price
	private int amount; // Ticket left
	private String type; // Ticket type

	/**
	 * Constructor with price
	 * @param price
	 */
	public Ticket(double price) {
		this.price = price;
	}

	/**
	 * constructor with price and amount
	 * @param price
	 * @param amount
	 */
	public Ticket(int price, int amount) {
		this.price = price;
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int compareTo(Ticket t) {
		return new Double(this.price).compareTo(new Double(t.price));
	}
}

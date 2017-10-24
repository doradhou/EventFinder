import java.util.TreeSet;

public class Event {
	private int id; //Event id
	private String name; //Event name
	private String description; //Event description
	private TreeSet<Ticket> tickets; // Event tickets - sorted by ticket price
	
	/**
	 * constructor with event id
	 * @param id
	 */
	public Event(int id) {
		this.id = id;
		this.tickets = new TreeSet<>();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public TreeSet<Ticket> getTickets() {
		return tickets;
	}

	public void setTickets(TreeSet<Ticket> tickets) {
		this.tickets = tickets;
	}

}

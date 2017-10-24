import java.awt.Point;

public class Location implements Comparable<Location> {
	private Point point; // Location coordinates
	private String name; // Location name
	private Event event; // Event held at this location. Can be null.
	private int distance; // Distance from user location

	/**
	 * constructor with coordinates
	 * @param p
	 */
	public Location(Point p) {
		this.point = p;
	}

	public Point getPoint() {
		return point;
	}

	public void setPoint(Point p) {
		this.point = p;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	@Override
	public int compareTo(Location o) {
		return new Integer(this.distance).compareTo(new Integer(o.distance));
	}

}

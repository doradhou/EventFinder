import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;
import java.util.TreeSet;

public class EventFinder {
	// Constants
	public static final int negX = -10;
	public static final int posX = 10;
	public static final int negY = -10;
	public static final int posY = 10;
	public static final int topKClosest = 5;
	
	public HashMap<Point, Location> locations; // Store locations

	public EventFinder() {
		locations = new HashMap<>();
	}

	public static void main(String[] args) {
		EventFinder finder = new EventFinder();

		// Input coordinates
		Scanner sc = new Scanner(System.in);
		System.out.println("Please Input Coordinates:");
		Point user = null;
		while (user == null) {
			try {
				String input = sc.nextLine();
				String[] coordinates = input.split(",");
				user = new Point(Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
				if (!finder.isAvailable(user)) { // Out of bound
					user = null;
					System.out.println("Keep ranges from -10 to +10 (Y axis), and -10 to +10 (X axis)");
					System.out.println("Please Input Coordinates(x,y): ");
				}
			} catch (Exception e) {
				// Error format
				System.out.println("Please Input Coordinates(x,y): ");
			}
		}
		
		// Generate random seed data
		finder.genLocs();

		List<Location> result = finder.sortWithBFS(topKClosest, user); // Select closest events by BFS. User location is the start point.
		// List<Location> result = finder.sortAll(topKClosest, user); // Select closest events by sorting all locations with the distances from user location
		
		// Result output
		System.out.printf("Closest Events to (%d,%d):\n", user.x, user.y);
		for (Location loc : result) {
			Event evt = loc.getEvent();
			System.out.println("Event " + String.format("%03d", evt.getId()) + " - $"
					+ String.format("%5.2f", evt.getTickets().first().getPrice()) + ", Distance " + loc.getDistance());
		}
	}

	/**
	 * Select closest events by sorting all locations
	 * @param n - n closest events
	 * @param user - user coordinates
	 * @return n closest location list
	 */
	public List<Location> sortAll(int n, Point user) {
		List<Location> closestLocations = new ArrayList<>();

		// Iterate location map
		for (Entry<Point, Location> entry : locations.entrySet()) {
			Point point = entry.getKey();
			Location loc = entry.getValue();
			Event evt = loc.getEvent();
			if (evt != null && !evt.getTickets().isEmpty()) { // There is an event with ticket
				int distance = Math.abs(user.x - point.x) + Math.abs(user.y - point.y);
				loc.setDistance(distance);
				closestLocations.add(loc);
			}
		}
		
		// Sort locations by distance
		Collections.sort(closestLocations);

		return closestLocations.subList(0, Math.min(n, closestLocations.size()));
	}

	/**
	 * Select closest events by BFS
	 * @param n - n closest events
	 * @param user - start point
	 * @return n closest location list
	 */
	public List<Location> sortWithBFS(int n, Point user) {
		List<Location> closestLocations = new ArrayList<>();

		// Points with distance 1
		int[] dx = { 1, -1, 0, 0 };
		int[] dy = { 0, 0, 1, -1 };

		// Visited array, avoiding duplicated visiting
		boolean[] visited = new boolean[(posX - negX + 1) * (posY - negY + 1)];
		Queue<Point> queue = new LinkedList<>();
		queue.add(user);
		visited[getIndex(user)] = true;
		int distance = 0;
		
		while (!queue.isEmpty()) {
			// each level has the same distance
			int curPointNum = queue.size();
			for (int i = 0; i < curPointNum; i++) {
				Point p = queue.poll();
				// If the location is in map
				if (locations.containsKey(p)) {
					Location loc = locations.get(p);
					Event evt = loc.getEvent();
					if (evt != null && !evt.getTickets().isEmpty()) { // There is an event with ticket
						loc.setDistance(distance);
						closestLocations.add(loc);

						if (closestLocations.size() == n) { // Have already find n events
							return closestLocations;
						}
					}
				}

				// Find next level points
				for (int j = 0; j < dx.length; j++) {
					Point next = new Point(p.x + dx[j], p.y + dy[j]);

					// The point is in bound and has not been visited, push into the queue
					if (isAvailable(next) && !visited[getIndex(next)]) { 
						queue.offer(next);
						visited[getIndex(next)] = true;
					}
				}
			}

			distance++;
		}

		return closestLocations;
	}

	/**
	 * If the point is in bound
	 * @param p
	 * @return
	 */
	public boolean isAvailable(Point p) {
		if (p.x < negX || p.x > posX || p.y < negY || p.y > posY) {
			return false;
		}

		return true;
	}

	/**
	 * Get visited array index
	 * @param p
	 * @return
	 */
	public int getIndex(Point p) {
		return (p.x - negX) * (posY - negY + 1) + (p.y - negY);
	}

	/**
	 * Generate random seed data
	 */
	public void genLocs() {
		// Assumptions for generating seed data
		final int max_location_number = (posX - negX + 1) * (posY - negY + 1);
		final double event_rate = 0.7;
		final int max_ticket_type = 5;
		final double max_ticket_price = 100;
		
		locations = new HashMap<>();
		Random r = new Random();

		int amount = r.nextInt(max_location_number);
		for (int i = 0; i < amount; i++) {
			int x = r.nextInt(posX - negX) + negX;
			int y = r.nextInt(posY - negY) + negY;
			Point p = new Point(x, y);
			
			// New location
			Location location = new Location(p);
			// Some locations hold events
			if (i < event_rate * amount) {
				Event event = new Event(i);
				TreeSet<Ticket> ticketSet = event.getTickets();
				
				// Generate tickets
				int ticketCount = r.nextInt(max_ticket_type);
				for (int j = 0; j < ticketCount; j++) {
					Ticket ticket = new Ticket(r.nextDouble() * max_ticket_price);
					ticketSet.add(ticket);
				}

				location.setEvent(event);
			}

			locations.put(p, location);
		}
	}
}

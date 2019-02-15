import java.time.LocalDate;

public class Reservation implements Comparable<Reservation> {

	private final int id;
	private final LocalDate fromDate;
	private final LocalDate toDate;
	private final int price;

	public Reservation(LocalDate fromDate, LocalDate toDate, int price) {
		this.id = generateRandomId();
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public LocalDate getFromDate() {
		return fromDate;
	}

	public LocalDate getToDate() {
		return toDate;
	}

	public int getPrice() {
		return price;
	}

	private int generateRandomId() {
		return (int) (Math.random() * 10000000);
	}
	
	public String toString() {
		return "id: " + id + ", from: " + fromDate + ", to: " + toDate + ", price: " + price; 
	}

	@Override
	public int compareTo(Reservation reservation) {
		if (fromDate == null || reservation.fromDate == null) {
			return 0;
		}
		return fromDate.compareTo(reservation.fromDate);
	}
}

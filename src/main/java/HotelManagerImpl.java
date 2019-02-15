import java.time.LocalDate;
import java.util.*;

public class HotelManagerImpl implements HotelManager {

    private int numRooms;
    private List<Reservation> reservationList = new ArrayList<>();

    @Override
    public void setNumberOfRooms(int numRooms) {
        this.numRooms = numRooms;
    }

    @Override
    public boolean makeReservation(Reservation reservation) {
        Collections.sort(reservationList);

        int from = 0;
        int to = 0;

        if (reservationList.isEmpty()) {
            reservationList.add(reservation);
            return true;
        }

        if (reservation.getToDate().isBefore(reservationList.get(0).getFromDate())) {
            reservationList.add(reservation);
            return true;
        }

        for (Reservation res : reservationList) {
            if ((reservation.getFromDate().isAfter(res.getFromDate()) || reservation.getFromDate().isEqual(res.getFromDate()))
                    && (reservation.getFromDate().isBefore(res.getToDate()) || reservation.getFromDate().isEqual(res.getToDate()))) {
                from++;
            }
            if ((reservation.getToDate().isAfter(res.getFromDate()) || reservation.getToDate().isEqual(res.getFromDate()))
                    && (reservation.getToDate().isBefore(res.getToDate()) || reservation.getToDate().isEqual(res.getToDate()))) {
                to++;
            }
        }

        if (from < numRooms && to < numRooms) {
            reservationList.add(reservation);
            return true;
        }

        return false;
    }

    @Override
    public void cancelReservation(int reservationId) {
        reservationList.removeIf(reservation -> reservation.getId() == reservationId);
    }

    @Override
    public Reservation getReservation(int reservationId) {
        for (Reservation reservation : reservationList) {
            if (reservation.getId() == reservationId) {
                return reservation;
            }
        }
        return null;
    }

    @Override
    public int getNumberAvailableRooms(LocalDate dateToCheck) {
        Collections.sort(reservationList);
        int availableRooms = numRooms;

        if (reservationList.isEmpty()) {
            return availableRooms;
        }

        for (Reservation res : reservationList) {
            if ((dateToCheck.isAfter(res.getFromDate()) || dateToCheck.isEqual(res.getFromDate()))
                    && (dateToCheck.isBefore(res.getToDate()) || dateToCheck.isEqual(res.getToDate()))) {
                availableRooms--;
            }
        }

        return availableRooms;
    }

    @Override
    public int getPriceOfReservations(LocalDate from, LocalDate to) {
        List<Reservation> tempReservationList = getReservationsByDate(from, to);
        int price = 0;

        for (Reservation res : tempReservationList) {
            price += res.getPrice();
        }

        return price;
    }

    @Override
    public List<Reservation> getAllReservationsSortedByPrice(LocalDate from, LocalDate to) {
        List<Reservation> tempReservationList = getReservationsByDate(from, to);

        tempReservationList.sort(Comparator.comparingInt(Reservation::getPrice));

        return tempReservationList;
    }

    @Override
    public List<Reservation> getAllReservationsSortedByDate(LocalDate from, LocalDate to) {
        List<Reservation> tempReservationList = getReservationsByDate(from, to);

        tempReservationList.sort(Comparator.comparing(Reservation::getFromDate));

        return tempReservationList;
    }

    private List<Reservation> getReservationsByDate(LocalDate from, LocalDate to) {
        Collections.sort(reservationList);
        List<Reservation> tempReservationList = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            if ((reservation.getFromDate().isAfter(from) || reservation.getFromDate().isEqual(from))
                    && (reservation.getToDate().isBefore(to) || reservation.getToDate().isEqual(to))) {
                tempReservationList.add(reservation);
            }
        }

        return tempReservationList;
    }
}

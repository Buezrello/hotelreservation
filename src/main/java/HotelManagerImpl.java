import java.time.LocalDate;
import java.util.*;

public class HotelManagerImpl implements HotelManager {

    private int numRooms;
    private int freeRooms;
    private List<Reservation> reservationList = new ArrayList<>();

    @Override
    public void setNumberOfRooms(int numRooms) {
        this.numRooms = numRooms;
        this.freeRooms = numRooms;
    }

    @Override
    public boolean makeReservation(Reservation reservation) {
        Collections.sort(reservationList);

        if (reservationList.isEmpty()) {
            reservationList.add(reservation);
            return true;
        }

        if (reservation.getToDate().isBefore(reservationList.get(0).getFromDate())) {
            reservationList.add(reservation);
            return true;
        } else if (reservation.getFromDate().isAfter(reservationList.get(reservationList.size()-1).getToDate())) {
            reservationList.add(reservation);
            return true;
        } else {
            for (int i=0; i< reservationList.size()-1; i++) {
                if (reservation.getFromDate().isAfter(reservationList.get(i).getToDate()) &&
                    reservation.getToDate().isBefore(reservationList.get(i+1).getFromDate())) {
                    reservationList.add(reservation);
                    return true;
                }
            }
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

        int freeRooms = numRooms;

        if (dateToCheck.isBefore(reservationList.get(0).getFromDate())) {
            return numRooms;
        } else if (dateToCheck.isAfter(reservationList.get(reservationList.size()-1).getToDate())) {
            return numRooms;
        } else {
            for (int i=0; i< reservationList.size()-1; i++) {
                if (dateToCheck.isAfter(reservationList.get(i).getToDate()) &&
                        dateToCheck.isBefore(reservationList.get(i+1).getFromDate())) {
                    return numRooms;
                }
            }
            for (int i=0; i< reservationList.size()-1; i++) {
                if (dateToCheck.isBefore(reservationList.get(i).getToDate())) {
                    freeRooms--;
                    if (dateToCheck.isAfter(reservationList.get(i+1).getFromDate())) {
                        freeRooms--;
                    }
                    return freeRooms;
                }
            }
        }

        return 0;
    }

    @Override
    public int getPriceOfReservations(LocalDate from, LocalDate to) {
        Collections.sort(reservationList);

        int price = 0;

        for (Reservation reservation : reservationList) {
            if (reservation.getFromDate().isAfter(from) && reservation.getToDate().isBefore(to)) {
                price += reservation.getPrice();
            }
        }

        return price;
    }

    @Override
    public List<Reservation> getAllReservationsSortedByPrice(LocalDate from, LocalDate to) {
        Collections.sort(reservationList);

        List<Reservation> tempReservationList = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            if (reservation.getFromDate().isAfter(from) && reservation.getToDate().isBefore(to)) {
                tempReservationList.add(reservation);
            }
        }

        tempReservationList.sort(Comparator.comparingInt(Reservation::getPrice));

        return tempReservationList;
    }

    @Override
    public List<Reservation> getAllReservationsSortedByDate(LocalDate from, LocalDate to) {
        Collections.sort(reservationList);

        List<Reservation> tempReservationList = new ArrayList<>();

        for (Reservation reservation : reservationList) {
            if (reservation.getFromDate().isAfter(from) && reservation.getToDate().isBefore(to)) {
                tempReservationList.add(reservation);
            }
        }

        tempReservationList.sort(Comparator.comparing(Reservation::getFromDate));

        return tempReservationList;
    }
}

import java.time.LocalTime;

class Reservation {
    String clientName;
    LocalTime startHour;
    LocalTime endHour;

    public Reservation(String clientName, LocalTime startHour, LocalTime endHour) {
        this.clientName = clientName;
        this.startHour = startHour;
        this.endHour = endHour;
    }
}

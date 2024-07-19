import java.time.LocalTime;
import java.util.ArrayList;

class Poste {
    String typePoste;
    String typeEcran;
    boolean etat;
    ArrayList<Reservation> reservations;
    ArrayList<String> joueurList;
    ArrayList<String> waitingList;


    public Poste(String typePoste, String typeEcran) {
        this.typePoste = typePoste;
        this.typeEcran = typeEcran;
        this.etat = false; // false = not occupied , true = occupied
        this.reservations = new ArrayList<>();
        this.joueurList = new ArrayList<>();
        this.waitingList = new ArrayList<>();
    }

    public boolean isAvailable(LocalTime startHour, LocalTime endHour) {
        for (Reservation reservation : reservations) {
            if ((startHour.isBefore(reservation.endHour) && endHour.isAfter(reservation.startHour))) {
                return false;
            }
        }
        return true;
    }

    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }

    public void addToWaitingList(String clientName) {
        waitingList.add(clientName);
    }
    public void addTojoueurList(String clientName) {
        joueurList.add(clientName);
    }


    public String getTypePoste() {
        return this.typePoste;
    }

    public String getTypeEcran() {
        return this.typeEcran;
    }

    public boolean getEtat() {
        return this.etat;
    }

    public void setEtat(boolean etat) {
        this.etat = etat;
    }
}

import java.time.*;
import java.util.*;


class GameSpace {
    public static final LocalTime openingHourMorning = LocalTime.of(9, 0);
    public static final LocalTime closingHourMorning = LocalTime.of(12, 0);
    public static final LocalTime openingHourAfternoon = LocalTime.of(14, 0);
    public static final LocalTime closingHourAfternoon = LocalTime.of(20, 0);

    public static void main(String[] args) {
        System.out.println("\n**********GameSpace**********\n");
        Scanner scanner = new Scanner(System.in);
        ArrayList<Poste> postes = new ArrayList<>();

        // Initializing game postes
        postes.add(new Poste("xbox", "dell"));
        postes.add(new Poste("xbox", "dell"));
        postes.add(new Poste("xbox", "dell"));
        postes.add(new Poste("xbox", "hp"));
        postes.add(new Poste("playstation", "asus"));
        postes.add(new Poste("playstation", "asus"));
        postes.add(new Poste("playstation", "asus"));
        postes.add(new Poste("Nintendo", "samsung"));
        postes.add(new Poste("Nintendo", "samsung"));

        Admin admin = new Admin();
        boolean premierJoueur = true;

        while (true) {
            System.out.print("Entrez votre prénom -> ");
            String prenom = scanner.nextLine();

            System.out.print("Entrez votre nom -> ");
            String nom = scanner.nextLine();


            for (int i = 0; i < postes.size(); i++) {
                System.out.println((i + 1) + ". Poste: " + postes.get(i).getTypePoste() + ", Ecran: " + postes.get(i).getTypeEcran() + (postes.get(i).getEtat() ? " (occupé) : prochaine disponibilité à : "+postes.get(i).reservations.getLast().endHour : " (libre)"));
            }
            System.out.print("Entrez le numéro du poste (1-9) -> ");
            int posteIndex = Integer.parseInt(scanner.nextLine()) - 1;

            if (posteIndex < 0 || posteIndex >= postes.size()) {
                System.out.println("Numéro de poste invalide.");
                continue;
            }

            Poste posteChoisi = postes.get(posteIndex);

            System.out.print("Les horaires d'ouverture : 09:00 -> 12:00 & 14:00 -> 18:00 \n Entrez l'heure de début (format HH:MM) -> ");
            LocalTime heureDebut = LocalTime.parse(scanner.nextLine());
            while (heureDebut.isBefore(LocalTime.parse("09:00")) || (heureDebut.isAfter(LocalTime.parse("12:00")) && heureDebut.isBefore(LocalTime.parse("14:00"))) || heureDebut.isAfter(LocalTime.parse("18:00"))) {
                System.out.print("les horaires d'ouverture : 09:00 -> 12:00 & 14:00 -> 18:00 \n Merci de choisir l'heure en respectant les horaires->");
                heureDebut = LocalTime.parse(scanner.nextLine());
            }

            System.out.print("Les periodes disponibles : 30 min , 60 min (1 heure) , 120 min (2 heures) , 300 min (5 heures) , 720 min (toute la journée) \n Entrez la durée de l'occupation en minutes -> ");
            int periode = Integer.parseInt(scanner.nextLine());
            while (periode != 30 && periode != 60 && periode != 120 && periode != 300 && periode != 720) {
                System.out.print("Cette période n'est pas disponible ! \n Les periodes disponibles : 30 min , 60 min (1 heure) , 120 min (2 heures) , 300 min (5 heures) , 720 min (toute la journée) \n Veuillez saisir l'une de ces périodes -> ");
                periode = Integer.parseInt(scanner.nextLine());
            }

            LocalTime heureFin = heureDebut.plusMinutes(periode);

            if (isWithinOperatingHours(heureDebut, heureFin) && posteChoisi.isAvailable(heureDebut, heureFin)) {
                posteChoisi.addReservation(new Reservation(prenom + " " + nom, heureDebut, heureFin));
                posteChoisi.setEtat(true);
                System.out.println("Réservation réussie pour " + prenom + " " + nom + " au poste " + (posteIndex + 1));

                System.out.println("Entrez le jeu auquel vous voulez jouer :");
                String jeu = scanner.nextLine();

                Joueur joueur = new Joueur(prenom, nom, posteChoisi.getTypePoste(), posteChoisi.getTypeEcran(), heureDebut, heureFin, periode, jeu);
                admin.eteindrePoste(joueur, posteChoisi);
                if (premierJoueur) {
                    joueur.setPremierJoueur(true);
                    premierJoueur = false;
                }

                double prix = admin.tarifs(periode, jeu, joueur.getPremierJoueur(), posteChoisi.getTypePoste(), posteChoisi.getTypeEcran());
                System.out.println("Veuillez payer le montant nécessaire -> " + prix + " DH");
                double prixDonner = scanner.nextDouble();
                scanner.nextLine();
                while (prixDonner != prix) {
                    System.out.println("Ce n'est pas le paiement correct, veuillez faire attention !!");
                    prixDonner = scanner.nextDouble();
                    scanner.nextLine();
                }
                System.out.println("Votre code est : " + joueur.getCodeJoueur());

                admin.calculRevenus(prixDonner, posteChoisi);
                posteChoisi.addTojoueurList(prenom + " " + nom);
                System.out.println("Le joueur " + joueur.getPrenom() + " " + joueur.getNom() + " pour poste " + joueur.getNomPoste() + " avec écran " + joueur.getNomEcran() + " début à l'heure " + joueur.getHeureDebut() + " pour une période de " + periode + " minutes pour jouer à " + joueur.getJeu() + ". Le prix " + prix + " est ajouté !!");
            } else if ((heureFin.isAfter(closingHourMorning) && heureFin.isBefore(openingHourAfternoon)) || (heureFin.isAfter(closingHourAfternoon))) {
                System.out.println("Cette reservation n'existe pas !!");
            } else {
                posteChoisi.addToWaitingList(prenom + " " + nom);
                System.out.println("Le poste est déjà occupé. " + prenom + " " + nom + " a été ajouté à la liste d'attente.");
            }

            System.out.println("Ajouter un autre joueur oui ou non ?");
            String reponse = scanner.nextLine();
            if (reponse.equalsIgnoreCase("non")) {
                break;
            }
        }

        // Affichage de l'état des postes
        System.out.println("État des postes :");
        for (int i = 0; i < postes.size(); i++) {
            Poste poste = postes.get(i);
            String etat = poste.getEtat() ? "Occupé" : "Libre";
            System.out.println((i + 1) + ". Poste: " + poste.getTypePoste() + ", Ecran: " + poste.getTypeEcran() + " - " + etat);
        }

        admin.afficherRevenusJour();
        admin.afficherRevenusMois();
        scanner.close();
    }

    private static boolean isWithinOperatingHours(LocalTime startHour, LocalTime endHour) {
        return ((startHour.isAfter(openingHourMorning.minusMinutes(1)) && endHour.isBefore(closingHourMorning.plusMinutes(1))) ||
                (startHour.isAfter(openingHourAfternoon.minusMinutes(1)) && endHour.isBefore(closingHourAfternoon.plusMinutes(1))));
    }

}

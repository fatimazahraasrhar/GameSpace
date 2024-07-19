import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

class Admin {
    private ArrayList<Joueur> listeJoueurs = new ArrayList<>();
    private ArrayList<Joueur> listeAttenteJoueurs = new ArrayList<>();
    double revenusJour;
    double revenusMois;

    public void calculRevenus(double prixDonner, Poste poste) {
        revenusJour += prixDonner;
        revenusMois += prixDonner;
//        eteindrePoste(joueur, poste);
    }

//    public void ajouterAuListeAttente(Joueur joueur, double prixDonner) {
//        listeAttenteJoueurs.add(joueur);
//        revenusJour += prixDonner;
//        revenusMois += prixDonner;
//    }

    public void afficherRevenusJour() {
        System.out.println("Revenu du jour : " + revenusJour);
    }

    public void afficherRevenusMois() {
        System.out.println("Revenu du mois : " + revenusMois);
    }

    public int minutesToMilliseconds(int minutes) {
        return minutes * 60000;
    }

    public double tarifs(int periode, String jeu, boolean premierJoueur, String nomPoste, String nomEcran) {
        double price;
        switch (periode) {
            case 30:
                price = 5;
                break;
            case 60:
                price = 10;
                break;
            case 120:
                price = 18;
                break;
            case 300:
                price = 40;
                break;
            case 720:
                price = 65;
                break;
            default:
                price = 0;
                break;
        }
        if (jeu.equalsIgnoreCase("FIFA") && periode >= 120) {
            price *= 0.95;
        }
        if (premierJoueur) {
            price *= 0.98;
        }
        if (nomPoste.equalsIgnoreCase("playstation") && nomEcran.equalsIgnoreCase("samsung") && periode >= 300) {
            price *= 0.90;
        }
        return price;
    }
    public void eteindrePoste(Joueur joueur, Poste poste) {
        Timer timer = new Timer();
        TimerTask task = new MyTask(joueur, poste);
        timer.schedule(task, minutesToMilliseconds(joueur.getPeriode()));
    }
}

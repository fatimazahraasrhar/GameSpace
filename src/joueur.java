import java.time.LocalTime;
import java.util.UUID;

class Joueur {
    private String prenom;
    private String nom;
    private String nomPoste;
    private String nomEcran;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private int periode; // en minutes
    private String jeu;
    private String codeJoueur;
    private boolean premierJoueur;

    public Joueur(String prenom, String nom, String nomPoste, String nomEcran, LocalTime heureDebut, LocalTime heureFin, int periode, String jeu) {
        this.prenom = prenom;
        this.nom = nom;
        this.nomPoste = nomPoste;
        this.nomEcran = nomEcran;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.periode = periode;
        this.jeu = jeu;
        this.codeJoueur = UUID.randomUUID().toString();
        this.premierJoueur = false;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getNomPoste() {
        return nomPoste;
    }

    public String getNomEcran() {
        return nomEcran;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public int getPeriode() {
        return periode;
    }

    public String getJeu() {
        return jeu;
    }

    public String getCodeJoueur() {
        return codeJoueur;
    }

    public boolean getPremierJoueur() {
        return premierJoueur;
    }

    public void setPremierJoueur(boolean premierJoueur) {
        this.premierJoueur = premierJoueur;
    }
}

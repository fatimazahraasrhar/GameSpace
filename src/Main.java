import java.util.Scanner;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.time.*;

class poste{
    private String type;
    private boolean etat;
    public poste(String type){
        this.type=type;
        this.etat=false;
    }
    public String getType(){
        return this.type;
    }
    public boolean getEtat(){
        return this.etat;
    }
    public void setEtat(boolean etat){
        this.etat=etat;
    }
    public void setType(String type){
        this.type=type;
    }
}
class ecran{
    private String type;
    private boolean etat;
    public ecran(String type){
        this.type=type;
        this.etat=false; //false = not occupied / true = occupied
    }
    public String getType(){
        return this.type;
    }
    public void setEtat(boolean etat){
        this.etat=etat;
    }
    public void setType(String type){
        this.type=type;
    }
    public boolean getEtat(){
        return this.etat;
    }
}
class joueur{
    private String prenom;
    private String nom;
    private String nomPost;
    private String nomEcran;
    private String heureDebut;
    private int periode;
    private String jeu;
    private String codeJoueur;
    private boolean premierJoueur; // false = nest pas premier
    public joueur(String prenom,String nom,String nomPost,String nomEcran,String heureDebut,int periode,String jeu){
        this.prenom=prenom;
        this.nom=nom;
        this.nomPost=nomPost;
        this.nomEcran=nomEcran;
        this.heureDebut=heureDebut;
        this.periode=periode;
        this.jeu=jeu;
        this.codeJoueur=UUID.randomUUID().toString();
        this.premierJoueur=false;
    }
    public String getPrenom(){
        return prenom;
    }
    public String getNom(){
        return nom;
    }
    public String getNomPost(){
        return nomPost;
    }
    public String getNomEcran(){
        return nomEcran;
    }
    public String getHeureDebut(){
        return heureDebut;
    }
    public int getPeriode(){
        return periode;
    }
    public String getJeu(){
        return jeu;
    }
    public String getCodeJoueur(){
        return codeJoueur;
    }
    public boolean getPremierJoueur(){
        return premierJoueur;
    }

    public void setPremierJoueur(boolean premierJoueur) {
        this.premierJoueur = premierJoueur;
    }
}
class MyTask extends TimerTask {
    private joueur joueur;
    private poste poste;
    private ecran ecran;
    public MyTask(joueur joueur,poste poste,ecran ecran) {
        this.joueur = joueur;
        this.poste = poste;
        this.ecran = ecran;
    }
    public void run() {
        System.out.println("Le temps est écoulé pour le joueur " + joueur.getPrenom() + " " + joueur.getNom() + ". Le poste va s'éteindre.");
        poste.setEtat(false);
        ecran.setEtat(false);
        System.out.println("Le poste "+poste.getType()+" et l'ecran "+ecran.getType());

    }
}

class admin {
    private ArrayList<joueur> listeJoueurs = new ArrayList<>();
    private ArrayList<joueur> listeAttenteJoueurs = new ArrayList<>();
    double revenusJour;
    double revenusMois;

    public void ajouterAuJoueur(joueur joueur, int prixDonner, poste poste,ecran ecran) {
        if (listeJoueurs.size() < 9) {
            listeJoueurs.add(joueur);
            revenusJour = revenusJour + prixDonner;
            revenusMois = revenusMois + prixDonner;
            eteindrePoste(joueur,poste,ecran);
        }
    }

    public void ajouterAuListeAttente(joueur joueur, int prixDonner) {
        if (listeAttenteJoueurs.size() < 8) {
            listeAttenteJoueurs.add(joueur);
            revenusJour = revenusJour + prixDonner;
            revenusMois = revenusMois + prixDonner;
        } else {
            System.out.println("le liste d'attente est pleine!!");
        }
    }

    public void afficherRevenusJour() {System.out.println("Revenue du jour : " + revenusJour);}

    public void afficherRevenusMois() {
        System.out.println("Revenue du mois : " + revenusMois);
    }

    public int minutesToMilliseconds(int minutes) {
        return minutes * 60000;
    }

    public int tarifs(int periode, String jeu, boolean premierJoueur, String nomPost, String nomEcran) {
        int price;
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
        if (jeu.equalsIgnoreCase("FIFA") && (periode >= 120)) {
            price = price - ((price * 5) / 100);
        }
        if (premierJoueur==true) {
            price = price - ( (price * 2) / 100);
        }
        if ((nomPost.equalsIgnoreCase("playstation")) && (nomEcran.equalsIgnoreCase("samsung")) && (periode >= 300)) {
            price = price - ( (price * 10) / 100);
        }
        return price;
    }
    public void eteindrePoste(joueur joueur,poste poste,ecran ecran) {
        Timer timer = new Timer();
        TimerTask task = new MyTask(joueur,poste,ecran);
        timer.schedule(task, minutesToMilliseconds(joueur.getPeriode()));
    }
}
class main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Application game space : "); //application gamespace
        ArrayList<poste> postes = new ArrayList<>();
        postes.add(new poste("xbox"));
        postes.add(new poste("xbox"));
        postes.add(new poste("xbox"));
        postes.add(new poste("xbox"));
        postes.add(new poste("playstation"));
        postes.add(new poste("playstation"));
        postes.add(new poste("playstation"));
        postes.add(new poste("Nintendo"));
        postes.add(new poste("Nintendo"));

        ArrayList<ecran> ecrans = new ArrayList<>();
        ecrans.add(new ecran("dell"));
        ecrans.add(new ecran("dell"));
        ecrans.add(new ecran("dell"));
        ecrans.add(new ecran("hp"));
        ecrans.add(new ecran("asus"));
        ecrans.add(new ecran("asus"));
        ecrans.add(new ecran("asus"));
        ecrans.add(new ecran("samsung"));
        ecrans.add(new ecran("samsung"));

        admin admin = new admin();
        boolean premierJoueur = true;

        while (true) {
            System.out.println("Saisir un prenom : ");
            String prenom = scanner.nextLine();

            System.out.println("Saisir un nom : ");
            String nom = scanner.nextLine();

            System.out.println("Saisir le nom du poste \n 1. xbox \n 2. playstation \n 3. nintendo switch ");
            String nomPost = scanner.nextLine();
            while (!(nomPost.equalsIgnoreCase("xbox") ||
                    nomPost.equalsIgnoreCase("playstation") ||
                    nomPost.equalsIgnoreCase("nintendo"))) {
                System.out.println("Veuillez saisir l'un des postes (xbox, playstation, nintendo) :");
                nomPost = scanner.nextLine();
            }


            System.out.println("Saisir le nom de l'ecran \n 1. dell \n 2. hp \n 3. asus \n 4. samsung ");
            String nomEcran = scanner.nextLine();
            while (!(nomEcran.equalsIgnoreCase("dell") ||
                    nomEcran.equalsIgnoreCase("hp") ||
                    nomEcran.equalsIgnoreCase("asus") ||
                    nomEcran.equalsIgnoreCase("samsung"))) {
                System.out.println("Veuillez saisir l'un des ecrans (dell, hp, asus, samsung) :");
                nomEcran = scanner.nextLine();
            }

            System.out.println("Saisir l'heure de debut: 09:00  ->  12:00  &  14:00  ->  18:00 ");
            String heureDebut = scanner.nextLine();
            LocalTime time1 = LocalTime.parse(heureDebut);
            while ( time1.isBefore(LocalTime.parse("09:00")) || time1.isAfter(LocalTime.parse("12:00")) &&  time1.isBefore(LocalTime.parse("14:00")) || time1.isAfter(LocalTime.parse("18:00") )){
                System.out.println("Merci de choisir l'heure en respectant horaires d'ouverture : 09:00  ->  12:00  &  14:00  ->  18:00 ");
                heureDebut = scanner.nextLine();
                time1 = LocalTime.parse(heureDebut);
            }

            System.out.println("Saisir une periode : \n 30 min , 60 min (1 heure) , 120 min (2 heures) , 300 min (5 heures) , 720 min (toute la journee)");
            int periode = scanner.nextInt();
            scanner.nextLine();
            while ( periode!=30 && periode!=60 && periode!=120 && periode!=300 && periode!=720){
                System.out.println("Cette periode n'est pas disponible ! veuillez saisir l'un de ces periodes : \n 30 min , 60 min (1 heure) , 120 min (2 heures) , 300 min (5 heures) , 720 min (toute la journee)");
                periode = scanner.nextInt();
                scanner.nextLine();
            }

            System.out.println("saisir un jeu :");
            String jeu = scanner.nextLine();

            joueur joueur = new joueur(prenom, nom, nomPost, nomEcran, heureDebut, periode, jeu);
            if (premierJoueur) {
                joueur.setPremierJoueur(true);
                premierJoueur = false;
            }

            int prix = admin.tarifs(periode, jeu, joueur.getPremierJoueur(), nomPost, nomEcran);
            System.out.println("Veuillez payer le montant necessaire -> " + prix + " DH");
            int prixDonner = scanner.nextInt();
            scanner.nextLine();
            while (prixDonner != prix) {
                System.out.println("Ce n'est pas le payemment veuillez faite attention !!");
                prix = scanner.nextInt();
                scanner.nextLine();
            }
            System.out.println("Votre cote est : "+ joueur.getCodeJoueur());

            //joueur.afficherCodeJoueur(joueur);

            poste posteChoisi = null;
            for (poste poste : postes) {
                if ((poste.getType().equalsIgnoreCase(nomPost)) && !(poste.getEtat())) {
                    posteChoisi = poste;
                    break;
                }
            }

            ecran ecranChoisi = null;
            for (ecran ecran : ecrans) {
                if (ecran.getType().equalsIgnoreCase(nomEcran) && !(ecran.getEtat())) {
                    ecranChoisi = ecran;
                    break;
                }
            }
            if (posteChoisi == null || ecranChoisi == null) {
                admin.ajouterAuListeAttente(joueur, prixDonner);
                System.out.println("Le poste est occupee vous serez ajouter a la liste d'attente, joueur " + joueur.getPrenom() + " " + joueur.getNom() + " pour poste " + joueur.getNomPost() + " et pour ecran " + joueur.getNomEcran() + " debut a l'heure " + joueur.getHeureDebut() + " pour une periode " + periode + " pour jeu " + joueur.getJeu() + " le prix = " + prix + " est ajouter a la liste d'attente!!");
            } else {
                posteChoisi.setEtat(true);
                ecranChoisi.setEtat(true);
                admin.ajouterAuJoueur(joueur, prixDonner,posteChoisi,ecranChoisi);
                System.out.println("Le joueur " + joueur.getPrenom() + " " + joueur.getNom() + " pour poste " + joueur.getNomPost() + " et pour ecran " + joueur.getNomEcran() + " debut a l'heure " + joueur.getHeureDebut() + " pour une periode " + periode + " pour jeu " + joueur.getJeu() + " le prix " + prix + " est ajouter!!");
            }

            System.out.println("Ajouter un autre joueur oui ou non ?");
            String reponse = scanner.nextLine();
            if (reponse.equalsIgnoreCase("non") ) {
                break;
            }


        }
        admin.afficherRevenusJour();
        admin.afficherRevenusMois();
        scanner.close();
    }
}

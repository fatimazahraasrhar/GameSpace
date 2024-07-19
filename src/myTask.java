import java.util.TimerTask;

class MyTask extends TimerTask {
    private Joueur joueur;
    private Poste poste;

    public MyTask(Joueur joueur, Poste poste) {
        this.joueur = joueur;
        this.poste = poste;
    }

    public void run() {
        System.out.println("Le temps est écoulé pour le joueur " + joueur.getPrenom() + " " + joueur.getNom() + ". Le poste va s'éteindre.");
        poste.setEtat(false);
        System.out.println("Le poste " + poste.getTypePoste() + " avec écran " + poste.getTypeEcran() + " est maintenant libre.");
    }
}
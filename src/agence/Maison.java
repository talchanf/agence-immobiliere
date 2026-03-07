package agence;

import java.time.LocalDate;

public class Maison extends Bien {
    private int nbrEtages;
    private boolean jardin;

    public Maison (long reference, String adresse, double tarifMensuel, double surfaceTotale, boolean location,
            LocalDate date, int nbrEtages, boolean jardin){
                super(reference, adresse, tarifMensuel, surfaceTotale, location, date);
                this.nbrEtages = nbrEtages;
                this.jardin = jardin ;
            }
    
    public int getNbrEtages() {
        return nbrEtages;
    }

    public void setNbrEtages(int nbrEtages) {
        this.nbrEtages = nbrEtages;
    }

    public boolean isJardin() {
        return jardin;
    }

    public void setJardin(boolean jardin) {
        this.jardin = jardin;
    }
    
    public void choisirDate() {
        System.out.println("Choisir la date de début de location de la maison");
        super.choisirDate();
    }       

        @Override
    public String toString() {
    	String infojardin = jardin ? "Avec jardin" : "Sans jardin";
    	return "MAISON | " + super.toString() + " | " + nbrEtages + " étage(s) | " + infojardin;
    }
}

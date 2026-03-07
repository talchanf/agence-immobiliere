package agence;

import java.time.LocalDate;

public class Appartement extends Bien {
    private boolean meuble;
    private int nbrChambres;
    private int numEtage;
    private boolean balcon;

    public Appartement(long reference, String adresse, double tarifMensuel, double surfaceTotale, boolean location,
            LocalDate date, boolean meuble, int nbrChambres, int numEtage, boolean balcon) {
        super(reference, adresse, tarifMensuel, surfaceTotale, location, date);
        this.meuble = meuble;
        this.nbrChambres = nbrChambres;
        this.numEtage = numEtage;
        this.balcon = balcon;
    }

    @Override
    public String toString() {
    	String type = meuble ? "Meublé" : "Non meublé";
    	String ext = balcon ? "Balcon" : "sans balcon"; 
    	return "APPARTEMENT | " + super.toString() + " | " + type + " | " + nbrChambres + " chambres | Etage " + numEtage + " | " + ext;
    }
    
    public void choisirDate() {
        System.out.println("Choisir la date de début de location de l'appartement");
        super.choisirDate();
    }

    public boolean isMeuble() {
        return meuble;
    }
    public void setMeuble(boolean meuble) {
        this.meuble = meuble;
    }
    public int getNbrChambres() {
        return nbrChambres;
    }
    public void setNbrChambres(int nbrChambres) {
        this.nbrChambres = nbrChambres;
    }
    public int getNumEtage() {
        return numEtage;
    }
    public void setNumEtage(int numEtage) {
        this.numEtage = numEtage;
    }
    public boolean isBalcon() {
        return balcon;
    }
    public void setBalcon(boolean balcon) {
        this.balcon = balcon;
    }

}
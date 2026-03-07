package agence;

import java.time.LocalDate;

public class Garage extends Bien {
    private int numEmplacement;

    public Garage (long reference, String adresse, double tarifMensuel, double surfaceTotale, boolean location,
            LocalDate date, int numEmplacement) {
                super(reference, adresse, tarifMensuel, surfaceTotale, location, date);
                this.numEmplacement = numEmplacement;
            }

    public int getNumEmplacement() {
        return numEmplacement;
    }

    public void setNumEmplacement(int numEmplacement) {
        this.numEmplacement = numEmplacement;
    }
    
    public void choisirDate() {
        System.out.println("Choisir la date de début de location du garage");
        super.choisirDate();
    } 
    
    @Override
    public String toString() {
    	return "GARAGE | " + super.toString() + " | Emplacement n°" + numEmplacement;
    }

}

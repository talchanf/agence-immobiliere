package agence;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Bien {
    private long reference;  
    private String adresse;
    private double tarifMensuel; 
    private double surfaceTotale; 
    private boolean location;
    private LocalDate date;
    private Client locataire;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Bien(long reference, String adresse, double tarifMensuel, double surfaceTotale, boolean location, LocalDate date){
        this.reference = reference;
        this.adresse = adresse;
        this.tarifMensuel = tarifMensuel;
        this.surfaceTotale = surfaceTotale;
        this.location = location;
        this.date = date;
        this.locataire = null;
    }

    public LocalDate getDate() {
        return date;
    }
    
    public void setDate(LocalDate date) { 
        this.date = date;
    }

    public long getReference() {
        return reference;
    }

    public void setReference(long reference) {
        this.reference = reference;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public double getTarifMensuel() {
        return tarifMensuel;
    }

    public void setTarifMensuel(double tarifMensuel) {
        this.tarifMensuel = tarifMensuel;
    }

    public double getSurfaceTotale() {
        return surfaceTotale;
    }

    public void setSurfaceTotale(double surfaceTotale) {
        this.surfaceTotale = surfaceTotale;
    }

    public boolean isLocation() {
        return location;
    }

    public void setLocation(boolean location) {
        this.location = location;
    }


	public Client getLocataire() {
		return locataire;
	}


	public void setLocataire(Client locataire) {
		this.locataire = locataire;
	}
    
	
    public void choisirDate() {
        if (location) {  
			try (Scanner scanner = new Scanner(System.in)) {
				System.out.println("Entrez une date (jj/MM/aaaa) : ");
				if (scanner.hasNextLine()) {
					String dateStr = scanner.nextLine();
					this.date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
				}
            } catch (DateTimeParseException e) {
            	System.out.println("Erreur: Format incorrect !");
            }
        } else {
            System.out.println("Ce bien n'est pas en location.");
        }
    }
    
    @Override
    public String toString() {
    	String infoprincipale = "Réf: " + reference + " , " + adresse + " , " + surfaceTotale + "m² , " + tarifMensuel +"€";
    	if (this.location) {
    		String nomLoc = (locataire != null) ? locataire.getNom() : "personne";
    		String dateFormatee = (date != null) ? date.format(FORMATTER) : "N/A";
    		return infoprincipale + " |  Loué à " + nomLoc + " (" + dateFormatee + ")";
    	} else {
    		return infoprincipale + " | LIBRE ";
    	}
    }

}

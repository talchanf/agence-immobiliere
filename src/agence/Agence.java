package agence;

import java.io.File;
import java.io.PrintWriter;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Agence {
	private List<Client> listeClients;
	private List<Bien> listeBiens;

	public Agence() {
		this.listeClients = new ArrayList<>();
		this.listeBiens = new ArrayList<>();
	}	
		
	public static void main (String[] args) {
		Agence monAgence = new Agence();
		
		monAgence.ajouterClient(new Client("Dupont", "Jean", "jean@gmail.fr", "0645123689"));
		monAgence.ajouterClient(new Client("Berger", "Lise", "lise@gmail.fr", "0645789562"));
		monAgence.ajouterClient(new Client("Simon", "Sara", "sara@gmail.fr", "0678495867"));
		
		monAgence.ajouterBien(new Maison(1, "2 rue de la gare", 1200.0, 145.5, false, null, 2, true));
		monAgence.ajouterBien(new Maison(2, "15 rue de la République", 2500.0, 275.5, false, null, 1, true));
		monAgence.ajouterBien(new Maison(3, "12 impasse Victor Hugo", 1150.0, 135.5, false, null, 1, true));
		
		monAgence.ajouterBien(new Appartement(4, "5 avenue Jean Jaurès", 750.0, 75.9, false, null, true, 2, 3, true));
		monAgence.ajouterBien(new Appartement(5, "11 rue Jean Moulin", 900.0, 152.3, false, null, false, 3, 1, false));
		monAgence.ajouterBien(new Appartement(6, "13 avenue Victoire", 655.0, 135.7, false, null, false, 3, 2, false));
		
		monAgence.ajouterBien(new Garage(7, "Parking Centre-Ville", 150.0, 25.0, false, null, 101));
		monAgence.ajouterBien(new Garage(8, "2 impasse de la République", 120.0, 15.0, false, null, 22));
		monAgence.ajouterBien(new Garage(9, "12 avenue Jean Jaurès", 170.0, 30.0, false, null, 35));
		
		monAgence.louerBien(1, "Dupont", "12/11/2025");
		
		monAgence.trierClients();
		monAgence.creationFichierClient();
		
		monAgence.afficher();
		
		new Fenetre(monAgence);
	}
			
	public void ajouterClient(Client c) {
		listeClients.add(c);
	}

	public void ajouterBien(Bien b) {
		listeBiens.add(b);
	}
	
	public void trierClients() {
		Collections.sort(listeClients, new Comparator<Client>() {
			@Override
			public int compare(Client c1, Client c2) {
				return c1.getNom().compareToIgnoreCase(c2.getNom());
			}
		});
	}
	
	public void afficher() {
		System.out.println("--------------Les clients et leurs informations-------------");
		for (Client c : listeClients) {
			System.out.println(c);
		}

		System.out.println("--------------Listes des Biens Immobiliers-------------");
		for (Bien b : listeBiens) {
			System.out.println(b);
		}
	}
	
	public void creationFichierClient() {
		this.trierClients();
		File f = new File("fichierClient.csv");
		
		try (PrintWriter writer = new PrintWriter(f)) {
			writer.println("Nom; Prenom; Email; Telephone");
			for (Client c :listeClients) {
				writer.println(c.getNom() + "; " + c.getPrenom() +"; " + c.getEmail() + "; " + c.getTelephone());
			}
			System.out.println("Fichier crée avec succès !");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public boolean louerBien(long ref, String nomClient, String dateLocation) {
		Bien bienTrouve = LireBien(ref);
		Client clientTrouve = LireClient(nomClient);
		
		if (bienTrouve != null && bienTrouve.isLocation()) {
			System.out.println("Erreur: Le bien n°" + ref + " est déjà loué !");
			return false;
		}
		
		if (bienTrouve != null && clientTrouve != null) {
			bienTrouve.setLocation(true);
			bienTrouve.setLocataire(clientTrouve);
			try {
				bienTrouve.setDate(LocalDate.parse(dateLocation, DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				System.out.println("Succès: Le bien n°" + ref + " est loué à " + nomClient);
				return true;
			} catch (Exception e) {
				System.out.println("Erreur: format date !");
				return false;
			}
		}
		System.out.println("Erreur: Bien ou Client introuvable !");
		return false;
	}
	
	public List<Client> getListeClients() {
		return listeClients;
	}
	
	public List<Bien> getListeBiens() {
		return listeBiens;
	}

	public Client LireClient (String nomClient) {
		for (Client c : listeClients) {
			if (c.getNom().equalsIgnoreCase (nomClient)) {
				return c;
			}
		}
		return null;
	}
	
	public Bien LireBien (long reference) {
		for (Bien b : listeBiens) {
			if (b.getReference() == reference) {
				return b;
			}
		}
		return null;
	}
	
	public boolean modifierClient (String nomClient, String nouveauNom, String nouveauPrenom, String nouveauEmail, String nouveauTel) {
		Client c = LireClient (nomClient);
		if (c != null) {
			c.setNom(nouveauNom);
			c.setPrenom(nouveauPrenom);
			c.setEmail(nouveauEmail);
			c.setTelephone(nouveauTel);
			System.out.println("Client modifié avec succès !");
			return true;
		} else {
			System.out.println("Erreur : Client introuvable !");
			return false;
		}
	}
	
	public boolean supprimerClient (String nomClient) {
		Client c = LireClient (nomClient);
		if (c != null) {
			listeClients.remove(c);
			System.out.println("Client supprimé avec succès !");
			return true;
		} else {
			System.out.println("Erreur: Client introuvable !");
			return false;
		}
	}
	
	public boolean modifierBien (long reference, String nouvelleAdresse, double nouveauTarif, double nouvelleSurface) {
		Bien b = LireBien (reference);
		if (b != null) {
			b.setAdresse(nouvelleAdresse);
			b.setTarifMensuel(nouveauTarif);
			b.setSurfaceTotale(nouvelleSurface);
			System.out.println("Bien modifié avec succès !");
			return true;
		} else {
			System.out.println("Erreur : Bien introuvable !");
			return false;	
		}
	}
	
	public boolean supprimerBien (long reference) {
		Bien b = LireBien (reference);
		if (b != null) {
			if (!b.isLocation()) {
				listeBiens.remove(b);
				System.out.println("Bien supprimé avec succès !");
				return true;
		} else {
			System.out.println("Erreur: Impossible de supprimer bien loué !");
			return false;
		}
		} else {
			System.out.println("Erreur : Bien introuvable !");
			return false;
		}
	}
	
	public List<Bien> rechercherParSurface(double surfaceMin, double surfaceMax) {
	    List<Bien> resultats = new ArrayList<>();
	    for (Bien b : listeBiens) {
	        if (b.getSurfaceTotale() >= surfaceMin && b.getSurfaceTotale() <= surfaceMax) {
	            resultats.add(b);
	        }
	    }
	    
	    if (resultats.isEmpty()) {
	        System.out.println("Aucun bien trouvé pour cette surface !");
	    } else {
	        System.out.println("--- Biens trouvés (surface entre " + surfaceMin + " et " + surfaceMax + " m²) ---");
	        for (Bien b : resultats) {
	            System.out.println(b);
	        }
	    }
	    
	    return resultats;
	}
	
	
	public void afficherBienLoues() {
		System.out.println("--------------Biens loués avec la date de location-------------");
		boolean trouve = false;
		for (Bien b : listeBiens) {
			if (b.isLocation()) {
				System.out.println(b);
				trouve = true;
			}
		}
		if (!trouve) {
			System.out.println("Il n'y a pas de bien loué !");
		}
	}

	
	public void afficherBienNonLoues() {
		System.out.println("--------------Biens disponibles pour la location-------------");
		boolean trouve = false;
		for (Bien b : listeBiens) {
			if (!b.isLocation()) {
				System.out.println(b);
				trouve = true;
			}
		}
		if (!trouve) {
			System.out.println("Tous les biens sont loués !");
		}
	}
	
	
	public boolean supprimerLocation (long reference) {
		Bien b = LireBien (reference);
		if (b != null && b.isLocation()) {
			b.setLocation(false);
			b.setLocataire(null);
			b.setDate(null);
			System.out.println("La location du bien n°" + reference + " a été supprimée !");
			return true;
		} else if (b != null && !b.isLocation()){
			System.out.println("Erreur : Le bien n'est pas loué !");
			return false;	
		} else {
			System.out.println("Erreur : Bien introuvable !");
			return false;
		}
	}
	
	
}
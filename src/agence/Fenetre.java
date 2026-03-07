package agence;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.*;

public class Fenetre extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private Agence agence;
	
	JMenuItem itemAjouterClient;
	JMenuItem itemModifierClient;
	JMenuItem itemSupprimerClient;
	JMenuItem itemAjouterBien;
	JMenuItem itemModifierBien;
	JMenuItem itemSupprimerBien;
	JMenuItem itemBiensLoues;
	JMenuItem itemBiensNonLoues;
	JMenuItem itemAjouterLocation;
	JMenuItem itemSupprimerLocation;
	JTextField textNomClient;
	JTextField textNomBien;
	JTextField champRechercheClient;
	JTextField champRechercheBien;
	DefaultListModel<String> modelClients;
	DefaultListModel<String> modelBiens;
	JList<String> listeClients;
	JList<String> listeBiens;
	Object bienRecherche;

	public Fenetre(Agence agence) {
		this.agence = agence;
		
		setTitle("Agence");
		setSize(800, 650);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		modelClients = new DefaultListModel<>();
		modelBiens = new DefaultListModel<>();
		listeClients = new JList<>(modelClients);
		listeBiens = new JList<>(modelBiens);
		champRechercheClient = new JTextField(20);
		champRechercheBien = new JTextField(20);
		
		setJMenuBar(construireMenu());
		setContentPane(construirePanel());
		
		rafraichirListes();
		setVisible(true);
	}

	private JMenuBar construireMenu() {
		JMenuBar bar = new JMenuBar();
		JMenu menuClient = new JMenu("Client");
		JMenu menuBien = new JMenu("Bien");
		JMenu menuLocation = new JMenu("Location");

		bar.add(menuClient);
		bar.add(menuBien);
		bar.add(menuLocation);

		itemAjouterClient = new JMenuItem("Ajouter");
		itemAjouterClient.addActionListener(this);
		itemModifierClient = new JMenuItem("Modifier");
		itemModifierClient.addActionListener(this);
		itemSupprimerClient = new JMenuItem("Supprimer");
		itemSupprimerClient.addActionListener(this);

		menuClient.add(itemAjouterClient);
		menuClient.add(itemModifierClient);
		menuClient.add(itemSupprimerClient);
		
		itemAjouterBien = new JMenuItem("Ajouter");
		itemAjouterBien.addActionListener(this);
		itemModifierBien = new JMenuItem("Modifier");
		itemModifierBien.addActionListener(this);
		itemSupprimerBien = new JMenuItem("Supprimer");
		itemSupprimerBien.addActionListener(this);
		itemBiensLoues = new JMenuItem ("Afficher biens loués");
		itemBiensLoues.addActionListener(this);
		itemBiensNonLoues = new JMenuItem ("Afficher biens non loués");
		itemBiensNonLoues.addActionListener(this);

		menuBien.add(itemAjouterBien);
		menuBien.add(itemModifierBien);
		menuBien.add(itemSupprimerBien);
		menuBien.add(itemBiensLoues);
		menuBien.add(itemBiensNonLoues);

		itemAjouterLocation = new JMenuItem("Ajouter");
		itemAjouterLocation.addActionListener(this);
		itemSupprimerLocation = new JMenuItem("Supprimer");
		itemSupprimerLocation.addActionListener(this);

		menuLocation.add(itemAjouterLocation);
		menuLocation.add(itemSupprimerLocation);

		return bar;
	}

	private JPanel construirePanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2));
		JPanel panelGauche = new JPanel();
		panelGauche.setLayout(new BoxLayout(panelGauche, BoxLayout.PAGE_AXIS));
		JPanel panelDroit = new JPanel();
		panelDroit.setLayout(new BoxLayout(panelDroit, BoxLayout.PAGE_AXIS));
		panelDroit.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		panelGauche.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
		panel.add(panelGauche);
		panel.add(panelDroit);
		
		JLabel labelRechercher = new JLabel("RECHERCHER UN CLIENT");
		labelRechercher.setFont(new Font("Arial", Font.BOLD, 14));
		labelRechercher.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel labelNomClient = new JLabel("Nom du client :");
		labelNomClient.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		champRechercheClient.setMaximumSize(new Dimension(250, 30));
		champRechercheClient.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton btnRechercherClient = new JButton("Rechercher");
		btnRechercherClient.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRechercherClient.addActionListener(e -> rechercherClient());
		
		JButton btnLouer = new JButton("Louer le bien sélectionné");
		btnLouer.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnLouer.setBackground(new Color(173, 216, 230));
		btnLouer.addActionListener(e -> procederLocation());
		
		
		JButton btnResetClients = new JButton ("Tout afficher");
		btnResetClients.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnResetClients.addActionListener(e-> rafraichirListes());

		JButton btnResetBiens = new JButton ("Tout afficher");
		btnResetBiens.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnResetBiens.addActionListener(e-> rafraichirListes());
		
		JButton btnExporter = new JButton ("Exporter Clients (CSV)");
		btnExporter.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnExporter.setBackground(new Color(200, 230, 201));
		btnExporter.addActionListener(e-> {
			agence.creationFichierClient();
			JOptionPane.showMessageDialog(this, "Fichier CSV crée !");
		});

		for (Client c : agence.getListeClients()) {
			modelClients.addElement(c.toString());
		}

		JScrollPane scrollPaneClients = new JScrollPane(listeClients);
		scrollPaneClients.setPreferredSize(new Dimension(250, 300));
		scrollPaneClients.setMaximumSize(new Dimension(350, 300));
		scrollPaneClients.setAlignmentX(Component.CENTER_ALIGNMENT);

		panelGauche.add(Box.createRigidArea(new Dimension(0, 10)));
		panelGauche.add(labelRechercher);
		panelGauche.add(Box.createRigidArea(new Dimension(0, 15)));
		panelGauche.add(labelNomClient);
		panelGauche.add(Box.createRigidArea(new Dimension(0, 5)));
		panelGauche.add(champRechercheClient);
		panelGauche.add(Box.createRigidArea(new Dimension(0, 10)));
		panelGauche.add(btnRechercherClient);
		panelGauche.add(Box.createRigidArea(new Dimension(0, 15)));
		panelGauche.add(scrollPaneClients);
		panelGauche.add(Box.createRigidArea(new Dimension(0, 10)));
		panelGauche.add(btnResetClients);
		panelGauche.add(Box.createRigidArea(new Dimension(0, 5)));
		panelGauche.add(btnExporter);
		
		JLabel labelRechercheBien = new JLabel("RECHERCHER UN BIEN");
		labelRechercheBien.setFont(new Font("Arial", Font.BOLD, 14));
		labelRechercheBien.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		JLabel labelRefBien = new JLabel("Surface totale du bien :");
		labelRefBien.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		champRechercheBien.setMaximumSize(new Dimension(250, 30));
		champRechercheBien.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton btnRechercherBien = new JButton("Rechercher");
		btnRechercherBien.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnRechercherBien.addActionListener(e -> rechercherBien());

		for (Bien b : agence.getListeBiens()) {
			modelBiens.addElement(b.toString());
		}

		JScrollPane scrollPaneBiens = new JScrollPane(listeBiens);
		scrollPaneBiens.setPreferredSize(new Dimension(250, 300));
		scrollPaneBiens.setMaximumSize(new Dimension(350, 300));
		scrollPaneBiens.setAlignmentX(Component.CENTER_ALIGNMENT);

		panelDroit.add(Box.createRigidArea(new Dimension(0, 10)));
		panelDroit.add(labelRechercheBien);
		panelDroit.add(Box.createRigidArea(new Dimension(0, 15)));
		panelDroit.add(labelRefBien);
		panelDroit.add(Box.createRigidArea(new Dimension(0, 5)));
		panelDroit.add(champRechercheBien);
		panelDroit.add(Box.createRigidArea(new Dimension(0, 10)));
		panelDroit.add(btnRechercherBien);
		panelDroit.add(Box.createRigidArea(new Dimension(0, 15)));
		panelDroit.add(scrollPaneBiens);
		panelDroit.add(Box.createRigidArea(new Dimension(0, 10)));
		panelDroit.add(btnResetBiens);		
		panelDroit.add(Box.createRigidArea(new Dimension(0, 5)));
		panelDroit.add(btnLouer);

		return panel;
	}

	private void rechercherClient() {
		String recherche = champRechercheClient.getText().trim().toLowerCase();

		if (recherche.isEmpty()) {
			rafraichirListes();
			return;
		}

		modelClients.clear();
		boolean trouve = false;

		for (Client c : agence.getListeClients()) {
			if (c.getNom().toLowerCase().contains(recherche) || 
				c.getPrenom().toLowerCase().contains(recherche)) {
				modelClients.addElement(c.toString());
				trouve = true;
			}
		}

		if (!trouve) {
			JOptionPane.showMessageDialog(this, 
				"Aucun client trouvé avec ce nom.", 
				"Recherche", 
				JOptionPane.INFORMATION_MESSAGE);
			rafraichirListes();
		}
	}

	private void rechercherBien() {
    	String recherche = champRechercheBien.getText().trim();

    	if (recherche.isEmpty()) {
        	rafraichirListes();
        	return;
    	}

    	try {
        	double surfaceEntree = Double.parseDouble(recherche);
        	modelBiens.clear();
        	boolean trouve = false;

        	for (Bien b : agence.getListeBiens()) {
            	double surfaceBien = b.getSurfaceTotale();

            	if (surfaceBien >= surfaceEntree && surfaceBien < surfaceEntree + 1) {
                	modelBiens.addElement(b.toString());
                	trouve = true;
            	}
        	}

        	if (!trouve) {
            	JOptionPane.showMessageDialog(this,
                	"Aucun bien trouvé pour cette surface.",
                	"Recherche",
                	JOptionPane.INFORMATION_MESSAGE);
            	rafraichirListes();
        	}

    		} catch (NumberFormatException e) {
        		JOptionPane.showMessageDialog(this,
            		"Veuillez entrer uniquement un nombre (ex : 50).",
            		"Erreur",
            		JOptionPane.ERROR_MESSAGE);
    		}
		}

	private void afficherFiltreBiens(boolean seulementLoues) {
	    modelBiens.clear();
	    for (Bien b : agence.getListeBiens()) {
	        if (b.isLocation() == seulementLoues) {
	            modelBiens.addElement(b.toString());
	        }
	    }
	    
	    if (modelBiens.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Aucun bien ne correspond à ce critère.");
	        rafraichirListes(); 
	    }
	}

	public void rafraichirListes() {
		modelClients.clear();
		for (Client c : agence.getListeClients()) {
			modelClients.addElement(c.toString());
		}

		modelBiens.clear();
		for (Bien b : agence.getListeBiens()) {
			modelBiens.addElement(b.toString());
		}

		champRechercheClient.setText("");
		champRechercheBien.setText("");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().equals(itemAjouterClient)) {
			ajouterClient();
		}
		
		if (e.getSource().equals(itemModifierClient)) {
			modifierClient();
		}

		if (e.getSource().equals(itemSupprimerClient)) {
			supprimerClient();
		}
		
		if (e.getSource().equals(itemAjouterBien)) {
			ajouterBien();
		}
		
		if (e.getSource().equals(itemModifierBien)) {
			modifierBien();
		}

		if (e.getSource().equals(itemSupprimerBien)) {
			supprimerBien();
		}
		
		if (e.getSource().equals(itemBiensLoues)) {
			afficherFiltreBiens(true);
		}
		
		if (e.getSource().equals(itemBiensNonLoues)) {
			afficherFiltreBiens(false);
		}

		if (e.getSource().equals(itemAjouterLocation)) {
    		ajouterLocation();
		}

		if (e.getSource().equals(itemSupprimerLocation)) {
			supprimerLocation();
		}
	}
	
	private void ajouterClient() {
		JTextField champNom = new JTextField();
		JTextField champPrenom = new JTextField();
		JTextField champMail = new JTextField();
		JTextField champTelephone = new JTextField();
		
		Object[] message = {
			"Nom:", champNom,
			"Prénom:", champPrenom,
			"Email:", champMail,
			"Téléphone:", champTelephone
		};
		
		int option = JOptionPane.showConfirmDialog(this, message, "Ajouter un client", JOptionPane.OK_CANCEL_OPTION);
		
		if (option == JOptionPane.OK_OPTION) {
			try {
				String nom = champNom.getText().trim();
				String prenom = champPrenom.getText().trim();
				String mail = champMail.getText().trim();
				String telephone = champTelephone.getText().trim();
				
				if (nom.isEmpty() || prenom.isEmpty() || mail.isEmpty()) {
					JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires !", "Erreur", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				Client nouveauClient = new Client(nom, prenom, mail, telephone);
				agence.ajouterClient(nouveauClient);
				rafraichirListes();
				
				
				JOptionPane.showMessageDialog(this, "Client ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Le numéro de téléphone doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void modifierClient() {
		String selection = listeClients.getSelectedValue();
		
		if (selection == null) {
			JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client dans la liste.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		String nomClient = selection.split(" ")[0];
		
		Client clientATrouver = agence.LireClient(nomClient);
		if (clientATrouver != null) {
			JTextField champNom = new JTextField(clientATrouver.getNom());
			JTextField champPrenom= new JTextField(clientATrouver.getPrenom());
			JTextField champMail = new JTextField(clientATrouver.getEmail());
			JTextField champTelephone = new JTextField(clientATrouver.getTelephone());
		
		
		Object[] message = {
			"Nouveau Nom:", champNom,
			"Nouveau Prenom:", champPrenom,	
			"Nouveau Email:", champMail,
			"Nouveau Téléphone:", champTelephone
		};
		
		int option = JOptionPane.showConfirmDialog(this, message, "Modifier le client " + nomClient, JOptionPane.OK_CANCEL_OPTION);
		
		if (option == JOptionPane.OK_OPTION) {
				clientATrouver.setNom(champNom.getText().trim());
				clientATrouver.setPrenom(champPrenom.getText().trim());
				clientATrouver.setEmail(champMail.getText().trim());
				clientATrouver.setTelephone(champTelephone.getText().trim());
				rafraichirListes();
				
				JOptionPane.showMessageDialog(this, "Client modifié avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
			} 
		} else {
				JOptionPane.showMessageDialog(this, "Le numéro de téléphone doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
	
	
	private void supprimerClient() {
		String selection = listeClients.getSelectedValue();
		
		if (selection == null) {
			JOptionPane.showMessageDialog(this, "Veuillez sélectionner un client dans la liste.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		String nomClient = selection.split(" ")[0];
		
		int confirmation = JOptionPane.showConfirmDialog(this, 
			"Êtes-vous sûr de vouloir supprimer le client " + nomClient + " ?", 
			"Confirmation", 
			JOptionPane.YES_NO_OPTION);
		
		if (confirmation == JOptionPane.YES_OPTION) {
			Client clientASupprimer = null;
			for (Client c : agence.getListeClients()) {
				if (c.getNom().equalsIgnoreCase(nomClient)) {
					clientASupprimer = c;
					break;
				}
			}
			
			if (clientASupprimer != null) {
				agence.getListeClients().remove(clientASupprimer);
				rafraichirListes();
				JOptionPane.showMessageDialog(this, "Client supprimé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	private void ajouterBien() {
		String [] types = {"Appartement", "Maison", "Garage"};
		String typeChoisi = (String) JOptionPane.showInputDialog(this, "Quel type de bien souhaitez-vous ajouter ?", "Ajout", JOptionPane.QUESTION_MESSAGE, null, types, types[0]);
		
		if (typeChoisi == null) return;
		
		
		JTextField champReference = new JTextField();
		JTextField champAdresse = new JTextField();
		JTextField champTarifMensuel = new JTextField();
		JTextField champSurfaceTotale = new JTextField();
		JTextField champLocation = new JTextField("false");
		JTextField champDate = new JTextField("dd/MM/yyyy");
		
		Object[] message = {
			"Reference:", champReference,
			"Adresse:", champAdresse,
			"TarifMensuel:", champTarifMensuel,
			"SurfaceTotale:", champSurfaceTotale,
			"Location:", champLocation,
			"Date:", champDate
		};
		
		int option = JOptionPane.showConfirmDialog(this, message, "Ajouter un bien", JOptionPane.OK_CANCEL_OPTION);
		
		if (option == JOptionPane.OK_OPTION) {
			try {
				if (champReference.getText().isEmpty() || champAdresse.getText().isEmpty()) {
					JOptionPane.showMessageDialog(this, "Tout les champs sont obligatoires !");
					return;
				}
				long reference = Long.parseLong(champReference.getText().trim());
				String adresse = champAdresse.getText().trim();
				double tarifMensuel = Double.parseDouble(champTarifMensuel.getText().trim());
				double surfaceTotale = Double.parseDouble(champSurfaceTotale.getText().trim());
				boolean location = Boolean.parseBoolean(champLocation.getText().trim());
				LocalDate date = null;
					if (!champDate.getText().equals("dd/MM/yyyy")) {
						date =LocalDate.parse(champDate.getText().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
					}
						
				
				
				Bien nouveauBien; 
				if (typeChoisi.equals("Maison")) {
					nouveauBien = new Maison (reference, adresse, tarifMensuel, surfaceTotale, location, date, 0, false);
				} else if (typeChoisi.equals("Appartement")) {
					nouveauBien = new Appartement (reference, adresse, tarifMensuel, surfaceTotale, location, date, false, 0, 0, false);
				} else {
					nouveauBien = new Garage (reference, adresse, tarifMensuel, surfaceTotale, location, date, 0);
				}
				agence.ajouterBien(nouveauBien);
				rafraichirListes();
				
				JOptionPane.showMessageDialog(this, "Bien ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "La référence/le tarif mensuel/la surface totale doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void modifierBien() {
		String selection = listeBiens.getSelectedValue();
		
		if (selection == null) {
			JOptionPane.showMessageDialog(this, "Veuillez sélectionner un bien dans la liste.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		long reference = Long.parseLong(selection.split(",")[0].replaceAll("[^0-9]", ""));
		
		
		Bien bienATrouver = agence.LireBien(reference);
		if (bienATrouver == null) return;
		
		JTextField champAdresse = new JTextField(bienATrouver.getAdresse());
		JTextField champTarifMensuel = new JTextField(String.valueOf(bienATrouver.getTarifMensuel()));
		JTextField champSurfaceTotale = new JTextField(String.valueOf(bienATrouver.getSurfaceTotale()));
		JTextField champLocation = new JTextField(String.valueOf(bienATrouver.isLocation())); 
		JTextField champDate = new JTextField(bienATrouver.getDate() != null ? bienATrouver.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "dd/MM/yyyy");
		
		Object[] message = {
			"Nouvelle adresse:", champAdresse,
			"Nouveau Tarif mensuel:", champTarifMensuel,
			"Nouveau Surface totale:", champSurfaceTotale,
			"Loacation ?:", champLocation,
			"Début date location:", champDate
		};
		
		int option = JOptionPane.showConfirmDialog(this, message, "Modifier le bien n° " + reference, JOptionPane.OK_CANCEL_OPTION);
		
		if (option == JOptionPane.OK_OPTION) {
			try {
				bienATrouver.setAdresse(champAdresse.getText().trim());
				bienATrouver.setTarifMensuel(Double.parseDouble(champTarifMensuel.getText().trim()));
				bienATrouver.setSurfaceTotale(Double.parseDouble(champSurfaceTotale.getText().trim()));
				bienATrouver.setLocation(Boolean.parseBoolean(champLocation.getText().trim()));
				if (!champDate.getText().equals("dd/MM/yyyy")) {
					bienATrouver.setDate(LocalDate.parse(champDate.getText().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
				}
				rafraichirListes();
				
				JOptionPane.showMessageDialog(this, "Bien modifié avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "La référence/le tarif mensuel/la surface totale doit être un nombre !", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void supprimerBien() {
		String selection = listeBiens.getSelectedValue();
		
		if (selection == null) {
			JOptionPane.showMessageDialog(this, "Veuillez sélectionner un bien dans la liste.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		String refStr = selection.split(":")[1].split(" ")[1];
		
		int confirmation = JOptionPane.showConfirmDialog(this, 
			"Êtes-vous sûr de vouloir supprimer le bien n°" + refStr + " ?", 
			"Confirmation", 
			JOptionPane.YES_NO_OPTION);
		
		if (confirmation == JOptionPane.YES_OPTION) {
			try {
				long reference = Long.parseLong(refStr);
				Bien bienASupprimer = null;
				
				for (Bien b : agence.getListeBiens()) {
					if (b.getReference() == reference) {
						bienASupprimer = b;
						break;
					}
				}
				
				if (bienASupprimer != null) {
					agence.getListeBiens().remove(bienASupprimer);
					rafraichirListes();
					JOptionPane.showMessageDialog(this, "Bien supprimé avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NumberFormatException ex) {
				JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.", "Erreur", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void procederLocation() {
	    String selection = listeBiens.getSelectedValue();
	    if (selection == null) {
	        JOptionPane.showMessageDialog(this, "Sélectionnez un bien dans la liste !");
	        return;
	    }

	    try {
	        String refStr = selection.split(":")[1].trim().split(" ")[0];
	        long ref = Long.parseLong(refStr);
	        
	        Bien b = agence.LireBien(ref);
	        if (b != null && b.isLocation()) {
	        	JOptionPane.showMessageDialog(this,  "Ce bien est déjà loué par " + b.getLocataire().getNom(), "Bien indisponible", JOptionPane.WARNING_MESSAGE);
	        	return;
	        }

	        JTextField champNomClient = new JTextField();
	        JTextField champDate = new JTextField(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

	        Object[] message = {
	            "Nom du client :", champNomClient,
	            "Date (dd/MM/yyyy) :", champDate
	        };

	        int option = JOptionPane.showConfirmDialog(this, message, "Louer le bien n°" + ref, JOptionPane.OK_CANCEL_OPTION);

	        if (option == JOptionPane.OK_OPTION) {
	            boolean succes = agence.louerBien(ref, champNomClient.getText().trim(), champDate.getText().trim());
	            if (succes) {
	                rafraichirListes();
	                JOptionPane.showMessageDialog(this, "Location enregistrée avec succès !");
	            } else {
	                JOptionPane.showMessageDialog(this, "Erreur : Client introuvable ou mauvais format de date.", "Erreur", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    } catch (Exception ex) {
	        JOptionPane.showMessageDialog(this, "Erreur lors de la récupération de la référence.");
	    }
	}
	
	private void ajouterLocation() {

    	JTextField champReference = new JTextField();
    	JTextField champNomClient = new JTextField();
    	JTextField champDate = new JTextField(
        	LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    	);

    	Object[] message = {
        	"Référence du bien :", champReference,
        	"Nom du client :", champNomClient,
        	"Date (dd/MM/yyyy) :", champDate
    	};

    	int option = JOptionPane.showConfirmDialog(
        	this,
        	message,
        	"Ajouter une location",
        	JOptionPane.OK_CANCEL_OPTION
    	);

    	if (option == JOptionPane.OK_OPTION) {
        	try {
            	long reference = Long.parseLong(champReference.getText().trim());
            	String nomClient = champNomClient.getText().trim();
            	String date = champDate.getText().trim();

            	if (nomClient.isEmpty()) {
                	JOptionPane.showMessageDialog(this,
                    	"Le nom du client est obligatoire.",
                    	"Erreur",
                    	JOptionPane.ERROR_MESSAGE);
                	return;
            	}

            	Bien bien = agence.LireBien(reference);
            	if (bien == null) {
                	JOptionPane.showMessageDialog(this,
                    	"Bien introuvable.",
                    	"Erreur",
                    	JOptionPane.ERROR_MESSAGE);
                	return;
            	}

            	if (bien.isLocation()) {
                	JOptionPane.showMessageDialog(this,
                    	"Ce bien est déjà loué.",
                    	"Erreur",
                    	JOptionPane.WARNING_MESSAGE);
                	return;
            	}

            	boolean succes = agence.louerBien(reference, nomClient, date);

            	if (succes) {
                	rafraichirListes();
                	JOptionPane.showMessageDialog(this,
                    	"Location ajoutée avec succès !",
                    	"Succès",
                    	JOptionPane.INFORMATION_MESSAGE);
            	} else {
                	JOptionPane.showMessageDialog(this,
                    	"Client introuvable ou date invalide.",
                    	"Erreur",
                    	JOptionPane.ERROR_MESSAGE);
            	}

        	} catch (NumberFormatException e) {
            	JOptionPane.showMessageDialog(this,
                	"La référence doit être un nombre.",
                	"Erreur",
                	JOptionPane.ERROR_MESSAGE);
        	}
    	}
	}

	private void supprimerLocation() {
	    String selection = listeBiens.getSelectedValue();
	    
	    if (selection == null) {
	        JOptionPane.showMessageDialog(this, "Veuillez sélectionner un bien dans la liste.", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
	        return;
	    }
	    
	    try {
	        String refStr = selection.split(":")[1].split(" ")[1];
	        long reference = Long.parseLong(refStr);
	        
	        Bien bienTrouve = agence.LireBien(reference);
	        
	        if (bienTrouve == null) {
	            JOptionPane.showMessageDialog(this, "Bien introuvable !", "Erreur", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	        
	        if (!bienTrouve.isLocation()) {
	            JOptionPane.showMessageDialog(this, 
	                "Ce bien n'est pas actuellement loué.", 
	                "Pas de location", 
	                JOptionPane.INFORMATION_MESSAGE);
	            return;
	        }
	        
	        String nomLocataire = bienTrouve.getLocataire() != null ? bienTrouve.getLocataire().getNom() : "Inconnu";
	        String dateDebut = bienTrouve.getDate() != null ? bienTrouve.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) : "Inconnue";
	        
	        int confirmation = JOptionPane.showConfirmDialog(this, 
	            "Supprimer la location du bien n°" + reference + " ?\n" +
	            "Locataire : " + nomLocataire + "\n" +
	            "Depuis le : " + dateDebut, 
	            "Confirmation", 
	            JOptionPane.YES_NO_OPTION);
	        
	        if (confirmation == JOptionPane.YES_OPTION) {
	            bienTrouve.setLocation(false);
	            bienTrouve.setLocataire(null);
	            bienTrouve.setDate(null);
	            
	            rafraichirListes();
	            JOptionPane.showMessageDialog(this, 
	                "Location supprimée avec succès !\nLe bien est maintenant disponible.", 
	                "Succès", 
	                JOptionPane.INFORMATION_MESSAGE);
	        }
	        
	    } catch (NumberFormatException ex) {
	        JOptionPane.showMessageDialog(this, 
	            "Erreur lors de la récupération de la référence.", 
	            "Erreur", 
	            JOptionPane.ERROR_MESSAGE);
	    }
	}

}
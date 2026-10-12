# Agence immobilière (Java)

Logiciel de gestion pour une agence immobilière : clients, biens à louer et locations, avec une interface graphique. Projet de L3, cours d'algorithmie et programmation, réalisé en binôme. Le rapport du projet est dans le dépôt (`Rapport_projet_FURNON_SIMSEK.pdf`).

## Fonctionnalités

- **Clients** : ajouter, modifier, supprimer, rechercher par nom
- **Biens** : ajouter, modifier, supprimer, rechercher par surface (minimum et maximum)
- **Locations** : louer un bien à un client avec une date, supprimer une location, afficher les biens loués et les biens disponibles
- Tri des clients et export de la liste dans un fichier CSV (`fichierClient.csv`)

## Conception

Programmation orientée objet avec héritage :

```
Bien (référence, adresse, tarif mensuel, surface, location)
  |- Appartement (meublé, chambres, étage, balcon)
  |- Maison (étages, jardin)
  |- Garage (numéro d'emplacement)
Client (nom, prénom, email, téléphone)
Agence (liste de clients, liste de biens, opérations de gestion)
Fenetre (interface Swing avec menus Client, Bien et Location)
```

## Technologies

Java, Swing, collections (`List`), fichiers CSV, projet Eclipse.

## Lancer le projet

1. Importer le dossier dans Eclipse (*File > Import > Existing Projects into Workspace*).
2. Exécuter la classe `agence.Agence` (méthode `main`). Elle crée quelques clients et biens d'exemple, puis ouvre la fenêtre.

Les données d'exemple (noms, adresses, téléphones) sont fictives.

## Ce que j'ai travaillé

- Modéliser des objets avec héritage et polymorphisme (`toString`, `choisirDate`).
- Séparer la logique métier (`Agence`) de l'interface (`Fenetre`).
- Gérer des collections et des fichiers.

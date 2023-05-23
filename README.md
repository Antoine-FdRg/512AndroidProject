
# 512AndroidProject
Dans le cadre de la ressource R411 Développement d'application mobile du BUT Informatique nous avons développé, en équipe de 3, une application android simple mais intégrant tous les points (en même plus) que nous avions vu en cours.

## Contraintes
- Plusieurs activités avec un  singleton ou en utilisant l'interface Parcelable
- Au moins un adapater pour afficher une liste
- Au moins un widgets (ex : calendrier)
- Au moins une animation
- Utilisation d'un fichier JSON
- Utilisation d'un web service public ou personnel
- Utilisation d'un technologie non vu (géolocalisation, Bluetooth, WIFI, ...)

## Cahier des charges
### **1. Finalité du projet**

La finalité du projet 512APP est la création d’un application de vente (fictive) de voiture.

### **2. Objectifs généraux du projet**

- O1 : Mettre en place la gestion du projet

	 - E1.1 - Lister les tâches à faire

	 - E1.2 - Répartir les tâches entre les différents membres

- O2 : Faire la conception de l’application

	 - E2.1 - Choisir une implémentation pour chaque contrainte

	 - E2.2 - Réaliser une maquette de l’application

- O3 : Réaliser l’application

	 - E3.2 - Développer des vues fidèles aux maquettes

	 - E3.3 - Implémenter les modèles de données de l’application

	 - E3.3 - Mettre en place une API Rest

	 - E3.4 - Développer l’intermédiaire d’échange entre les vues et les modèles (controleur)

### 3. Fonctionnalités du projet

1.  Parcourir la liste des voitures proposées
2.  Commander une voiture en choisissant en entrant quelques données personnelles, le lieu et la date souhaités pour la livraison
3.  Parcourir la liste des commandes précédemment effectuées

### 4. Implémentation de chaque contrainte

-   Singleton de la liste des voitures : une fois que l’API a envoyé la liste des produits à l’application, celle-ci est stockée dans un singleton.
-   Un adapter : pour construire la liste des produits et celle des commandes.
-   Un widget : affichage d’un calendrier pour choisir la date de la livraison.
-   Une animation : lorsque l’on clique sur un produit, lors du défilement des produits et lors du changement d’activité
-   L'utilisation d'un Web Service public ou personnel : création d’un API Spring-Boot avec 3 routes donnant la liste de tous les produit, une permettant d’ajouter une commande à la liste des commandes effectuées ainsi qu’une permettant d’obtenir la liste des commandes effectuées.
-   L’utilisation d’une technologie non vue : affichage d’une carte OpenStreetMap lors d’une commande pour choisir le lieu de livraison qui sera stocker sous forme d’une adresse de la livraison et envoi d’une notification pour confirmer la commande.

### 5. Choix techniques
Nous allons développer notre application en Java avec Android Studio. Nos produits et commandes seront accessibles à l’aide d’une API Spring-Boot hébergée sur un VPS extérieur.

# Projet Génie Logiciel 3A - CHATROOM

![Alt text](/banniere.png)

Ce projet est un chatroom qui permet à plusieurs utilisateurs de créer un compte et de se connecter pour échanger entre eux en public, soit en privé.

Ce projet est l'aboutissement de notre cours de Génie Lodiciel et programmation orientée objet.

## Description

La finalité du projet est de nous initier aux différentes méthodes de développement logiciel et à la programmation orientée objet.

### Prérequis

What things you need to install the software and how to install them

* Choix d'une méthode de conception

* Choix d'un patron de coneption

* lister les exigences attendues

* Réaliser la documentation

## Membres du groupe

* BEN KEMOUN Tom

* Dia Issa

* GUENNETEAU Maxime

* HATTAB Reuben

* OULD SAMBA Ahmed

* UTHAYAKUMAR Yasiga

## Comment compiler?

* Run le serveur

* Run HomeWindow

Si vous souhaitez réinitialiser la base de donnée au préalable :

* Supprimez les fichiers texte qui se sont crées pour chaque utilisateur

* Décommentez la ligne "stmt.executeUpdate("Truncate table userInfo");" dans "createTable.java" puis redémarez

### Consignes respectées:

* Méthode de concreption : Kanban

* Programme de bureau développé en java

* Architecture:

* MVC

* Principes SOLID

* Gitflow

* CI/CD

* comunication en groupe

* communication en privé

* Connexion de plusieurs utilisateurs

* Enregistrement permanent des messages

* supression des messages

* Autres Fonctionnalités :

* Inscription

* Connexion

* Implémentation d'une base de données H2 qui sauvegarde les données en permanence

* Visualisation des utilisateurs en ligne

* Option de déconnection

* Option de choix du thème dans le groupe de discussion

## Fonctionnalités

### Page d'insription

* L'utilisaateur fourni les identifiants de son compte pour s'inscrire

* Un messssage de bienvenue apparait lorsque le nom d'utilisateur est valable

![Alt text](/registration.png)

![Alt text](/registration2.png)

### Page de connexion

* L'utilisaateur fourni les identifiants de son compte pour se connecter

* Si l'utilisateur n'a pas de compte il a une option pour en créer un

![Alt text](/login.png)

### Page d'accueil

* Ecran d'accueil montrant les différents choix possibles

* L'utilisateur peut soit entrer dans le groupe de discussion ou voir les utilisateurs en ligne ou encore se déconnecter

![Alt text](/Homepage.png)

### Page de chat

* Les utilisateurs peuvent écrire et recevoir des messages dans le groupe

![Alt text](/public_chat.png)

* Les utilisateurs peuvent écrire et recevoir des messages dans le groupe

* Il suffit de double cliquer sur le nom de la personne dans la liste à droite pour envoyer un message privé

* Les messages privés sont uniquement vus par leur destinataire

![Alt text](/private_chat.png)

# Instructions de démarrage du projet

Ce document explique comment créer et lier la base de données au projet.

---

Assurez-vous d'avoir MySQL installé sur votre machine et que les commandes MySQL sont disponibles dans le terminal.

---

## Étape 1 : Configuration de la base de données

**Créez la base de données** :

   - Connectez-vous à MySQL :
     ```
     mysql -u [nom_utilisateur] -p
     ```

   - Créez la base de données :
     ```
     CREATE DATABASE task_app_db;
     ```
	exit
---

## Étape 2 : Importez le schéma de la base de données

**Exécution du schéma** :

   Depuis le terminal, à la racine du projet, exécutez la commande suivante :
   ```
   mysql -u [nom_utilisateur] -p task_app_db < src/main/resources/db/task_app_db.sql
   
 ---
 
## Étape 3: Configurez la connexion à la base de données

**Modifiez la class DbConn** :

	chemin: src\main\java\DATA\DbConn

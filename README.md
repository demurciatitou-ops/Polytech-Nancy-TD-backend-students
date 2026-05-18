
# TD — API REST minimal (TODO list)

## 🎯 Objectifs du TD
À la fin de ce TD, vous saurez :
- rédiger un **contrat OpenAPI/Swagger** de base,
- **implémenter** des endpoints REST minimaux,
- manipuler **Git** (clone, branche, commit, push) et comprendre le **workflow** minimal,
- utiliser les **outils** essentiels (GitHub, Git Bash, Postman, cURL, IntelliJ IDEA).

---

## ✅ Prérequis (matériel & logiciels)
- Java **17+**
- Maven **3.8+**
- Un **compte** GitHub
- **Git** installé (avec **Git Bash** sous Windows)
- Postman (ou équivalent)
- IntelliJ IDEA (Community suffit)

Contrôler vos versions
> ```bash
> java -version
> mvn -v
> ```

Liens :
- https://jdk.java.net/java-se-ri/21 (téléchargement JDK)
- https://maven.apache.org/download.cgi (téléchargement Maven)
- https://github.com/join (compte Github)
---

## 🧭 Étapes du TD

### Étape 0 — Mise en place
1. **Lire ces deux articles** :
  - https://about.gitlab.com/fr-fr/blog/what-is-git/
  - https://docs.gitlab.com/tutorials/make_first_git_commit/  
    ⚠️ **Sautez** la section *Create a sample project* (le dépôt existe déjà).
2. **Créer un fork du repository https://github.com/fmillerwork/Polytech-Nancy-TD-1-backend**
3. **Cloner en local votre repository en HTTPS** *(pas en SSH)* :
  - Ouvrez **Git Bash** dans le dossier où vous voulez travailler.
  - Remplacez `URL_DU_REPO` par l’URL HTTPS.
  - Commande :
    ```bash
    git clone URL_DU_REPO
    cd NOM_DU_REPERTOIRE_CLONE
    ```
  - Vérifiez l’origine :
    ```bash
    git remote -v
    ```
4. **Ouvrir le projet avec IntelliJ et configurer le SDK**
---

### Étape 1 — Lire et comprendre le projet
- Parcourez et découvrez le projet.
- Repérez le port (par défaut `8080`).

---

### Étape 2 — Premiers appels **cURL**
1. **Lancer l’application** avec IntelliJ.
2. **Faire les appels suivants depuis git-bash (possible de l'intégrer dans IntelliJ)** :
   ```bash
   curl http://localhost:8080/tasks/1
   curl -i http://localhost:8080/tasks/1
   curl -v http://localhost:8080/tasks/1
   curl -i http://localhost:8080/tasks/2
   curl -i http://localhost:8080/tasks/3
   curl -i http://localhost:8080/tasks/4
   ```
  - `-i` affiche les **en‑têtes** + le **corps**.
  - `-v` mode verbeux.
  - Vous devriez obtenir `200` (si la tâche existe) ou `404`.

---

### Étape 3 — Appeler le endpoint **GET /tasks/{id}** avec **Postman**
- Créez une requête **GET** vers `http://localhost:8080/tasks/1`. Appelez cette requête `GET /tasks/{id}`.
- Créer une **Collection** « Cours initiation dev WEB » pour y ranger vos requêtes.
- (Bonus) Ajoutez une variable `{{baseUrl}} = http://localhost:8080` pour avoir une configuration dynamique. Créer un environnement `local` et initialiser la nouvelle variable.

---

### Étape 4 — Créer **votre branche** de travail
1. **Créer et se positionner sur la branche** :
   ```bash
   git checkout -b feature/td-1
   ```
   - `-b` permet de créer la branche si elle n'existe pas.
2. **Premier commit & push** :
   ```bash
   git status
   git add .
   git commit -m "votre message"
   git push -u origin feature/td-1
   ```
3. **Pensez à commit/push régulièrement** quand le code **compile**.

---

### Étape 5 — Implémenter les nouveaux endpoints
- **`GET /tasks`** : Récupère l'ensemble des tâches.
    - Paramètres de requête : 
      - `todo-only` de type booléen indiquant s'il faut seulement récupérer les tâches à réaliser.
    - Retour : `200 Ok` + JSON avec les tâches, `204 No Content` si pas de résultat.
- **`DELETE /tasks/{id}`** : supprime une tâche par ID.
  - Retour : `204 No Content` si supprimée, `404 Not Found` sinon.
- **`PUT /tasks/{id}`** : modifie une tâche à partir d’un JSON.
  - Retour : `204 No Content`, `404 Not Found` sinon.

Documenter chacun de ces endpoints dans le fichier `swagger.yaml`, puis les implémenter. Enfin, complétez la collection Postman et tester.
Vous pouvez vous aider du Petstore (https://petstore.swagger.io/) ainsi que de Swagger Editor (https://editor.swagger.io/). Vous pouvez également installer le plugin IntelliJ **OpenAPI Specifications**
---

### Étape 6 — Commit & push de votre travail
Exporter la collection Postman puis l'ajouter à la racine de votre projet.
Pousser l'ensemble de vos travaux (collection Postman comprise) sur votre repo distant.

**⚠️ Travail minimal à rendre sur votre repo Git pour le TD1 ⚠️** 
- **Collection postman** fonctionnelle avec les 5 endpoints :
  - GET /tasks/{id}
  - GET /tasks
  - POST /tasks
  - PUT /tasks/{id}
  - DELETE /tasks/{id}
- **Swagger** avec les endpoints 5 suivants :
  - GET /tasks/{id}
  - GET /tasks
  - POST /tasks
  - PUT /tasks/{id}
  - DELETE /tasks/{id}
- **Projet** fonctionnel avec les 5 endpoints suivants : 
  - GET /tasks/{id}
  - GET /tasks
  - POST /tasks
  - PUT /tasks/{id}
  - DELETE /tasks/{id}
  
**Le TD peut être terminé en dehors du cours.**

**Veiller à la qualité du code !**
> 🔔 **Important** : **Conservez ce projet** — il sera réutilisé dans les prochains TD.
---

## Étapes bonus
- Implémenter un endpoint **DELETE /tasks** (supprime toutes les tâches). Compléter la collection Postman ainsi que le Swagger.
- Implémenter un endpoint **GET /tasks/count** (retourne le nombre total de tâches sous la forme d'un entier). Compléter la collection Postman ainsi que le Swagger.
- Ajouter des **logs** dédiés (niveau DEBUG) sur la création/suppression.

---

## 🧠 Mémo Git (commandes utilisées)
```bash
# Cloner le dépôt (HTTPS)
git clone URL_DU_REPO

# Créer une branche et s'y positionner
git checkout -b nom-branche

# Voir l'état local
git status

# Indexer tous les fichiers modifiés
git add .

# Commit (message court, verbe à l'infinitif/impératif)
git commit -m "feat: implement POST and DELETE for tasks"

# Définir le remote de suivi et pousser la première fois
git push -u origin nom-branche

# Pousser les commits suivants
git push

# Récupérer les changements distants (si besoin)
git pull

# Voir les remotes configurés
git remote -v
```

---

_Bon TD !_

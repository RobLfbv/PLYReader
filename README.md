# Membres de l'équipe
Noé Delcroix @Noe-Delcroix : https://github.com/Noe-Delcroix

Constant VENNIN @ConstantVennin : https://github.com/ConstantVennin

Robin LEFEBVRE @RobLfbv : https://github.com/RobLfbv

Quentin BERNARD @Quentin3010 : https://github.com/Quentin3010
# Livrable 1
=======

## Fonctionnalités implémentées

- [X] Afficher la liste détaillée des modèles
- [X] Il est possible de choisir le modèle à visualiser dans la liste
- [X] Le modèle est chargé et visualisé
- [X] Message d'erreur en cas d'erreur de format dans le fichier
- [X] La visualisation est correcte
- [X] On visualise simultanément trois vues du modèle (de face, de haut, de côté)
- [X] Rotation
- [X] Translation
- [X] Homotétie

## Autres éléments demandés

- [X] Tests pour les classes de calcul mathématique
- [X] Captures d'écran pour le rendu
- [X] Vidéo de présentation du rendu
- [X] Respect du format de rendu (cf Moodle)

## Distribution du travail (qui a fait quoi)
- **Robin**: Tri, Comparateurs, Matrice, Tests de tri et tests des classes de calculs mathématiques

- **Constant**: Model, lecture de fichier PLY, Exceptions PLY, Tests PLY, Matrice de rotation, couleur faces 

- **Quentin**: Model, lecture de fichier PLY, Exceptions PLY, Tests PLY et d'exceptions, lecture des couleurs 

- **Noé**: Toute la vue(Rendu sur les canvas, Gestionnaire des fichiers, Différentes vues, Interface, Bouton de transformation,...), Matrice, Test de matrice, PLYData, Repere 

- **Tous**: Points, Faces, Model, PLYData

## Difficultés rencontrées
- Destruction du modèle (problème d'arrondi lors des transformations)

- Avoir un tri efficace (Tri par insertion avec recherche dichotomique (trop lent) -> Tri Rapide (annulé car pire des cas peu efficace) -> Tri fusion (N'a jamais fonctionné) -> Tri par tas (celui qui fonctionne ! :) ))

- Vérification des fichiers PLY

- Refactor

## Pas finis/Problèmes pour le moment
- Problèmes d'optimisation on fait trop de tri tout le temps à régler lors du livrable 2

- Algorithme du peintre crée quelques problèmes lorsqu'on regarde les modèles dans certains angles 

# Livrable 2

## Fonctionnalités implémentées


- [X] Affichage faces seulement / segments seulement
- [X] Affichage avancé de la bibliothèque de modèles
- [X] Recherche dans la bibliothèque de modèles
- [X] Éditer les informations sur un modèle
- [X] Modèle centré
- [X] Éclairage
- [X] Lissage
- [X] Ombre portée
- [ ] Vue en tranches
- [X] Controleur horloge
- [X] Autres, préciser
    - Interface evolué (presets pour les canvas, renommage des fichiers ply depuis l'appli)
    - Rendu seulement des face visibles (du bon coté)
    - Customisation des couleurs (faces,fond,arrêtes)

## Autres exigences

- [X] Tests unitaires
- [X] Diagramme de classes UML
- [X] Javadoc
- [X] Captures d'écran
- [X] Vidéo de présentation
- [X] Respect du format de rendu

## Distribution du travail (qui a fait quoi)

- **Robin**: Calculs de la lumière (vecteurs et tests), Calculs de l'ombre portée

- **Constant**: Parseur evolué, edition des meta données des fichiers ply (commentaires)

- **Noé**: Toute l'interface, lissage

- **Quentin**: controlleur horloge

- **Tous**: Javadoc, cleancode

## Difficultés rencontrées

- Ombre Portée : N'ayant pas eu de cours sur les maths derrière l'ombre portée et n'ayant pas eu de réponse lorsque nous avons demander de l'aide par mail, nous avons proposé une solution de calcul de l'ombre qui n'est pas forcement comme attendu

- Le controlleur horloge : il fallait comprendre qu'on devait utiliser les Threads de JavaFX (AnimationTimer) et pas les Thread de Java qui faisaient planter notre application car il n'attendait pas la fin du rendu avant d'en lancer un autres.

- Les lois de Demeter : Certains cas nous semblent impossible à regler. Pour d'autre, nous avons juste l'impression de rajouter des getters dans des classes où il n'ont pas l'air à leur places et ainsi avoir des classes trop longues qui risquent de ne plus respecter la règles des 200 lignes par classes. Nous n'avons donc pas réglé toutes les lois de Demeter.

# Membres de l'équipe
Noé Delcroix Groupe H
Constant VENNIN Groupe H
Robin LEFEBVRE Groupe H
Quentin BERNARD Groupe H 
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
- [ ] Captures d'écran pour le rendu
- [ ] Vidéo de présentation du rendu
- [ ] Respect du format de rendu (cf Moodle)

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
- [ ] Éditer les informations sur un modèle
- [X] Modèle centré
- [ ] Éclairage
- [ ] Lissage
- [ ] Ombre portée
- [ ] Vue en tranches
- [ ] Controleur horloge
- [ ] Autres, préciser

## Autres exigences

- [ ] Tests unitaires
- [ ] Diagramme de classes UML
- [ ] Javadoc
- [ ] Captures d'écran
- [ ] Vidéo de présentation
- [ ] Respect du format de rendu

## Distribution du travail (qui a fait quoi)


## Difficultés rencontrées




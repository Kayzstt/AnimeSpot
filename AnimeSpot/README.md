# Projet AnimeSpot

Ce dépôt contient les sources du projet **AnimeSpot**.

## Description

L'application permet de voir les sorties d'animé des 7 prochains jours,
pourvoir lire des informations sur l'anime tel que :
<li>le titre
<li>une description
<li>le score moyen
<li>la date de sortie et l'heure de sortie de l'épisode

On peut aussi regarder le trailer et ajouter cette animé en favoris !

L'application permet aussi de rechercher n'importe quel anime ou manga, et avoir des informations,etc.


## Persistance des données

L'application conserve une liste des animé favoris. Cette liste est persistée dans la base de données locale de l'application.

Les données relatives aux comptes utilisateurs(mot de passe,email),sont persistées via firebase : <https://animespot-3818e.firebaseio.com/>


## Service rest

Pour collecter les données relatives aux animés, des appels au service graphQL suivant sont effectués : https://anilist.github.io/ApiV2-GraphQL-Docs/

## Auteur

**Aimen El Mahsini** g56149

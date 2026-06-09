package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.FileManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de test de {@link FileManager} retournant
 * un jeu de {@link CsvBO} injectées à la construction.
 *
 * Permet de tester la couche de conversion/mapping sans aucun
 * accès disque ni dépendance à un vrai fichier CSV.
 *
 * <pre>
 *   FileManagerAvecDonneesMock mock = new FileManagerAvecDonneesMock();
 *   mock.ajouterLigne("1", "Sport niv 1", "1", "fr", "Question ?", "Réponse", "2", "Explication", "");
 *   // injecter mock dans le service à tester
 * </pre>
 */
public class FileManagerAvecDonneesMock implements FileManager {

    private final List<CsvBO> lignes = new ArrayList<>();

    @Override
    public List<CsvBO> chargerFichier(String filePath) {

        return new ArrayList<>(lignes);
    }
}
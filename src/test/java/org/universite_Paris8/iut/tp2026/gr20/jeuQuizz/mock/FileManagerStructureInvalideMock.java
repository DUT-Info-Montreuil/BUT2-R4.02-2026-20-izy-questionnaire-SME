package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.FileManager;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.InvalidCSVStructureException;

import java.util.List;

/**
 * Implémentation de test de {@link FileManager} simulant un fichier CSV
 * dont la structure est invalide (séparateur incorrect, colonnes manquantes…).
 *
 * chargerFichier() lève toujours {@link InvalidCSVStructureException}.
 */
public class FileManagerStructureInvalideMock implements FileManager {

    private final String messageErreur;

    public FileManagerStructureInvalideMock() {
        this.messageErreur = "Structure du fichier CSV incorrecte (séparateur ou colonnes invalides).";
    }

    public FileManagerStructureInvalideMock(String messageErreur) {
        this.messageErreur = messageErreur;
    }

    @Override
    public List<CsvBO> chargerFichier(String filePath) throws InvalidCSVStructureException {
        throw new InvalidCSVStructureException(messageErreur);
    }
}
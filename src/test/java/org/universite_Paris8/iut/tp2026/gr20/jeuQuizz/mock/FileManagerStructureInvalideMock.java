package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.FileManager;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.InvalidCSVStructureException;

import java.util.List;

/**
 * Implémentation de test de {@link FileManager} simulant un fichier CSV
 * vide ou de structure invalide (illisible).
 *
 * <p>{@code chargerFichier()} lève toujours {@link InvalidCSVStructureException},
 * quelle que soit la valeur du chemin passé.
 */
public class FileManagerStructureInvalideMock implements FileManager {

    @Override
    public List<CsvBO> chargerFichier(String filePath) throws InvalidCSVStructureException {
        throw new InvalidCSVStructureException(
                "Le fichier CSV est vide ou sa structure est invalide : " + filePath
        );
    }
}
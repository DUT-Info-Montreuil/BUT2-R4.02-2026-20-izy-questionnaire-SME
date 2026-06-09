package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.FileManager;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FileSizeExceededException;

import java.util.List;

/**
 * Implémentation de test de {@link FileManager} simulant un fichier
 * dont la taille dépasse la limite autorisée.
 *
 * <p>{@code chargerFichier()} lève toujours {@link FileSizeExceededException},
 * quelle que soit la valeur du chemin passé.
 */
public class FileManagerTailleExcessiveMock implements FileManager {

    @Override
    public List<CsvBO> chargerFichier(String filePath) throws FileSizeExceededException {
        throw new FileSizeExceededException(
                "La taille du fichier dépasse la limite autorisée : " + filePath
        );
    }
}

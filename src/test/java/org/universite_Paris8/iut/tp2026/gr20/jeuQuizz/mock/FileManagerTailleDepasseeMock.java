package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.FileManager;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FileSizeExceededException;

import java.util.List;

/**
 * Implémentation de test de {@link FileManager} simulant un fichier
 * dont la taille dépasse la limite autorisée (5 Mo).
 *
 * chargerFichier() lève toujours {@link FileSizeExceededException}.
 */
public class FileManagerTailleDepasseeMock implements FileManager {

    @Override
    public List<CsvBO> chargerFichier(String filePath) throws FileSizeExceededException {
        throw new FileSizeExceededException(
                "Taille maximale autorisée dépassée (limite : 5 Mo)."
        );
    }
}
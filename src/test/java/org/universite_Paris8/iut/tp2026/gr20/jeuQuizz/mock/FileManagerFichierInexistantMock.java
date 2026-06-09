package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.FileManager;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FichierIntrouvableException;


import java.util.List;

/**
 * Implémentation de test de {@link FileManager} simulant un fichier introuvable.
 *
 * <p>{@code chargerFichier()} lève toujours {@link FichierIntrouvableException},
 * quelle que soit la valeur du chemin passé.
 */
public class FileManagerFichierInexistantMock implements FileManager {

    @Override
    public List<CsvBO> chargerFichier(String filePath) throws FichierIntrouvableException {
        throw new FichierIntrouvableException(
                "Fichier introuvable sur le système de fichiers : " + filePath
        );
    }
}

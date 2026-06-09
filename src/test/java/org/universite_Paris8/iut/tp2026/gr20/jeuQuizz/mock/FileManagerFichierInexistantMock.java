package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.FileManager;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FileNotFoundException;

import java.util.List;

/**
 * Implémentation de test de {@link FileManager} simulant un fichier introuvable.
 *
 * chargerFichier() lève toujours {@link FileNotFoundException},
 * quelle que soit la valeur du chemin passé.
 */
public class FileManagerFichierInexistantMock implements FileManager {

    @Override
    public List<CsvBO> chargerFichier(String filePath) throws FileNotFoundException {
        throw new FileNotFoundException(
                "Fichier introuvable sur le système de fichiers : " + filePath
        );
    }
}
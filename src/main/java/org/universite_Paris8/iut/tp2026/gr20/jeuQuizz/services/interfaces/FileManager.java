package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FileNotFoundException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FileSizeExceededException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.InvalidCSVStructureException;

import java.util.List;

/**
 * Contrat du service de chargement de fichier CSV brut.
 *
 * <p>Retourne une {@code List<CsvBO>} — une liste de lignes brutes,
 * indépendante de tout objet métier. Cela permet de lire n'importe
 * quel fichier CSV valide sans connaître sa structure applicative.
 *
 * <p>La conversion {@code CsvBO} → objet métier est déléguée
 * au mapper ({@code QuestionnaireMapper}).
 */
public interface FileManager {

    /**
     * Charge un fichier CSV et retourne ses lignes sous forme brute.
     *
     * @param filePath chemin vers le fichier CSV
     * @return liste de {@link CsvBO}, une entrée par ligne non vide
     * @throws FileNotFoundException        si le fichier est introuvable
     * @throws FileSizeExceededException    si la taille dépasse la limite autorisée
     * @throws InvalidCSVStructureException si le fichier est vide ou illisible
     */
    List<CsvBO> chargerFichier(String filePath)
            throws FileNotFoundException,
            FileSizeExceededException,
            InvalidCSVStructureException;
}
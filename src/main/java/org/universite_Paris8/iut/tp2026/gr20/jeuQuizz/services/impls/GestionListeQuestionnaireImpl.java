package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.impls;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mappers.CsvBOToQuestionnaireMapper;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.FileManager;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.GestionListeQuestionnaire;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FichierIntrouvableException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FileSizeExceededException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.InvalidCSVStructureException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.NoQuestionnaireAvailableException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implémentation de {@link GestionListeQuestionnaire}.
 *
 * <p>Orchestre le chargement CSV et la conversion vers les DTOs métier :
 * <ol>
 *   <li>{@link FileManager#chargerFichier} → {@code List<CsvBO>}</li>
 *   <li>{@link CsvBOToQuestionnaireMapper#map} → {@code List<QuestionnaireDTO>}</li>
 * </ol>
 */
public class GestionListeQuestionnaireImpl implements GestionListeQuestionnaire {

    private final FileManager             fileManager;
    private       List<QuestionnaireDTO>  listeQuestionnaires;

    /**
     * Constructeur de production.
     *
     * @param fileManager implémentation du chargeur CSV, ne doit pas être {@code null}
     */
    public GestionListeQuestionnaireImpl(FileManager fileManager) {
        this.fileManager         = Objects.requireNonNull(fileManager, "fileManager ne peut pas être null.");
        this.listeQuestionnaires = new ArrayList<>();
    }

    /**
     * Charge le fichier CSV, convertit les lignes en questionnaires
     * et les conserve en mémoire.
     *
     * @param filePath chemin vers le fichier CSV
     * @throws FichierIntrouvableException        si le fichier est introuvable
     * @throws FileSizeExceededException    si la taille dépasse la limite
     * @throws InvalidCSVStructureException si la structure est invalide
     */
    public void charger(String filePath)
            throws FichierIntrouvableException,
            FileSizeExceededException,
            InvalidCSVStructureException {

        List<CsvBO> lignes = fileManager.chargerFichier(filePath);
        this.listeQuestionnaires = CsvBOToQuestionnaireMapper.map(lignes);

        System.out.printf("%d questionnaire(s) chargé(s) avec succès (%d questions au total).%n",
                listeQuestionnaires.size(),
                listeQuestionnaires.stream().mapToInt(q -> q.getListeQuestion().size()).sum()
        );
    }

    @Override
    public List<QuestionnaireDTO> fournirListeQuestionnaire()
            throws NoQuestionnaireAvailableException {

        if (listeQuestionnaires.isEmpty()) {
            throw new NoQuestionnaireAvailableException();
        }
        return Collections.unmodifiableList(listeQuestionnaires);
    }
}
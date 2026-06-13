package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.GestionListeQuestionnaire;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FichierIntrouvableException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FileSizeExceededException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.InvalidCSVStructureException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.NoQuestionnaireAvailableException;

import java.util.List;

/**
 * Implémentation de test de {@link GestionListeQuestionnaire} simulant
 * l'absence totale de questionnaire disponible.
 *
 * <p>{@code fournirListeQuestionnaire()} lève toujours
 * {@link NoQuestionnaireAvailableException}.
 */
public class GestionListeQuestionnaireVideMock implements GestionListeQuestionnaire {

    @Override
    public List<QuestionnaireDTO> fournirListeQuestionnaire()
            throws NoQuestionnaireAvailableException {
        throw new NoQuestionnaireAvailableException(
                "Aucun questionnaire n'est disponible."
        );
    }
}

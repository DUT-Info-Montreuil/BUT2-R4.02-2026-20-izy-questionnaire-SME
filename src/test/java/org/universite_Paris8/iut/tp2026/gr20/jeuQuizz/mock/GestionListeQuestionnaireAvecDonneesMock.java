package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.GestionListeQuestionnaire;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.NoQuestionnaireAvailableException;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de test de {@link GestionListeQuestionnaire}
 * retournant une liste de {@link QuestionnaireDTO} préconfigurée.
 *
 * <pre>
 *   GestionListeQuestionnaireAvecDonneesMock mock = new GestionListeQuestionnaireAvecDonneesMock();
 *   mock.ajouterQuestionnaire(new QuestionnaireDTO(1, "Sport niv 1"));
 * </pre>
 */
public class GestionListeQuestionnaireAvecDonneesMock implements GestionListeQuestionnaire {

    private final List<QuestionnaireDTO> liste = new ArrayList<>();

    public void ajouterQuestionnaire(QuestionnaireDTO questionnaire) {
        liste.add(questionnaire);
    }

    @Override
    public List<QuestionnaireDTO> fournirListeQuestionnaire() throws NoQuestionnaireAvailableException {
        if (liste.isEmpty()) {
            throw new NoQuestionnaireAvailableException(
                    "Aucun questionnaire disponible en mémoire."
            );
        }
        return new ArrayList<>(liste);
    }
}
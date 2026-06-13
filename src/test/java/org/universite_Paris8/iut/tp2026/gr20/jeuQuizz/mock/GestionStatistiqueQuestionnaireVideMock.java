package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.StatQuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.GestionStatistiqueQuestionnaire;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.NoStatistiqueAvailableException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.QuestionnaireNotFoundException;

/**
 * Mock de {@link GestionStatistiqueQuestionnaire} pour le scénario sans données de jeu.
 *
 * <p>Simule un questionnaire 1 existant mais pour lequel aucune partie
 * n'a encore été jouée → {@link NoStatistiqueAvailableException} systématique
 * sur {@link #statQuestionnaire(int)}.
 *
 * <p>Tout autre {@code idQuestionnaire} lève {@link QuestionnaireNotFoundException}.
 */
public class GestionStatistiqueQuestionnaireVideMock implements GestionStatistiqueQuestionnaire {

    private static final int ID_QUESTIONNAIRE_VALIDE = 1;

    @Override
    public StatQuestionnaireDTO statQuestionnaire(int idQuestionnaire)
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        if (idQuestionnaire != ID_QUESTIONNAIRE_VALIDE) {
            throw new QuestionnaireNotFoundException(
                    "Le questionnaire demandé (id=" + idQuestionnaire + ") est introuvable en mémoire.");
        }

        throw new NoStatistiqueAvailableException(
                "Aucune partie n'a encore été jouée sur ce questionnaire.");
    }

    @Override
    public void enregistrerPartie(int idQuestionnaire) throws QuestionnaireNotFoundException {
        if (idQuestionnaire != ID_QUESTIONNAIRE_VALIDE) {
            throw new QuestionnaireNotFoundException(
                    "Le questionnaire demandé (id=" + idQuestionnaire + ") est introuvable en mémoire.");
        }
    }

    @Override
    public void enregistrerReponse(int idQuestionnaire, int numQuestion, boolean bonneReponse)
            throws QuestionnaireNotFoundException {
        if (idQuestionnaire != ID_QUESTIONNAIRE_VALIDE) {
            throw new QuestionnaireNotFoundException(
                    "Le questionnaire demandé (id=" + idQuestionnaire + ") est introuvable en mémoire.");
        }
    }
}

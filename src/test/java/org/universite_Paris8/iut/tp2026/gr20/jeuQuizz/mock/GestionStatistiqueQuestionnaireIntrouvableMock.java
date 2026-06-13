package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.StatQuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.GestionStatistiqueQuestionnaire;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.QuestionnaireNotFoundException;

/**
 * Mock de {@link GestionStatistiqueQuestionnaire} pour le scénario d'erreur 1.
 *
 * <p>Simule un système dont aucun questionnaire ne correspond à l'identifiant
 * demandé → {@link QuestionnaireNotFoundException} systématique sur toutes
 * les méthodes recevant un {@code idQuestionnaire}.
 *
 * <p>Correspond au scénario CDC :
 * <pre>
 * Statistique du questionnaire 99 :
 * [ERREUR] Le questionnaire demandé (id=99) est introuvable en mémoire.
 * </pre>
 */
public class GestionStatistiqueQuestionnaireIntrouvableMock implements GestionStatistiqueQuestionnaire {

    @Override
    public StatQuestionnaireDTO statQuestionnaire(int idQuestionnaire)
            throws QuestionnaireNotFoundException{

        throw new QuestionnaireNotFoundException(
                "Le questionnaire demandé (id=" + idQuestionnaire + ") est introuvable en mémoire.");

    }

    @Override
    public void enregistrerPartie(int idQuestionnaire) throws QuestionnaireNotFoundException {
        throw new QuestionnaireNotFoundException(
                "Le questionnaire demandé (id=" + idQuestionnaire + ") est introuvable en mémoire.");
    }

    @Override
    public void enregistrerReponse(int idQuestionnaire, int numQuestion, boolean bonneReponse)
            throws QuestionnaireNotFoundException {
        throw new QuestionnaireNotFoundException(
                "Le questionnaire demandé (id=" + idQuestionnaire + ") est introuvable en mémoire.");
    }
}

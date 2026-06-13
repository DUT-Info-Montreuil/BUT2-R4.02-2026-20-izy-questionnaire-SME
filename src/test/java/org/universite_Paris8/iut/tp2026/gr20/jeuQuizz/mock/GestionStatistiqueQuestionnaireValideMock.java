package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.StatQuestionDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.StatQuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.GestionStatistiqueQuestionnaire;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.enums.Difficulte;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.NoStatistiqueAvailableException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.QuestionnaireNotFoundException;

/**
 * Mock de {@link GestionStatistiqueQuestionnaire} pour les scénarios nominaux.
 *
 * <p>Simule un état cohérent avec le scénario nominal du CDC :
 * <ul>
 *   <li>Questionnaire 1 « Sport niv 1 » — 19 parties jouées</li>
 *   <li>Meilleure question : Q03, taux 100 % (12/12), Simple</li>
 *   <li>Pire question    : Q11, taux ~7 %  (1/14),  Intermédiaire</li>
 * </ul>
 *
 * <p>Tout autre {@code idQuestionnaire} lève {@link QuestionnaireNotFoundException}.
 */
public class GestionStatistiqueQuestionnaireValideMock implements GestionStatistiqueQuestionnaire {

    // ── Données fixes alignées sur le scénario nominal du CDC ─────────────────

    private static final int ID_QUESTIONNAIRE_VALIDE = 1;
    private static final int NB_PARTIES_JOUEES       = 19;

    private static final StatQuestionDTO MEILLEURE_QUESTION = new StatQuestionDTO(
            3,
            "Combien y a-t-il de joueurs sur le terrain dans une équipe de football ?",
            Difficulte.SIMPLE,
            12,
            12,
            1.0
    );

    private static final StatQuestionDTO PIRE_QUESTION = new StatQuestionDTO(
            11,
            "En épreuve de saut en longueur, à combien d'essais chaque concurrent a-t-il droit ?",
            Difficulte.INTERMEDIAIRE,
            1,
            14,
            1.0 / 14.0
    );

    // ── Implémentation de l'interface ─────────────────────────────────────────

    @Override
    public StatQuestionnaireDTO statQuestionnaire(int idQuestionnaire)
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        if (idQuestionnaire != ID_QUESTIONNAIRE_VALIDE) {
            throw new QuestionnaireNotFoundException(
                    "Le questionnaire demandé (id=" + idQuestionnaire + ") est introuvable en mémoire.");
        }

        return new StatQuestionnaireDTO(
                ID_QUESTIONNAIRE_VALIDE,
                NB_PARTIES_JOUEES,
                MEILLEURE_QUESTION,
                PIRE_QUESTION
        );
    }

    @Override
    public void enregistrerPartie(int idQuestionnaire) throws QuestionnaireNotFoundException {
        if (idQuestionnaire != ID_QUESTIONNAIRE_VALIDE) {
            throw new QuestionnaireNotFoundException(
                    "Le questionnaire demandé (id=" + idQuestionnaire + ") est introuvable en mémoire.");
        }
        // Mock : enregistrement sans effet de bord
    }

    @Override
    public void enregistrerReponse(int idQuestionnaire, int numQuestion, boolean bonneReponse)
            throws QuestionnaireNotFoundException {
        if (idQuestionnaire != ID_QUESTIONNAIRE_VALIDE) {
            throw new QuestionnaireNotFoundException(
                    "Le questionnaire demandé (id=" + idQuestionnaire + ") est introuvable en mémoire.");
        }
        // Mock : enregistrement sans effet de bord
    }
}

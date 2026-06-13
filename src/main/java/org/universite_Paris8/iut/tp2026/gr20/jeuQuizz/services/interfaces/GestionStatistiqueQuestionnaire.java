package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.StatQuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.NoStatistiqueAvailableException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.QuestionnaireNotFoundException;

public interface GestionStatistiqueQuestionnaire {

    /**
     * Fournit les statistiques agrégées d'un questionnaire : nombre de parties
     * jouées, question au meilleur taux de réussite et question au pire taux
     * de réussite (règles de départage du CDC appliquées).
     *
     * @param idQuestionnaire identifiant du questionnaire concerné
     * @return le {@link StatQuestionnaireDTO} complet
     * @throws QuestionnaireNotFoundException  si aucun questionnaire ne porte cet identifiant
     * @throws NoStatistiqueAvailableException si aucune partie n'a encore été jouée sur ce questionnaire
     */
    StatQuestionnaireDTO statQuestionnaire(int idQuestionnaire)
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException;

    /**
     * Enregistre une partie jouée sur un questionnaire. Alimente le compteur
     * {@code nbPartiesJouees} utilisé par {@link #statQuestionnaire(int)}.
     *
     * @param idQuestionnaire identifiant du questionnaire joué
     * @throws QuestionnaireNotFoundException si aucun questionnaire ne porte cet identifiant
     */
    void enregistrerPartie(int idQuestionnaire) throws QuestionnaireNotFoundException;

    /**
     * Enregistre le résultat d'une question posée au cours d'une partie.
     * Alimente les compteurs {@code nbFoisPosee} et {@code nbBonnesReponses}
     * utilisés par {@link #statQuestionnaire(int)}.
     *
     * @param idQuestionnaire identifiant du questionnaire concerné
     * @param numQuestion     numéro de la question posée
     * @param bonneReponse    {@code true} si le joueur a répondu correctement
     * @throws QuestionnaireNotFoundException si aucun questionnaire ne porte cet identifiant
     */
    void enregistrerReponse(int idQuestionnaire, int numQuestion, boolean bonneReponse)
            throws QuestionnaireNotFoundException;
}

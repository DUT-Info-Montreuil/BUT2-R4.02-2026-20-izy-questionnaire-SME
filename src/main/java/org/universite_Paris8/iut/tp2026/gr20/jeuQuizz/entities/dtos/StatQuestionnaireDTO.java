package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos;

import java.util.Objects;

/**
 * Objet de transfert de données représentant les statistiques agrégées
 * d'un questionnaire.
 *
 * <p>Contient le nombre de parties jouées ainsi que les questions au
 * meilleur et au pire taux de réussite (règles de départage appliquées
 * par la couche service). Aucune logique métier.
 */
public class StatQuestionnaireDTO {

    private int             idQuestionnaire;
    private int             nbPartiesJouees;
    private StatQuestionDTO meilleurQuestion;
    private StatQuestionDTO pireQuestion;

    public StatQuestionnaireDTO() {}

    /**
     * Constructeur complet — préféré pour l'instanciation explicite.
     */
    public StatQuestionnaireDTO(int idQuestionnaire, int nbPartiesJouees,
                                StatQuestionDTO meilleurQuestion, StatQuestionDTO pireQuestion) {
        this.idQuestionnaire  = idQuestionnaire;
        this.nbPartiesJouees  = nbPartiesJouees;
        this.meilleurQuestion = meilleurQuestion;
        this.pireQuestion     = pireQuestion;
    }

    // ── Getters ────────────────────────────────────────────────────────────────

    public int             getIdQuestionnaire()  { return idQuestionnaire; }
    public int             getNbPartiesJouees()  { return nbPartiesJouees; }
    public StatQuestionDTO getMeilleurQuestion() { return meilleurQuestion; }
    public StatQuestionDTO getPireQuestion()     { return pireQuestion; }

    // ── Setters ────────────────────────────────────────────────────────────────

    public void setIdQuestionnaire(int idQuestionnaire)            { this.idQuestionnaire = idQuestionnaire; }
    public void setNbPartiesJouees(int nbPartiesJouees)            { this.nbPartiesJouees = nbPartiesJouees; }
    public void setMeilleurQuestion(StatQuestionDTO meilleurQuestion) { this.meilleurQuestion = meilleurQuestion; }
    public void setPireQuestion(StatQuestionDTO pireQuestion)      { this.pireQuestion = pireQuestion; }

    // ── equals / hashCode / toString ──────────────────────────────────────────

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatQuestionnaireDTO that)) return false;
        return idQuestionnaire == that.idQuestionnaire
                && nbPartiesJouees == that.nbPartiesJouees
                && Objects.equals(meilleurQuestion, that.meilleurQuestion)
                && Objects.equals(pireQuestion, that.pireQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idQuestionnaire, nbPartiesJouees, meilleurQuestion, pireQuestion);
    }

    @Override
    public String toString() {
        return "StatQuestionnaireDTO{"
                + "id=" + idQuestionnaire
                + ", nbPartiesJouees=" + nbPartiesJouees
                + ", meilleurQuestion=" + meilleurQuestion
                + ", pireQuestion=" + pireQuestion
                + '}';
    }
}

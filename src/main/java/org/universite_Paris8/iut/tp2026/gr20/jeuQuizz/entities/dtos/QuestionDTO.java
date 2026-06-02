package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos;

import java.util.Objects;

/**
 * Objet de transfert de données représentant une question de quizz.
 *
 * <p>Ce DTO est la frontière entre la couche de parsing CSV et la couche
 * service. Il ne porte aucune logique métier — uniquement des données.
 **/

public class QuestionDTO {

    private int    numQuestion;
    private String langue;
    private String libelleQuestion;
    private String reponse;
    private int    difficulte;
    private String explication;
    private String reference;

    /** Constructeur no-arg requis par le mapper via réflexion potentielle. */
    public QuestionDTO() {}

    /**
     * Constructeur complet — préféré pour l'instanciation explicite.
     */
    public QuestionDTO(int numQuestion, String langue, String libelleQuestion,
                       String reponse, int difficulte,
                       String explication, String reference) {
        this.numQuestion     = numQuestion;
        this.langue          = langue;
        this.libelleQuestion = libelleQuestion;
        this.reponse         = reponse;
        this.difficulte      = difficulte;
        this.explication     = explication;
        this.reference       = reference;
    }

    // ── Getters ────────────────────────────────────────────────────────────────

    public int    getNumQuestion()      { return numQuestion; }
    public String getLangue()           { return langue; }
    public String getLibelleQuestion()  { return libelleQuestion; }
    public String getReponse()          { return reponse; }
    public int    getDifficulte()       { return difficulte; }
    public String getExplication()      { return explication; }
    public String getReference()        { return reference; }

    // ── Setters ────────────────────────────────────────────────────────────────

    public void setNumQuestion(int numQuestion)             { this.numQuestion = numQuestion; }
    public void setLangue(String langue)                    { this.langue = langue; }
    public void setLibelleQuestion(String libelleQuestion)  { this.libelleQuestion = libelleQuestion; }
    public void setReponse(String reponse)                  { this.reponse = reponse; }
    public void setDifficulte(int difficulte)               { this.difficulte = difficulte; }
    public void setExplication(String explication)          { this.explication = explication; }
    public void setReference(String reference)              { this.reference = reference; }

    // ── equals / hashCode / toString ──────────────────────────────────────────
    // Nécessaires pour les assertions JUnit et les collections Set/Map

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionDTO)) return false;
        QuestionDTO that = (QuestionDTO) o;
        return numQuestion == that.numQuestion && difficulte == that.difficulte
                && Objects.equals(langue, that.langue)
                && Objects.equals(libelleQuestion, that.libelleQuestion)
                && Objects.equals(reponse, that.reponse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numQuestion, langue, libelleQuestion, reponse, difficulte);
    }

    @Override
    public String toString() {
        return "QuestionDTO{"
                + "num=" + numQuestion
                + ", langue='" + langue + '\''
                + ", difficulte=" + difficulte
                + ", libelle='" + libelleQuestion + '\''
                + '}';
    }
}

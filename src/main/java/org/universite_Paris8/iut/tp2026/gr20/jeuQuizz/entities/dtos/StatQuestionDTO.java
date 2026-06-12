package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.enums.Difficulte;

import java.util.Objects;

/**
 * Objet de transfert de données représentant les statistiques d'une question.
 *
 * <p>Porte les compteurs agrégés d'une question (bonnes réponses, nombre de
 * fois posée) ainsi que le taux de réussite calculé. Aucune logique métier.
 */
public class StatQuestionDTO {

    private int        numQuestion;
    private String     libelleQuestion;
    private Difficulte difficulte;
    private int        nbBonnesReponses;
    private int        nbFoisPosee;
    private double     tauxReussite;

    public StatQuestionDTO() {}

    /**
     * Constructeur complet — préféré pour l'instanciation explicite.
     */
    public StatQuestionDTO(int numQuestion, String libelleQuestion, Difficulte difficulte,
                           int nbBonnesReponses, int nbFoisPosee, double tauxReussite) {
        this.numQuestion      = numQuestion;
        this.libelleQuestion  = libelleQuestion;
        this.difficulte       = difficulte;
        this.nbBonnesReponses = nbBonnesReponses;
        this.nbFoisPosee      = nbFoisPosee;
        this.tauxReussite     = tauxReussite;
    }

    // ── Getters ────────────────────────────────────────────────────────────────

    public int        getNumQuestion()      { return numQuestion; }
    public String     getLibelleQuestion()  { return libelleQuestion; }
    public Difficulte getDifficulte()       { return difficulte; }
    public int        getNbBonnesReponses() { return nbBonnesReponses; }
    public int        getNbFoisPosee()      { return nbFoisPosee; }
    public double     getTauxReussite()     { return tauxReussite; }

    // ── Setters ────────────────────────────────────────────────────────────────

    public void setNumQuestion(int numQuestion)             { this.numQuestion = numQuestion; }
    public void setLibelleQuestion(String libelleQuestion)  { this.libelleQuestion = libelleQuestion; }
    public void setDifficulte(Difficulte difficulte)        { this.difficulte = difficulte; }
    public void setNbBonnesReponses(int nbBonnesReponses)   { this.nbBonnesReponses = nbBonnesReponses; }
    public void setNbFoisPosee(int nbFoisPosee)             { this.nbFoisPosee = nbFoisPosee; }
    public void setTauxReussite(double tauxReussite)        { this.tauxReussite = tauxReussite; }

    // ── equals / hashCode / toString ──────────────────────────────────────────

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StatQuestionDTO that)) return false;
        return numQuestion == that.numQuestion
                && nbBonnesReponses == that.nbBonnesReponses
                && nbFoisPosee == that.nbFoisPosee
                && difficulte == that.difficulte
                && Objects.equals(libelleQuestion, that.libelleQuestion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numQuestion, libelleQuestion, difficulte, nbBonnesReponses, nbFoisPosee);
    }

    @Override
    public String toString() {
        return "StatQuestionDTO{"
                + "num=" + numQuestion
                + ", difficulte=" + difficulte
                + ", bonnesReponses=" + nbBonnesReponses
                + ", foisPosee=" + nbFoisPosee
                + ", taux=" + tauxReussite
                + '}';
    }
}

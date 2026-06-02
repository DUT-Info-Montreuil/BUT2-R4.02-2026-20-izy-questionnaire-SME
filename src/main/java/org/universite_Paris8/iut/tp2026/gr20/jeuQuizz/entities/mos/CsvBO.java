package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos;

import com.opencsv.bean.CsvBindByPosition;

/**
 * Business Object représentant une ligne CSV brute telle que lue par OpenCSV.
 *
 * <p>Chaque attribut est mappé automatiquement sur une colonne du CSV
 * via {@link CsvBindByPosition}. La position correspond à l'ordre des
 * colonnes dans le fichier (0-based).
 *
 * <p>Cette classe reste volontairement neutre — elle ne contient aucune
 * logique métier. La conversion vers {@code QuestionnaireDTO} est
 * déléguée au mapper.
 */
public class CsvBO {

    @CsvBindByPosition(position = 0)
    private String idQuestionnaire;

    @CsvBindByPosition(position = 1)
    private String libelleQuestionnaire;

    @CsvBindByPosition(position = 2)
    private String numQuestion;

    @CsvBindByPosition(position = 3)
    private String langue;

    @CsvBindByPosition(position = 4)
    private String libelleQuestion;

    @CsvBindByPosition(position = 5)
    private String reponse;

    @CsvBindByPosition(position = 6)
    private String difficulte;

    @CsvBindByPosition(position = 7)
    private String explication;

    @CsvBindByPosition(position = 8)
    private String reference;

    public CsvBO() {}

    // ── Getters ───────────────────────────────────────────────────────────────

    public String getIdQuestionnaire()      { return idQuestionnaire; }
    public String getLibelleQuestionnaire() { return libelleQuestionnaire; }
    public String getNumQuestion()          { return numQuestion; }
    public String getLangue()               { return langue; }
    public String getLibelleQuestion()      { return libelleQuestion; }
    public String getReponse()              { return reponse; }
    public String getDifficulte()           { return difficulte; }
    public String getExplication()          { return explication; }
    public String getReference()            { return reference; }

    // ── Setters ───────────────────────────────────────────────────────────────

    public void setIdQuestionnaire(String idQuestionnaire)           { this.idQuestionnaire = idQuestionnaire; }
    public void setLibelleQuestionnaire(String libelleQuestionnaire) { this.libelleQuestionnaire = libelleQuestionnaire; }
    public void setNumQuestion(String numQuestion)                   { this.numQuestion = numQuestion; }
    public void setLangue(String langue)                             { this.langue = langue; }
    public void setLibelleQuestion(String libelleQuestion)           { this.libelleQuestion = libelleQuestion; }
    public void setReponse(String reponse)                           { this.reponse = reponse; }
    public void setDifficulte(String difficulte)                     { this.difficulte = difficulte; }
    public void setExplication(String explication)                   { this.explication = explication; }
    public void setReference(String reference)                       { this.reference = reference; }

    @Override
    public String toString() {
        return "CsvBO{"
                + "idQuestionnaire='" + idQuestionnaire + '\''
                + ", libelleQuestionnaire='" + libelleQuestionnaire + '\''
                + ", numQuestion='" + numQuestion + '\''
                + ", langue='" + langue + '\''
                + ", libelleQuestion='" + libelleQuestion + '\''
                + '}';
    }
}
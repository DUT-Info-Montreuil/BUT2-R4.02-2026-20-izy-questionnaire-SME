package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Objet de transfert de données représentant un questionnaire.
 *
 * <p>Conteneur logique d'un ensemble thématique de {@link QuestionDTO},
 * directement mappé depuis le fichier CSV source.
 *
 * <p><strong>Règle :</strong> ne jamais y ajouter de logique métier.
 */
public class QuestionnaireDTO {

    private int               idQuestionnaire;
    private String            libelleQuestionnaire;
    private List<QuestionDTO> listeQuestion;

    public QuestionnaireDTO() {
        this.listeQuestion = new ArrayList<>();
    }

    public QuestionnaireDTO(int idQuestionnaire, String libelleQuestionnaire) {
        this.idQuestionnaire      = idQuestionnaire;
        this.libelleQuestionnaire = libelleQuestionnaire;
        this.listeQuestion        = new ArrayList<>();
    }

    // ── Getters ────────────────────────────────────────────────────────────────

    public int    getIdQuestionnaire()      { return idQuestionnaire; }
    public String getLibelleQuestionnaire() { return libelleQuestionnaire; }

    /**
     * Retourne une vue non modifiable de la liste des questions.
     * Pour ajouter une question, utiliser {@link #ajouterQuestion(QuestionDTO)}.
     */
    public List<QuestionDTO> getListeQuestion() {
        return Collections.unmodifiableList(listeQuestion);
    }

    // ── Setters ────────────────────────────────────────────────────────────────

    public void setIdQuestionnaire(int idQuestionnaire) {
        this.idQuestionnaire = idQuestionnaire;
    }

    public void setLibelleQuestionnaire(String libelleQuestionnaire) {
        this.libelleQuestionnaire = libelleQuestionnaire;
    }

    /**
     * Remplace entièrement la liste des questions.
     * Effectue une copie défensive pour éviter les mutations externes.
     *
     * @param listeQuestion nouvelle liste (ne doit pas être {@code null})
     */
    public void setListeQuestion(List<QuestionDTO> listeQuestion) {
        Objects.requireNonNull(listeQuestion, "listeQuestion ne peut pas être null.");
        this.listeQuestion = new ArrayList<>(listeQuestion);
    }

    /**
     * Ajoute une question à ce questionnaire.
     *
     * @param question la question à ajouter (ne doit pas être {@code null})
     */
    public void ajouterQuestion(QuestionDTO question) {
        Objects.requireNonNull(question, "La question à ajouter ne peut pas être null.");
        this.listeQuestion.add(question);
    }

    // ── equals / hashCode / toString ──────────────────────────────────────────

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QuestionnaireDTO)) return false;
        QuestionnaireDTO that = (QuestionnaireDTO) o;
        return idQuestionnaire == that.idQuestionnaire
                && Objects.equals(libelleQuestionnaire, that.libelleQuestionnaire);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idQuestionnaire, libelleQuestionnaire);
    }

    @Override
    public String toString() {
        return "QuestionnaireDTO{"
                + "id=" + idQuestionnaire
                + ", libelle='" + libelleQuestionnaire + '\''
                + ", nbQuestions=" + listeQuestion.size()
                + '}';
    }
}

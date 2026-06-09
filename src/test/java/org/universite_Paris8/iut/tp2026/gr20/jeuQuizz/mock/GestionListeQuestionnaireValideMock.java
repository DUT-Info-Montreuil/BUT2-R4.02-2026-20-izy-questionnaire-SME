package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.GestionListeQuestionnaire;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.enums.Difficulte;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FichierIntrouvableException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FileSizeExceededException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.InvalidCSVStructureException;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de test de {@link GestionListeQuestionnaire} simulant
 * un retour nominal.
 *
 * <p>{@code fournirListeQuestionnaire()} retourne toujours une liste
 * cohérente (jamais {@code null}, jamais vide) de deux questionnaires
 * entièrement peuplés de {@link QuestionDTO}.
 */
public class GestionListeQuestionnaireValideMock implements GestionListeQuestionnaire {

    @Override
    public List<QuestionnaireDTO> fournirListeQuestionnaire() {
        List<QuestionnaireDTO> questionnaires = new ArrayList<>();

        // ── Questionnaire 1 : Culture générale ──────────────────────────────
        QuestionnaireDTO q1 = new QuestionnaireDTO(1, "Culture générale");
        q1.ajouterQuestion(new QuestionDTO(
                1, "fr", "Quelle est la capitale de la France ?", "Paris", Difficulte.EXPERT,
                "Paris est la capitale de la France depuis le Xe siècle.",
                "https://fr.wikipedia.org/wiki/Paris"));
        q1.ajouterQuestion(new QuestionDTO(
                2, "fr", "Combien de continents compte la Terre ?", "7", Difficulte.EXPERT,
                "On dénombre traditionnellement sept continents.",
                "https://fr.wikipedia.org/wiki/Continent"));
        questionnaires.add(q1);

        // ── Questionnaire 2 : Informatique ──────────────────────────────────
        QuestionnaireDTO q2 = new QuestionnaireDTO(2, "Informatique");
        q2.ajouterQuestion(new QuestionDTO(
                1, "fr", "Que signifie l'acronyme CPU ?", "Central Processing Unit", Difficulte.EXPERT,
                "Le CPU est l'unité centrale de traitement.",
                "https://fr.wikipedia.org/wiki/Processeur"));
        q2.ajouterQuestion(new QuestionDTO(
                2, "fr", "Quel paradigme repose sur l'encapsulation et l'héritage ?",
                "La programmation orientée objet", Difficulte.EXPERT,
                "La POO structure le code autour d'objets et de classes.",
                "https://fr.wikipedia.org/wiki/Programmation_orientée_objet"));
        questionnaires.add(q2);

        return questionnaires;
    }
}

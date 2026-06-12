package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.impls;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.StatQuestionDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.StatQuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.GestionStatistiqueQuestionnaire;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.NoStatistiqueAvailableException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.QuestionnaireNotFoundException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Implémentation de {@link GestionStatistiqueQuestionnaire}.
 *
 * <p>Reçoit en injection la liste des questionnaires déjà chargés et conserve
 * en mémoire les compteurs de jeu (parties jouées, questions posées, bonnes
 * réponses) alimentés via {@link #enregistrerPartie(int)} et
 * {@link #enregistrerReponse(int, int, boolean)}.
 *
 * <p>Règles de départage du CDC en cas d'égalité de taux de réussite :
 * <ul>
 *   <li>Meilleur taux : difficulté la plus élevée, puis la plus posée,
 *       puis la première dans l'ordre du fichier CSV.</li>
 *   <li>Pire taux : difficulté la plus faible, puis la plus posée,
 *       puis la première dans l'ordre du fichier CSV.</li>
 * </ul>
 */
public class GestionStatistiqueQuestionnaireImpl implements GestionStatistiqueQuestionnaire {

    /** Compteurs bruts d'une question (mutables, internes au service). */
    private static class CompteurQuestion {
        int nbFoisPosee;
        int nbBonnesReponses;
    }

    private final List<QuestionnaireDTO> listeQuestionnaires;

    /** idQuestionnaire → nombre de parties jouées. */
    private final Map<Integer, Integer> partiesParQuestionnaire = new HashMap<>();

    /** idQuestionnaire → (numQuestion → compteurs). */
    private final Map<Integer, Map<Integer, CompteurQuestion>> compteursParQuestionnaire = new HashMap<>();

    /**
     * @param listeQuestionnaires liste partagée avec l'appelant ; ne doit pas être {@code null}
     */
    public GestionStatistiqueQuestionnaireImpl(List<QuestionnaireDTO> listeQuestionnaires) {
        this.listeQuestionnaires = Objects.requireNonNull(listeQuestionnaires, "listeQuestionnaires ne peut pas être null.");
    }

    @Override
    public StatQuestionnaireDTO statQuestionnaire(int idQuestionnaire)
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        QuestionnaireDTO questionnaire = rechercherQuestionnaire(idQuestionnaire);

        int nbPartiesJouees = partiesParQuestionnaire.getOrDefault(idQuestionnaire, 0);
        if (nbPartiesJouees == 0) {
            throw new NoStatistiqueAvailableException(
                    "Aucune partie n'a encore été jouée sur ce questionnaire.");
        }

        List<StatQuestionDTO> statsQuestions = calculerStatsQuestions(questionnaire);
        if (statsQuestions.isEmpty()) {
            throw new NoStatistiqueAvailableException(
                    "Aucune question n'a encore été posée sur ce questionnaire.");
        }

        StatQuestionDTO meilleurQuestion = selectionner(statsQuestions, comparateurMeilleur());
        StatQuestionDTO pireQuestion     = selectionner(statsQuestions, comparateurPire());

        return new StatQuestionnaireDTO(idQuestionnaire, nbPartiesJouees, meilleurQuestion, pireQuestion);
    }

    @Override
    public void enregistrerPartie(int idQuestionnaire) throws QuestionnaireNotFoundException {
        rechercherQuestionnaire(idQuestionnaire);
        partiesParQuestionnaire.merge(idQuestionnaire, 1, Integer::sum);
    }

    @Override
    public void enregistrerReponse(int idQuestionnaire, int numQuestion, boolean bonneReponse)
            throws QuestionnaireNotFoundException {
        rechercherQuestionnaire(idQuestionnaire);
        CompteurQuestion compteur = compteursParQuestionnaire
                .computeIfAbsent(idQuestionnaire, id -> new HashMap<>())
                .computeIfAbsent(numQuestion, num -> new CompteurQuestion());
        compteur.nbFoisPosee++;
        if (bonneReponse) {
            compteur.nbBonnesReponses++;
        }
    }

    // ── Méthodes internes ──────────────────────────────────────────────────────

    private QuestionnaireDTO rechercherQuestionnaire(int idQuestionnaire)
            throws QuestionnaireNotFoundException {
        return listeQuestionnaires.stream()
                .filter(q -> q.getIdQuestionnaire() == idQuestionnaire)
                .findFirst()
                .orElseThrow(() -> new QuestionnaireNotFoundException(
                        "Le questionnaire demandé (id=" + idQuestionnaire + ") est introuvable en mémoire."));
    }

    /**
     * Construit, dans l'ordre du fichier CSV, les statistiques des questions
     * ayant été posées au moins une fois (le taux de réussite n'est pas
     * défini pour une question jamais posée).
     */
    private List<StatQuestionDTO> calculerStatsQuestions(QuestionnaireDTO questionnaire) {
        Map<Integer, CompteurQuestion> compteurs = compteursParQuestionnaire
                .getOrDefault(questionnaire.getIdQuestionnaire(), Map.of());

        List<StatQuestionDTO> stats = new ArrayList<>();
        for (QuestionDTO question : questionnaire.getListeQuestion()) {
            CompteurQuestion compteur = compteurs.get(question.getNumQuestion());
            if (compteur == null || compteur.nbFoisPosee == 0) {
                continue;
            }
            double taux = (double) compteur.nbBonnesReponses / compteur.nbFoisPosee;
            stats.add(new StatQuestionDTO(
                    question.getNumQuestion(),
                    question.getLibelleQuestion(),
                    question.getDifficulte(),
                    compteur.nbBonnesReponses,
                    compteur.nbFoisPosee,
                    taux
            ));
        }
        return stats;
    }

    /**
     * Retourne la première question (ordre CSV) au sens du comparateur :
     * en parcourant la liste dans l'ordre et en ne retenant un candidat que
     * s'il est strictement meilleur, l'égalité parfaite conserve la première
     * question rencontrée (3ème règle de départage).
     */
    private StatQuestionDTO selectionner(List<StatQuestionDTO> stats, Comparator<StatQuestionDTO> comparateur) {
        StatQuestionDTO retenue = stats.get(0);
        for (StatQuestionDTO candidate : stats.subList(1, stats.size())) {
            if (comparateur.compare(candidate, retenue) < 0) {
                retenue = candidate;
            }
        }
        return retenue;
    }

    /** Meilleur taux : taux décroissant, puis difficulté la plus élevée, puis la plus posée. */
    private Comparator<StatQuestionDTO> comparateurMeilleur() {
        return Comparator.comparingDouble(StatQuestionDTO::getTauxReussite).reversed()
                .thenComparing(Comparator.comparingInt((StatQuestionDTO s) -> s.getDifficulte().getNiveau()).reversed())
                .thenComparing(Comparator.comparingInt(StatQuestionDTO::getNbFoisPosee).reversed());
    }

    /** Pire taux : taux croissant, puis difficulté la plus faible, puis la plus posée. */
    private Comparator<StatQuestionDTO> comparateurPire() {
        return Comparator.comparingDouble(StatQuestionDTO::getTauxReussite)
                .thenComparingInt((StatQuestionDTO s) -> s.getDifficulte().getNiveau())
                .thenComparing(Comparator.comparingInt(StatQuestionDTO::getNbFoisPosee).reversed());
    }
}

package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.StatQuestionDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.StatQuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mappers.CsvBOToQuestionnaireMapper;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.impls.FileManagerImpl;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.impls.GestionListeQuestionnaireImpl;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.impls.GestionStatistiqueQuestionnaireImpl;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.FileManager;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.GestionListeQuestionnaire;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.GestionStatistiqueQuestionnaire;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.enums.Difficulte;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FichierIntrouvableException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FileSizeExceededException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.InvalidCSVStructureException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.NoQuestionnaireAvailableException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.NoStatistiqueAvailableException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.QuestionnaireNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final String DOSSIER_RESSOURCES = "src/main/resources/";

    public static void main(String[] args) {

        FileManager fileManager = new FileManagerImpl();
        List<QuestionnaireDTO> questionnaires = new ArrayList<>();
        GestionListeQuestionnaire gestion = new GestionListeQuestionnaireImpl(questionnaires);
        GestionStatistiqueQuestionnaire statistiques = new GestionStatistiqueQuestionnaireImpl(questionnaires);
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Jeu du Quizz — Système Questionnaire ===");
        System.out.println("Fichiers CSV attendus dans : " + DOSSIER_RESSOURCES);

        demanderEtCharger(fileManager, questionnaires, scanner);

        boolean continuer = true;
        while (continuer) {
            afficherMenu();
            String choix = scanner.nextLine().trim();
            switch (choix) {
                case "1" -> afficherListeQuestionnaires(gestion);
                case "2" -> demanderEtCharger(fileManager, questionnaires, scanner);
                case "3" -> afficherStatistiques(statistiques, scanner);
                case "4" -> { continuer = false; System.out.println("Au revoir !"); }
                default  -> System.out.println("Choix invalide.");
            }
        }
        scanner.close();
    }

    private static void afficherMenu() {
        System.out.println("\n========= MENU =========");
        System.out.println("1. Afficher les questionnaires");
        System.out.println("2. Charger un fichier CSV");
        System.out.println("3. Afficher les statistiques d'un questionnaire");
        System.out.println("4. Quitter");
        System.out.print("Votre choix : ");
    }

    private static void demanderEtCharger(FileManager fileManager,
                                          List<QuestionnaireDTO> questionnaires,
                                          Scanner scanner) {
        System.out.print("\nNom du fichier CSV : ");
        String chemin = DOSSIER_RESSOURCES + scanner.nextLine().trim();
        try {
            List<CsvBO> lignes = fileManager.chargerFichier(chemin);
            questionnaires.clear();
            questionnaires.addAll(CsvBOToQuestionnaireMapper.map(lignes));
        } catch (FichierIntrouvableException | FileSizeExceededException | InvalidCSVStructureException e) {
            System.err.println("[ERREUR] " + e.getMessage());
        }
    }

    private static void afficherListeQuestionnaires(GestionListeQuestionnaire gestion) {
        try {
            List<QuestionnaireDTO> liste = gestion.fournirListeQuestionnaire();
            System.out.println("\nQuestionnaires disponibles :");
            for (int i = 0; i < liste.size(); i++) {
                QuestionnaireDTO q = liste.get(i);
                System.out.printf("  %d. %s [ %s ] – %d question(s)%n",
                        i + 1,
                        q.getLibelleQuestionnaire(),
                        q.getListeQuestion().isEmpty() ? "?" : q.getListeQuestion().get(0).getLangue(),
                        q.getListeQuestion().size()
                );
            }
        } catch (NoQuestionnaireAvailableException e) {
            System.err.println("[ERREUR] " + e.getMessage());
        }
    }

    private static void afficherStatistiques(GestionStatistiqueQuestionnaire statistiques, Scanner scanner) {
        System.out.print("\nIdentifiant du questionnaire : ");
        int idQuestionnaire;
        try {
            idQuestionnaire = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.err.println("[ERREUR] Identifiant invalide : un nombre entier est attendu.");
            return;
        }

        System.out.println("\nStatistique du questionnaire " + idQuestionnaire + " :");
        try {
            StatQuestionnaireDTO stat = statistiques.statQuestionnaire(idQuestionnaire);
            System.out.printf("Le questionnaire %d a été joué : %d fois.%n",
                    stat.getIdQuestionnaire(), stat.getNbPartiesJouees());
            System.out.println("\nLa question avec le meilleur taux de réussite est :");
            afficherStatQuestion(stat.getMeilleurQuestion());
            System.out.println("\nLa question avec le pire taux de réussite est :");
            afficherStatQuestion(stat.getPireQuestion());
        } catch (QuestionnaireNotFoundException | NoStatistiqueAvailableException e) {
            System.err.println("[ERREUR] " + e.getMessage());
        }
    }

    private static void afficherStatQuestion(StatQuestionDTO question) {
        System.out.printf("Q%02d. %s [%s]%n",
                question.getNumQuestion(),
                question.getLibelleQuestion(),
                libelleDifficulte(question.getDifficulte()));
        System.out.printf("%d bonne%s réponse%s sur %d (taux : %d%%)%n",
                question.getNbBonnesReponses(),
                question.getNbBonnesReponses() > 1 ? "s" : "",
                question.getNbBonnesReponses() > 1 ? "s" : "",
                question.getNbFoisPosee(),
                Math.round(question.getTauxReussite() * 100));
    }

    private static String libelleDifficulte(Difficulte difficulte) {
        return switch (difficulte) {
            case SIMPLE        -> "Simple";
            case INTERMEDIAIRE -> "Intermédiaire";
            case EXPERT        -> "Expert";
        };
    }
}

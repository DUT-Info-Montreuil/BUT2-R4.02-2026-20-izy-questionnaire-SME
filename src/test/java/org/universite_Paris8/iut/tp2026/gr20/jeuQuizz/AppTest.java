package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock.FileManagerAvecDonneesMock;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock.FileManagerFichierInexistantMock;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock.FileManagerStructureInvalideMock;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock.FileManagerTailleDepasseeMock;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock.GestionListeQuestionnaireAvecDonneesMock;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock.GestionListeQuestionnaireVideMock;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.FileManager;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.GestionListeQuestionnaire;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FileNotFoundException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FileSizeExceededException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.InvalidCSVStructureException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.NoQuestionnaireAvailableException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    // ─── Utilitaire : chemin absolu d'un CSV dans src/test/resources/csv/ ───────

    // ═══════════════════════════════════════════════════════════════════════════
    // BLOC 1 – FileManager : étape 1 – fichier inexistant
    // ═══════════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("FileManager – Étape 1 : fichier introuvable")
    class FichierInexistant {

        FileManager fileManagerFileNotFoundService = new FileManagerFichierInexistantMock();

        @Test
        @DisplayName("Lève FileNotFoundException")
        void leveFileNotFoundException() {
            assertThrows(FileNotFoundException.class,
                    () -> fileManagerFileNotFoundService.chargerFichier("/chemin/inexistant.csv"));
        }

    }

    // ═══════════════════════════════════════════════════════════════════════════
    // BLOC 2 – FileManager : étape 2 – taille dépassée
    // ═══════════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("FileManager – Étape 2 : taille dépassée")
    class TailleDepassee {

        FileManager fileManagerFileTaileDepasse = new FileManagerTailleDepasseeMock();

        @Test
        @DisplayName("Lève FileSizeExceededException")
        void leveFileSizeExceededException() {
            assertThrows(FileSizeExceededException.class,
                    () -> fileManagerFileTaileDepasse.chargerFichier("/gros_fichier.csv"));
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // BLOC 3 – FileManager : étape 3 – structure CSV invalide (mock)
    // ═══════════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("FileManager – Étape 3 : structure invalide (mock)")
    class StructureInvalideMock {

        @Test
        @DisplayName("Lève InvalidCSVStructureException")
        void leveInvalidCSVStructureException() {
            FileManager fileManagerStructureInvalideMock = new FileManagerStructureInvalideMock();
            assertThrows(InvalidCSVStructureException.class,
                    () -> fileManagerStructureInvalideMock.chargerFichier("/corrompu.csv"));
        }

    }

    // ═══════════════════════════════════════════════════════════════════════════
    // BLOC 4 – FileManager : étape 4 – chargement nominal (mock avec données)
    // ═══════════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("FileManager – Étape 4 : chargement nominal (mock)")
    class ChargementNominalMock {

        @Test
        @DisplayName("Retourne une liste non nulle")
        void retourneListeNonNulle() {
            FileManagerAvecDonneesMock mock = new FileManagerAvecDonneesMock();
            assertNotNull(mock.chargerFichier("/fictif.csv"));
        }

        @Test
        @DisplayName("Retourne autant de CsvBO que de lignes injectées")
        void retourneNombreCorrectDeLignes() {
            FileManagerAvecDonneesMock mock = new FileManagerAvecDonneesMock();
            assertEquals(3, mock.chargerFichier("/fictif.csv").size());
        }

        @Test
        @DisplayName("Les données du CsvBO correspondent à la ligne injectée")
        void donneesCsvBoCorrectes() {
            FileManagerAvecDonneesMock mock = new FileManagerAvecDonneesMock();

            CsvBO bo = mock.chargerFichier("/fictif.csv").get(0);
            assertEquals("1",           bo.getIdQuestionnaire());
            assertEquals("Sport niv 1", bo.getLibelleQuestionnaire());
            assertEquals("3",           bo.getNumQuestion());
            assertEquals("fr",          bo.getLangue());
            assertEquals("Onze",        bo.getReponse());
            assertEquals("1",           bo.getDifficulte());
        }

        @Test
        @DisplayName("L'ordre des lignes injectées est conservé")
        void ordreInsertionConserve() {
            FileManagerAvecDonneesMock mock = new FileManagerAvecDonneesMock();


            List<CsvBO> result = mock.chargerFichier("/fictif.csv");
            assertEquals("1", result.get(0).getNumQuestion());
            assertEquals("2", result.get(1).getNumQuestion());
            assertEquals("3", result.get(2).getNumQuestion());
        }

        @Test
        @DisplayName("Sans lignes injectées, retourne une liste vide")
        void sansLignes_retourneListeVide() {
            FileManagerAvecDonneesMock mock = new FileManagerAvecDonneesMock();
            assertTrue(mock.chargerFichier("/fictif.csv").isEmpty());
        }

        @Test
        @DisplayName("Une référence vide est bien stockée comme chaîne vide")
        void referenceVideBienStockee() {
            FileManagerAvecDonneesMock mock = new FileManagerAvecDonneesMock();

            assertEquals("", mock.chargerFichier("/fictif.csv").get(0).getReference());
        }
    }

    // ═══════════════════════════════════════════════════════════════════════════
    // BLOC 5 – GestionListeQuestionnaire : aucun questionnaire disponible
    // ═══════════════════════════════════════════════════════════════════════════

    @Nested
    @DisplayName("GestionListeQuestionnaire – Liste vide")
    class ListeVide {

        GestionListeQuestionnaire gestionListeQuestionnaireVideService = new GestionListeQuestionnaireVideMock();

        @Test
        @DisplayName("Lève NoQuestionnaireAvailableException")
        void leveNoQuestionnaireAvailableException() {
            assertThrows(NoQuestionnaireAvailableException.class,
                    gestionListeQuestionnaireVideService::fournirListeQuestionnaire);
        }
    }
}

    // ═══════════════════════════════════════════════════════════════════════════
    // BLOC 6 – GestionListeQuestionnaire : liste disponible
    //═══════════════════════════════════════════════════════════════════════════

        @Nested
        @DisplayName("GestionListeQuestionnaire – Liste disponible")
        class ListeDisponible {

            @Test
            @DisplayName("Retourne le bon nombre de questionnaires")
            void retourneNombreCorrect() {
                GestionListeQuestionnaireAvecDonneesMock mock = new GestionListeQuestionnaireAvecDonneesMock();
                mock.ajouterQuestionnaire(new QuestionnaireDTO(1, "Sport niv 1"));
                mock.ajouterQuestionnaire(new QuestionnaireDTO(2, "Célébrités niv 1"));
                assertEquals(2, mock.fournirListeQuestionnaire().size());
            }

            @Test
            @DisplayName("Le 1er questionnaire a le bon id")
            void premiereEntree_idCorrect() {
                GestionListeQuestionnaireAvecDonneesMock mock = new GestionListeQuestionnaireAvecDonneesMock();
                mock.ajouterQuestionnaire(new QuestionnaireDTO(1, "Sport niv 1"));
                assertEquals(1, mock.fournirListeQuestionnaire().get(0).getIdQuestionnaire());
            }

            @Test
            @DisplayName("Le 1er questionnaire a le bon libellé")
            void premiereEntree_libelleCorrect() {
                GestionListeQuestionnaireAvecDonneesMock mock = new GestionListeQuestionnaireAvecDonneesMock();
                mock.ajouterQuestionnaire(new QuestionnaireDTO(1, "Sport niv 1"));
                assertEquals("Sport niv 1",
                        mock.fournirListeQuestionnaire().get(0).getLibelleQuestionnaire());
            }

            @Test
            @DisplayName("Un questionnaire contient bien ses questions")
            void questionnaireContientSesQuestions() {
                QuestionnaireDTO q = new QuestionnaireDTO(1, "Sport niv 1");
                q.ajouterQuestion(new QuestionDTO(1,"fr","Question 1 ?","Tee",1,"Explication",""));
                q.ajouterQuestion(new QuestionDTO(2,"fr","Question 2 ?","Onze",1,"Explication",""));

                GestionListeQuestionnaireAvecDonneesMock mock = new GestionListeQuestionnaireAvecDonneesMock();
                mock.ajouterQuestionnaire(q);
                assertEquals(2, mock.fournirListeQuestionnaire().get(0).getListeQuestion().size());
            }

            @Test
            @DisplayName("Sans questionnaire injecté, lève NoQuestionnaireAvailableException")
            void sansQuestionnaire_leveException() {
                GestionListeQuestionnaireAvecDonneesMock mock = new GestionListeQuestionnaireAvecDonneesMock();
                assertThrows(NoQuestionnaireAvailableException.class,
                        mock::fournirListeQuestionnaire);
            }

            @Test
            @DisplayName("Peut être appelée plusieurs fois sans exception")
            void appelsMultiples_sansException() {
                GestionListeQuestionnaireAvecDonneesMock mock = new GestionListeQuestionnaireAvecDonneesMock();
                mock.ajouterQuestionnaire(new QuestionnaireDTO(1, "Sport niv 1"));
                assertDoesNotThrow(() -> {
                    mock.fournirListeQuestionnaire();
                    mock.fournirListeQuestionnaire();
                });
            }
        }

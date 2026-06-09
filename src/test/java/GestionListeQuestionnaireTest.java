import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock.GestionListeQuestionnaireValideMock;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock.GestionListeQuestionnaireVideMock;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.GestionListeQuestionnaire;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.NoQuestionnaireAvailableException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests du contrat de l'interface {@link GestionListeQuestionnaire},
 * vérifié via ses mocks.
 *
 * <p>Deux scénarios : retour nominal d'une liste peuplée, et absence
 * de questionnaire disponible.
 */
class GestionListeQuestionnaireTest {

    @Test
    @DisplayName("fournirListeQuestionnaire() retourne une liste non nulle et non vide")
    void fournirListe_retourneListeNonVide() throws Exception {
        GestionListeQuestionnaire service = new GestionListeQuestionnaireValideMock();

        List<QuestionnaireDTO> questionnaires = service.fournirListeQuestionnaire();

        assertAll(
                () -> assertNotNull(questionnaires, "La liste ne doit pas être null."),
                () -> assertFalse(questionnaires.isEmpty(), "La liste ne doit pas être vide."),
                () -> assertEquals(2, questionnaires.size(), "Le mock fournit 2 questionnaires.")
        );
    }

    @Test
    @DisplayName("fournirListeQuestionnaire() retourne des questionnaires correctement peuplés")
    void fournirListe_questionnairesPeuples() throws Exception {
        GestionListeQuestionnaire service = new GestionListeQuestionnaireValideMock();

        QuestionnaireDTO premier = service.fournirListeQuestionnaire().get(0);

        assertAll(
                () -> assertEquals(1, premier.getIdQuestionnaire()),
                () -> assertEquals("Culture générale", premier.getLibelleQuestionnaire()),
                () -> assertEquals(2, premier.getListeQuestion().size(),
                        "Le premier questionnaire contient 2 questions.")
        );
    }


    @Test
    @DisplayName("fournirListeQuestionnaire() lève NoQuestionnaireAvailableException si vide")
    void fournirListe_aucunQuestionnaire() {
        GestionListeQuestionnaire service = new GestionListeQuestionnaireVideMock();

        assertThrows(
                NoQuestionnaireAvailableException.class,
                service::fournirListeQuestionnaire
        );
    }
}

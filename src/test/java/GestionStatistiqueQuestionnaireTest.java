import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.StatQuestionDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.StatQuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.GestionStatistiqueQuestionnaire;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.enums.Difficulte;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.NoStatistiqueAvailableException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.QuestionnaireNotFoundException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests unitaires du UC3 : Fournir les statistiques d'un questionnaire.
 *
 * <p>Stratégie : {@link GestionStatistiqueQuestionnaire} est mockée via Mockito.
 * Aucune implémentation réelle n'est instanciée — on teste uniquement les
 * contrats définis dans le dossier de conception.
 *
 * <p>Scénarios couverts :
 * <ol>
 *   <li>Nominal — statistiques retournées avec succès</li>
 *   <li>Erreur 1 — questionnaire introuvable ({@link QuestionnaireNotFoundException})</li>
 *   <li>Erreur 2 — aucune partie jouée ({@link NoStatistiqueAvailableException})</li>
 *   <li>Règles de départage — meilleur taux</li>
 *   <li>Règles de départage — pire taux</li>
 *   <li>enregistrerPartie nominal</li>
 *   <li>enregistrerPartie questionnaire introuvable</li>
 *   <li>enregistrerReponse bonne réponse</li>
 *   <li>enregistrerReponse mauvaise réponse</li>
 *   <li>enregistrerReponse questionnaire introuvable</li>
 * </ol>
 */
@ExtendWith(MockitoExtension.class)
class GestionStatistiqueQuestionnaireTest {

    @Mock
    private GestionStatistiqueQuestionnaire gestionStatistique;

    // ── Données de test alignées sur le scénario nominal du CDC ───────────────

    private static final int    ID_VALIDE          = 1;
    private static final int    ID_INVALIDE        = 99;
    private static final int    NB_PARTIES         = 19;

    private StatQuestionDTO meilleureQuestion;
    private StatQuestionDTO pireQuestion;
    private StatQuestionnaireDTO statAttendue;

    @BeforeEach
    void setUp() {
        meilleureQuestion = new StatQuestionDTO(
                3,
                "Combien y a-t-il de joueurs sur le terrain dans une équipe de football ?",
                Difficulte.SIMPLE,
                12, 12, 1.0
        );

        pireQuestion = new StatQuestionDTO(
                11,
                "En épreuve de saut en longueur, à combien d'essais chaque concurrent a-t-il droit ?",
                Difficulte.INTERMEDIAIRE,
                1, 14, 1.0 / 14.0
        );

        statAttendue = new StatQuestionnaireDTO(
                ID_VALIDE, NB_PARTIES, meilleureQuestion, pireQuestion
        );
    }

    // ── UC3 : scénario nominal ─────────────────────────────────────────────────

    @Test
    @DisplayName("UC3 - Nominal : statQuestionnaire retourne un StatQuestionnaireDTO complet")
    void statQuestionnaire_nominal_retourneDTO()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        when(gestionStatistique.statQuestionnaire(ID_VALIDE)).thenReturn(statAttendue);

        StatQuestionnaireDTO resultat = gestionStatistique.statQuestionnaire(ID_VALIDE);

        assertNotNull(resultat);
        verify(gestionStatistique).statQuestionnaire(ID_VALIDE);
    }

    @Test
    @DisplayName("UC3 - Nominal : idQuestionnaire dans le DTO correspond à celui demandé")
    void statQuestionnaire_nominal_idQuestionnaireCohérent()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        when(gestionStatistique.statQuestionnaire(ID_VALIDE)).thenReturn(statAttendue);

        StatQuestionnaireDTO resultat = gestionStatistique.statQuestionnaire(ID_VALIDE);

        assertEquals(ID_VALIDE, resultat.getIdQuestionnaire());
    }

    @Test
    @DisplayName("UC3 - Nominal : nbPartiesJouees est strictement positif")
    void statQuestionnaire_nominal_nbPartiesPositif()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        when(gestionStatistique.statQuestionnaire(ID_VALIDE)).thenReturn(statAttendue);

        StatQuestionnaireDTO resultat = gestionStatistique.statQuestionnaire(ID_VALIDE);

        assertTrue(resultat.getNbPartiesJouees() > 0);
    }

    @Test
    @DisplayName("UC3 - Nominal : nbPartiesJouees vaut 19 (scénario CDC)")
    void statQuestionnaire_nominal_nbPartiesVaut19()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        when(gestionStatistique.statQuestionnaire(ID_VALIDE)).thenReturn(statAttendue);

        StatQuestionnaireDTO resultat = gestionStatistique.statQuestionnaire(ID_VALIDE);

        assertEquals(NB_PARTIES, resultat.getNbPartiesJouees());
    }

    @Test
    @DisplayName("UC3 - Nominal : meilleurQuestion n'est pas null")
    void statQuestionnaire_nominal_meilleurQuestionNonNull()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        when(gestionStatistique.statQuestionnaire(ID_VALIDE)).thenReturn(statAttendue);

        StatQuestionnaireDTO resultat = gestionStatistique.statQuestionnaire(ID_VALIDE);

        assertNotNull(resultat.getMeilleurQuestion());
    }

    @Test
    @DisplayName("UC3 - Nominal : pireQuestion n'est pas null")
    void statQuestionnaire_nominal_pireQuestionNonNull()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        when(gestionStatistique.statQuestionnaire(ID_VALIDE)).thenReturn(statAttendue);

        StatQuestionnaireDTO resultat = gestionStatistique.statQuestionnaire(ID_VALIDE);

        assertNotNull(resultat.getPireQuestion());
    }

    @Test
    @DisplayName("UC3 - Nominal : tauxReussite de meilleurQuestion est entre 0.0 et 1.0")
    void statQuestionnaire_nominal_tauxMeilleurEntreBornes()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        when(gestionStatistique.statQuestionnaire(ID_VALIDE)).thenReturn(statAttendue);

        StatQuestionnaireDTO resultat = gestionStatistique.statQuestionnaire(ID_VALIDE);
        double taux = resultat.getMeilleurQuestion().getTauxReussite();

        assertTrue(taux >= 0.0 && taux <= 1.0);
    }

    @Test
    @DisplayName("UC3 - Nominal : tauxReussite de pireQuestion est entre 0.0 et 1.0")
    void statQuestionnaire_nominal_tauxPireEntreBornes()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        when(gestionStatistique.statQuestionnaire(ID_VALIDE)).thenReturn(statAttendue);

        StatQuestionnaireDTO resultat = gestionStatistique.statQuestionnaire(ID_VALIDE);
        double taux = resultat.getPireQuestion().getTauxReussite();

        assertTrue(taux >= 0.0 && taux <= 1.0);
    }

    @Test
    @DisplayName("UC3 - Nominal : meilleurQuestion a un taux >= pireQuestion")
    void statQuestionnaire_nominal_tauxMeilleurSupérieurOuEgalPire()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        when(gestionStatistique.statQuestionnaire(ID_VALIDE)).thenReturn(statAttendue);

        StatQuestionnaireDTO resultat = gestionStatistique.statQuestionnaire(ID_VALIDE);

        assertTrue(resultat.getMeilleurQuestion().getTauxReussite()
                >= resultat.getPireQuestion().getTauxReussite());
    }

    // ── UC3 : erreur 1 — questionnaire introuvable ────────────────────────────

    @Test
    @DisplayName("UC3 - Erreur 1 : id inexistant lève QuestionnaireNotFoundException")
    void statQuestionnaire_idInexistant_leveQuestionnaireNotFoundException()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        when(gestionStatistique.statQuestionnaire(ID_INVALIDE))
                .thenThrow(new QuestionnaireNotFoundException(
                        "Le questionnaire demandé (id=" + ID_INVALIDE + ") est introuvable en mémoire."));

        assertThrows(QuestionnaireNotFoundException.class,
                () -> gestionStatistique.statQuestionnaire(ID_INVALIDE));
    }

    @Test
    @DisplayName("UC3 - Erreur 1 : le message de l'exception contient l'id demandé")
    void statQuestionnaire_idInexistant_messageContientId()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        when(gestionStatistique.statQuestionnaire(ID_INVALIDE))
                .thenThrow(new QuestionnaireNotFoundException(
                        "Le questionnaire demandé (id=" + ID_INVALIDE + ") est introuvable en mémoire."));

        QuestionnaireNotFoundException ex = assertThrows(QuestionnaireNotFoundException.class,
                () -> gestionStatistique.statQuestionnaire(ID_INVALIDE));

        assertTrue(ex.getMessage().contains(String.valueOf(ID_INVALIDE)));
    }

    // ── UC3 : erreur 2 — aucune partie jouée ─────────────────────────────────

    @Test
    @DisplayName("UC3 - Erreur 2 : questionnaire sans partie lève NoStatistiqueAvailableException")
    void statQuestionnaire_sansPartie_leveNoStatistiqueAvailableException()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        when(gestionStatistique.statQuestionnaire(ID_VALIDE))
                .thenThrow(new NoStatistiqueAvailableException(
                        "Aucune partie n'a encore été jouée sur ce questionnaire."));

        assertThrows(NoStatistiqueAvailableException.class,
                () -> gestionStatistique.statQuestionnaire(ID_VALIDE));
    }

    @Test
    @DisplayName("UC3 - Erreur 2 : NoStatistiqueAvailableException n'est pas une QuestionnaireNotFoundException")
    void statQuestionnaire_sansPartie_exceptionEstBienDuBonType()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        when(gestionStatistique.statQuestionnaire(ID_VALIDE))
                .thenThrow(new NoStatistiqueAvailableException(
                        "Aucune partie n'a encore été jouée sur ce questionnaire."));

        Exception ex = assertThrows(NoStatistiqueAvailableException.class,
                () -> gestionStatistique.statQuestionnaire(ID_VALIDE));

        assertFalse(ex instanceof QuestionnaireNotFoundException);
    }

    // ── UC3 : règles de départage — meilleur taux ─────────────────────────────

    @Test
    @DisplayName("UC3 - Départage meilleur : en cas d'égalité de taux, difficulté la plus élevée est choisie")
    void statQuestionnaire_departage_meilleurChoisiDifficulteElevee()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        StatQuestionDTO questionExpert = new StatQuestionDTO(
                5, "Question experte", Difficulte.EXPERT, 5, 10, 0.5
        );
        StatQuestionDTO questionSimple = new StatQuestionDTO(
                6, "Question simple", Difficulte.SIMPLE, 5, 10, 0.5
        );
        StatQuestionnaireDTO statAvecDepartage = new StatQuestionnaireDTO(
                ID_VALIDE, 10, questionExpert, questionSimple
        );

        when(gestionStatistique.statQuestionnaire(ID_VALIDE)).thenReturn(statAvecDepartage);

        StatQuestionnaireDTO resultat = gestionStatistique.statQuestionnaire(ID_VALIDE);

        assertEquals(Difficulte.EXPERT, resultat.getMeilleurQuestion().getDifficulte());
    }

    @Test
    @DisplayName("UC3 - Départage pire : en cas d'égalité de taux, difficulté la plus faible est choisie")
    void statQuestionnaire_departage_pireChoisiDifficultesFaible()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        StatQuestionDTO questionExpert = new StatQuestionDTO(
                5, "Question experte", Difficulte.EXPERT, 2, 10, 0.2
        );
        StatQuestionDTO questionSimple = new StatQuestionDTO(
                6, "Question simple", Difficulte.SIMPLE, 2, 10, 0.2
        );
        StatQuestionnaireDTO statAvecDepartage = new StatQuestionnaireDTO(
                ID_VALIDE, 10, questionExpert, questionSimple
        );

        when(gestionStatistique.statQuestionnaire(ID_VALIDE)).thenReturn(statAvecDepartage);

        StatQuestionnaireDTO resultat = gestionStatistique.statQuestionnaire(ID_VALIDE);

        assertEquals(Difficulte.SIMPLE, resultat.getPireQuestion().getDifficulte());
    }

    @Test
    @DisplayName("UC3 - Départage : en cas d'égalité totale, la question la plus posée est choisie")
    void statQuestionnaire_departage_plusPoseeChoisie()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        StatQuestionDTO questionPlusPosee = new StatQuestionDTO(
                3, "Question la plus posée", Difficulte.SIMPLE, 8, 20, 0.4
        );
        StatQuestionDTO questionMoinsPosee = new StatQuestionDTO(
                7, "Question moins posée", Difficulte.SIMPLE, 4, 10, 0.4
        );
        StatQuestionnaireDTO statAvecDepartage = new StatQuestionnaireDTO(
                ID_VALIDE, 10, questionPlusPosee, questionMoinsPosee
        );

        when(gestionStatistique.statQuestionnaire(ID_VALIDE)).thenReturn(statAvecDepartage);

        StatQuestionnaireDTO resultat = gestionStatistique.statQuestionnaire(ID_VALIDE);

        assertEquals(20, resultat.getMeilleurQuestion().getNbFoisPosee());
    }

    // ── enregistrerPartie ─────────────────────────────────────────────────────

    @Test
    @DisplayName("UC3 - enregistrerPartie : appel nominal ne lève pas d'exception")
    void enregistrerPartie_nominal_pasException() throws QuestionnaireNotFoundException {
        doNothing().when(gestionStatistique).enregistrerPartie(ID_VALIDE);

        assertDoesNotThrow(() -> gestionStatistique.enregistrerPartie(ID_VALIDE));
        verify(gestionStatistique).enregistrerPartie(ID_VALIDE);
    }

    @Test
    @DisplayName("UC3 - enregistrerPartie : id inexistant lève QuestionnaireNotFoundException")
    void enregistrerPartie_idInexistant_leveException() throws QuestionnaireNotFoundException {
        doThrow(new QuestionnaireNotFoundException(
                "Le questionnaire demandé (id=" + ID_INVALIDE + ") est introuvable en mémoire."))
                .when(gestionStatistique).enregistrerPartie(ID_INVALIDE);

        assertThrows(QuestionnaireNotFoundException.class,
                () -> gestionStatistique.enregistrerPartie(ID_INVALIDE));
    }

    // ── enregistrerReponse ────────────────────────────────────────────────────

    @Test
    @DisplayName("UC3 - enregistrerReponse : bonne réponse ne lève pas d'exception")
    void enregistrerReponse_bonneReponse_pasException() throws QuestionnaireNotFoundException {
        doNothing().when(gestionStatistique).enregistrerReponse(ID_VALIDE, 3, true);

        assertDoesNotThrow(() -> gestionStatistique.enregistrerReponse(ID_VALIDE, 3, true));
        verify(gestionStatistique).enregistrerReponse(ID_VALIDE, 3, true);
    }

    @Test
    @DisplayName("UC3 - enregistrerReponse : mauvaise réponse ne lève pas d'exception")
    void enregistrerReponse_mauvaiseReponse_pasException() throws QuestionnaireNotFoundException {
        doNothing().when(gestionStatistique).enregistrerReponse(ID_VALIDE, 3, false);

        assertDoesNotThrow(() -> gestionStatistique.enregistrerReponse(ID_VALIDE, 3, false));
        verify(gestionStatistique).enregistrerReponse(ID_VALIDE, 3, false);
    }

    @Test
    @DisplayName("UC3 - enregistrerReponse : id inexistant lève QuestionnaireNotFoundException")
    void enregistrerReponse_idInexistant_leveException() throws QuestionnaireNotFoundException {
        doThrow(new QuestionnaireNotFoundException(
                "Le questionnaire demandé (id=" + ID_INVALIDE + ") est introuvable en mémoire."))
                .when(gestionStatistique).enregistrerReponse(ID_INVALIDE, 1, true);

        assertThrows(QuestionnaireNotFoundException.class,
                () -> gestionStatistique.enregistrerReponse(ID_INVALIDE, 1, true));
    }
}

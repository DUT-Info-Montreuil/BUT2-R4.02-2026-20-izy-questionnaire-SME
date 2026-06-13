import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.StatQuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.impls.GestionStatistiqueQuestionnaireImpl;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.enums.Difficulte;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.NoStatistiqueAvailableException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.QuestionnaireNotFoundException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests unitaires du UC3 : Fournir les statistiques d'un questionnaire.
 *
 * <p>Stratégie : on instancie {@link GestionStatistiqueQuestionnaireImpl}
 * avec de vrais {@link QuestionnaireDTO}. L'état est alimenté via
 * {@code enregistrerPartie} et {@code enregistrerReponse} avant chaque
 * appel à {@code statQuestionnaire}.
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
class GestionStatistiqueQuestionnaireTest {

    private static final int ID_VALIDE   = 1;
    private static final int ID_INVALIDE = 99;

    private List<QuestionnaireDTO>              listeQuestionnaires;
    private GestionStatistiqueQuestionnaireImpl service;

    // ── Questions du questionnaire 1 (alignées sur le CDC) ───────────────────

    private QuestionDTO q03;  // SIMPLE       — sera la meilleure (taux 100%)
    private QuestionDTO q11;  // INTERMEDIAIRE — sera la pire     (taux ~7%)

    @BeforeEach
    void setUp() {

        QuestionnaireDTO questionnaire1 = new QuestionnaireDTO(ID_VALIDE, "Sport niv 1");

        q03 = new QuestionDTO(3, "fr",
                "Combien y a-t-il de joueurs sur le terrain dans une équipe de football ?",
                "Onze", Difficulte.SIMPLE,
                "Codifié par les Britanniques à la fin du XIXe siècle.",
                "https://fr.wikipedia.org/wiki/Football");

        q11 = new QuestionDTO(11, "fr",
                "En épreuve de saut en longueur, à combien d'essais chaque concurrent a-t-il droit ?",
                "Trois", Difficulte.INTERMEDIAIRE,
                "Le record du monde masculin est détenu par Mike Powell.",
                "https://fr.wikipedia.org/wiki/Saut_en_longueur");

        questionnaire1.ajouterQuestion(q03);
        questionnaire1.ajouterQuestion(q11);

        listeQuestionnaires = new ArrayList<>();
        listeQuestionnaires.add(questionnaire1);

        service = new GestionStatistiqueQuestionnaireImpl(listeQuestionnaires);
    }

    // ── Méthode utilitaire : rejoue le scénario nominal du CDC ────────────────

    /**
     * Simule 19 parties sur le questionnaire 1 avec les compteurs du CDC :
     * Q03 → 12 bonnes / 12 posées (taux 100 %)
     * Q11 →  1 bonne  / 14 posées (taux ~7 %)
     */
    private void jouerScenarioCDC() throws QuestionnaireNotFoundException {
        for (int i = 0; i < 19; i++) {
            service.enregistrerPartie(ID_VALIDE);
        }
        for (int i = 0; i < 12; i++) {
            service.enregistrerReponse(ID_VALIDE, 3, true);
        }
        service.enregistrerReponse(ID_VALIDE, 11, true);
        for (int i = 0; i < 13; i++) {
            service.enregistrerReponse(ID_VALIDE, 11, false);
        }
    }

    // ── UC3 : scénario nominal ────────────────────────────────────────────────

    @Test
    @DisplayName("UC3 - Nominal : statQuestionnaire retourne un StatQuestionnaireDTO non null")
    void statQuestionnaire_nominal_retourneDTO()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        jouerScenarioCDC();

        assertNotNull(service.statQuestionnaire(ID_VALIDE));
    }

    @Test
    @DisplayName("UC3 - Nominal : idQuestionnaire dans le DTO correspond à celui demandé")
    void statQuestionnaire_nominal_idQuestionnaireCohérent()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        jouerScenarioCDC();

        assertEquals(ID_VALIDE, service.statQuestionnaire(ID_VALIDE).getIdQuestionnaire());
    }

    @Test
    @DisplayName("UC3 - Nominal : nbPartiesJouees vaut 19 (scénario CDC)")
    void statQuestionnaire_nominal_nbPartiesVaut19()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        jouerScenarioCDC();

        assertEquals(19, service.statQuestionnaire(ID_VALIDE).getNbPartiesJouees());
    }

    @Test
    @DisplayName("UC3 - Nominal : meilleurQuestion n'est pas null")
    void statQuestionnaire_nominal_meilleurQuestionNonNull()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        jouerScenarioCDC();

        assertNotNull(service.statQuestionnaire(ID_VALIDE).getMeilleurQuestion());
    }

    @Test
    @DisplayName("UC3 - Nominal : pireQuestion n'est pas null")
    void statQuestionnaire_nominal_pireQuestionNonNull()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        jouerScenarioCDC();

        assertNotNull(service.statQuestionnaire(ID_VALIDE).getPireQuestion());
    }

    @Test
    @DisplayName("UC3 - Nominal : meilleurQuestion est Q03 (taux 100%)")
    void statQuestionnaire_nominal_meilleurQuestionEstQ03()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        jouerScenarioCDC();

        assertEquals(3, service.statQuestionnaire(ID_VALIDE).getMeilleurQuestion().getNumQuestion());
    }

    @Test
    @DisplayName("UC3 - Nominal : pireQuestion est Q11 (taux ~7%)")
    void statQuestionnaire_nominal_pireQuestionEstQ11()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        jouerScenarioCDC();

        assertEquals(11, service.statQuestionnaire(ID_VALIDE).getPireQuestion().getNumQuestion());
    }

    @Test
    @DisplayName("UC3 - Nominal : taux de meilleurQuestion vaut 1.0 (12/12)")
    void statQuestionnaire_nominal_tauxMeilleurVaut100()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        jouerScenarioCDC();

        assertEquals(1.0,
                service.statQuestionnaire(ID_VALIDE).getMeilleurQuestion().getTauxReussite(),
                0.001);
    }

    @Test
    @DisplayName("UC3 - Nominal : taux de pireQuestion vaut ~0.071 (1/14)")
    void statQuestionnaire_nominal_tauxPireVaut7pourcent()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        jouerScenarioCDC();

        assertEquals(1.0 / 14.0,
                service.statQuestionnaire(ID_VALIDE).getPireQuestion().getTauxReussite(),
                0.001);
    }

    @Test
    @DisplayName("UC3 - Nominal : meilleurQuestion a un taux >= pireQuestion")
    void statQuestionnaire_nominal_tauxMeilleurSupérieurOuEgalPire()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        jouerScenarioCDC();

        StatQuestionnaireDTO resultat = service.statQuestionnaire(ID_VALIDE);

        assertTrue(resultat.getMeilleurQuestion().getTauxReussite()
                >= resultat.getPireQuestion().getTauxReussite());
    }

    // ── UC3 : erreur 1 — questionnaire introuvable ────────────────────────────

    @Test
    @DisplayName("UC3 - Erreur 1 : id inexistant lève QuestionnaireNotFoundException")
    void statQuestionnaire_idInexistant_leveQuestionnaireNotFoundException() {

        assertThrows(QuestionnaireNotFoundException.class,
                () -> service.statQuestionnaire(ID_INVALIDE));
    }

    @Test
    @DisplayName("UC3 - Erreur 1 : le message de l'exception contient l'id demandé")
    void statQuestionnaire_idInexistant_messageContientId() {

        QuestionnaireNotFoundException ex = assertThrows(QuestionnaireNotFoundException.class,
                () -> service.statQuestionnaire(ID_INVALIDE));

        assertTrue(ex.getMessage().contains(String.valueOf(ID_INVALIDE)));
    }

    // ── UC3 : erreur 2 — aucune partie jouée ─────────────────────────────────

    @Test
    @DisplayName("UC3 - Erreur 2 : questionnaire sans partie lève NoStatistiqueAvailableException")
    void statQuestionnaire_sansPartie_leveNoStatistiqueAvailableException() {

        assertThrows(NoStatistiqueAvailableException.class,
                () -> service.statQuestionnaire(ID_VALIDE));
    }

    @Test
    @DisplayName("UC3 - Erreur 2 : NoStatistiqueAvailableException n'est pas une QuestionnaireNotFoundException")
    void statQuestionnaire_sansPartie_exceptionEstBienDuBonType() {

        Exception ex = assertThrows(NoStatistiqueAvailableException.class,
                () -> service.statQuestionnaire(ID_VALIDE));

        assertFalse(ex instanceof QuestionnaireNotFoundException);
    }

    // ── UC3 : règles de départage ─────────────────────────────────────────────

    @Test
    @DisplayName("UC3 - Départage meilleur : à taux égal, difficulté la plus élevée est choisie")
    void statQuestionnaire_departage_meilleurChoisiDifficulteElevee()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        // Q03 SIMPLE et Q11 INTERMEDIAIRE — même taux 0.5
        service.enregistrerPartie(ID_VALIDE);
        service.enregistrerReponse(ID_VALIDE, 3,  true);
        service.enregistrerReponse(ID_VALIDE, 3,  false);
        service.enregistrerReponse(ID_VALIDE, 11, true);
        service.enregistrerReponse(ID_VALIDE, 11, false);

        StatQuestionnaireDTO resultat = service.statQuestionnaire(ID_VALIDE);

        // INTERMEDIAIRE > SIMPLE → Q11 doit être la meilleure
        assertEquals(11, resultat.getMeilleurQuestion().getNumQuestion());
        assertEquals(Difficulte.INTERMEDIAIRE, resultat.getMeilleurQuestion().getDifficulte());
    }

    @Test
    @DisplayName("UC3 - Départage pire : à taux égal, difficulté la plus faible est choisie")
    void statQuestionnaire_departage_pireChoisiDifficultesFaible()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        // Q03 SIMPLE et Q11 INTERMEDIAIRE — même taux 0.5
        service.enregistrerPartie(ID_VALIDE);
        service.enregistrerReponse(ID_VALIDE, 3,  true);
        service.enregistrerReponse(ID_VALIDE, 3,  false);
        service.enregistrerReponse(ID_VALIDE, 11, true);
        service.enregistrerReponse(ID_VALIDE, 11, false);

        StatQuestionnaireDTO resultat = service.statQuestionnaire(ID_VALIDE);

        // SIMPLE < INTERMEDIAIRE → Q03 doit être la pire
        assertEquals(3, resultat.getPireQuestion().getNumQuestion());
        assertEquals(Difficulte.SIMPLE, resultat.getPireQuestion().getDifficulte());
    }

    @Test
    @DisplayName("UC3 - Départage : à taux et difficulté égaux, la plus posée est choisie comme meilleure")
    void statQuestionnaire_departage_plusPoseeChoisieCommeMeilleure()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        // Ajouter Q05 de même difficulté que Q03 (SIMPLE) pour forcer le départage par nbFoisPosee
        QuestionDTO q05 = new QuestionDTO(5, "fr", "Question simple supplémentaire",
                "Réponse", Difficulte.SIMPLE, "Explication", "http://ref");
        listeQuestionnaires.get(0).ajouterQuestion(q05);

        service.enregistrerPartie(ID_VALIDE);

        // Q03 : 4 fois posée, 2 bonnes → taux 0.5
        service.enregistrerReponse(ID_VALIDE, 3, true);
        service.enregistrerReponse(ID_VALIDE, 3, true);
        service.enregistrerReponse(ID_VALIDE, 3, false);
        service.enregistrerReponse(ID_VALIDE, 3, false);

        // Q05 : 2 fois posée, 1 bonne → taux 0.5, même difficulté SIMPLE
        service.enregistrerReponse(ID_VALIDE, 5, true);
        service.enregistrerReponse(ID_VALIDE, 5, false);

        // Q11 : 1 fois posée, 0 bonne → taux 0.0 (sera la pire)
        service.enregistrerReponse(ID_VALIDE, 11, false);

        StatQuestionnaireDTO resultat = service.statQuestionnaire(ID_VALIDE);

        // Q03 et Q05 : même taux (0.5) et même difficulté (SIMPLE) → Q03 plus posée (4 vs 2)
        assertEquals(3, resultat.getMeilleurQuestion().getNumQuestion());
    }

    // ── enregistrerPartie ─────────────────────────────────────────────────────

    @Test
    @DisplayName("UC3 - enregistrerPartie : appel nominal ne lève pas d'exception")
    void enregistrerPartie_nominal_pasException() {

        assertDoesNotThrow(() -> service.enregistrerPartie(ID_VALIDE));
    }

    @Test
    @DisplayName("UC3 - enregistrerPartie : id inexistant lève QuestionnaireNotFoundException")
    void enregistrerPartie_idInexistant_leveException() {

        assertThrows(QuestionnaireNotFoundException.class,
                () -> service.enregistrerPartie(ID_INVALIDE));
    }

    @Test
    @DisplayName("UC3 - enregistrerPartie : plusieurs appels incrémentent nbPartiesJouees")
    void enregistrerPartie_plusieursAppels_incremente()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        service.enregistrerPartie(ID_VALIDE);
        service.enregistrerPartie(ID_VALIDE);
        service.enregistrerPartie(ID_VALIDE);
        service.enregistrerReponse(ID_VALIDE, 3, true);

        assertEquals(3, service.statQuestionnaire(ID_VALIDE).getNbPartiesJouees());
    }

    // ── enregistrerReponse ────────────────────────────────────────────────────

    @Test
    @DisplayName("UC3 - enregistrerReponse : bonne réponse ne lève pas d'exception")
    void enregistrerReponse_bonneReponse_pasException() {

        assertDoesNotThrow(() -> service.enregistrerReponse(ID_VALIDE, 3, true));
    }

    @Test
    @DisplayName("UC3 - enregistrerReponse : mauvaise réponse ne lève pas d'exception")
    void enregistrerReponse_mauvaiseReponse_pasException() {

        assertDoesNotThrow(() -> service.enregistrerReponse(ID_VALIDE, 3, false));
    }

    @Test
    @DisplayName("UC3 - enregistrerReponse : id inexistant lève QuestionnaireNotFoundException")
    void enregistrerReponse_idInexistant_leveException() {

        assertThrows(QuestionnaireNotFoundException.class,
                () -> service.enregistrerReponse(ID_INVALIDE, 1, true));
    }

    @Test
    @DisplayName("UC3 - enregistrerReponse : nbBonnesReponses est cohérent avec les appels")
    void enregistrerReponse_nbBonnesReponsesCohérent()
            throws QuestionnaireNotFoundException, NoStatistiqueAvailableException {

        service.enregistrerPartie(ID_VALIDE);
        service.enregistrerReponse(ID_VALIDE, 3, true);
        service.enregistrerReponse(ID_VALIDE, 3, true);
        service.enregistrerReponse(ID_VALIDE, 3, false);
        // Q11 doit aussi être posée pour que le service trouve une pire question
        service.enregistrerReponse(ID_VALIDE, 11, false);

        StatQuestionnaireDTO resultat = service.statQuestionnaire(ID_VALIDE);

        assertEquals(2, resultat.getMeilleurQuestion().getNbBonnesReponses());
        assertEquals(3, resultat.getMeilleurQuestion().getNbFoisPosee());
    }
}
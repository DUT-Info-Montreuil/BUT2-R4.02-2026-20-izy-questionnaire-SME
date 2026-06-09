import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock.FileManagerFichierInexistantMock;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock.FileManagerStructureInvalideMock;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock.FileManagerTailleExcessiveMock;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock.FileManagerValideMock;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.FileManager;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FichierIntrouvableException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FileSizeExceededException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.InvalidCSVStructureException;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Tests du contrat de l'interface {@link FileManager}, vérifié via ses mocks.
 *
 * <p>On ne teste pas une implémentation réelle ici, mais le comportement
 * attendu de chaque scénario : chargement réussi et trois cas d'erreur.
 */
class FileManagerTest {

    @Test
    @DisplayName("chargerFichier() retourne une liste non nulle et non vide")
    void chargerFichier_retourneListeNonVide() throws Exception {
        FileManager fileManager = new FileManagerValideMock();

        List<CsvBO> lignes = fileManager.chargerFichier("peu_importe.csv");

        assertAll(
                () -> assertNotNull(lignes, "La liste ne doit pas être null."),
                () -> assertFalse(lignes.isEmpty(), "La liste ne doit pas être vide."),
                () -> assertEquals(4, lignes.size(), "Le mock fournit 4 lignes.")
        );
    }

    @Test
    @DisplayName("chargerFichier() mappe correctement les colonnes de la première ligne")
    void chargerFichier_mappeLesColonnes() throws Exception {
        FileManager fileManager = new FileManagerValideMock();

        CsvBO premiere = fileManager.chargerFichier("peu_importe.csv").get(0);

        assertAll(
                () -> assertEquals("1", premiere.getIdQuestionnaire()),
                () -> assertEquals("Culture générale", premiere.getLibelleQuestionnaire()),
                () -> assertEquals("1", premiere.getNumQuestion()),
                () -> assertEquals("fr", premiere.getLangue()),
                () -> assertEquals("Quelle est la capitale de la France ?", premiere.getLibelleQuestion()),
                () -> assertEquals("Paris", premiere.getReponse()),
                () -> assertEquals("1", premiere.getDifficulte())
        );
    }


    @Test
    @DisplayName("chargerFichier() lève FileNotFoundException si le fichier est introuvable")
    void chargerFichier_fichierInexistant() {
        FileManager fileManager = new FileManagerFichierInexistantMock();

        assertThrows(
                FichierIntrouvableException.class,
                () -> fileManager.chargerFichier("introuvable.csv")
        );
    }

    @Test
    @DisplayName("chargerFichier() lève FileSizeExceededException si le fichier est trop volumineux")
    void chargerFichier_tailleExcessive() {
        FileManager fileManager = new FileManagerTailleExcessiveMock();

        assertThrows(
                FileSizeExceededException.class,
                () -> fileManager.chargerFichier("trop_gros.csv")
        );
    }

    @Test
    @DisplayName("chargerFichier() lève InvalidCSVStructureException si le CSV est invalide")
    void chargerFichier_structureInvalide() {
        FileManager fileManager = new FileManagerStructureInvalideMock();

        assertThrows(
                InvalidCSVStructureException.class,
                () -> fileManager.chargerFichier("vide.csv")
        );
    }
}

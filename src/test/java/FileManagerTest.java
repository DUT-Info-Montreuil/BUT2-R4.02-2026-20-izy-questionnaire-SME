import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.impls.FileManagerImpl;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FichierIntrouvableException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FileSizeExceededException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.InvalidCSVStructureException;

import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FileManagerTest {

    @TempDir
    Path tempDir;

    private final FileManagerImpl fileManager = new FileManagerImpl();

    private String cheminRessource(String nom) throws Exception {
        return Paths.get(getClass().getClassLoader().getResource(nom).toURI()).toString();
    }

    @Test
    @DisplayName("chargerFichier() retourne une liste non nulle et non vide")
    void chargerFichier_retourneListeNonVide() throws Exception {
        List<CsvBO> lignes = fileManager.chargerFichier(cheminRessource("test_valide.csv"));

        assertAll(
                () -> assertNotNull(lignes, "La liste ne doit pas être null."),
                () -> assertFalse(lignes.isEmpty(), "La liste ne doit pas être vide."),
                () -> assertEquals(4, lignes.size(), "Le fichier de test fournit 4 lignes.")
        );
    }

    @Test
    @DisplayName("chargerFichier() mappe correctement les colonnes de la première ligne")
    void chargerFichier_mappeLesColonnes() throws Exception {
        CsvBO premiere = fileManager.chargerFichier(cheminRessource("test_valide.csv")).get(0);

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
    @DisplayName("chargerFichier() lève FichierIntrouvableException si le fichier est introuvable")
    void chargerFichier_fichierInexistant() {
        assertThrows(
                FichierIntrouvableException.class,
                () -> fileManager.chargerFichier("fichier_qui_nexiste_pas.csv")
        );
    }

    @Test
    @DisplayName("chargerFichier() lève FileSizeExceededException si le fichier est trop volumineux")
    void chargerFichier_tailleExcessive() throws Exception {
        Path grosFile = tempDir.resolve("trop_gros.csv");
        byte[] chunk = new byte[1024 * 1024]; // 1 Mo
        try (OutputStream os = Files.newOutputStream(grosFile)) {
            for (int i = 0; i < 6; i++) {
                os.write(chunk); // 6 Mo au total > limite de 5 Mo
            }
        }

        assertThrows(
                FileSizeExceededException.class,
                () -> fileManager.chargerFichier(grosFile.toString())
        );
    }

    @Test
    @DisplayName("chargerFichier() lève InvalidCSVStructureException si le CSV est vide")
    void chargerFichier_structureInvalide() throws Exception {
        Path videFile = tempDir.resolve("vide.csv");
        Files.createFile(videFile);

        assertThrows(
                InvalidCSVStructureException.class,
                () -> fileManager.chargerFichier(videFile.toString())
        );
    }
}

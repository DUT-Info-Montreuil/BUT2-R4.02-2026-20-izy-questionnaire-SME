package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.impls;

import com.opencsv.bean.CsvToBeanBuilder;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.FileManager;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FichierIntrouvableException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.FileSizeExceededException;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.InvalidCSVStructureException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

/**
 * Implémentation de {@link FileManager}.
 *
 * <p>Applique les quatre vérifications en cascade définies dans le
 * dossier de conception : existence → taille → structure → chargement.
 *
 * <p>Les sous-fonctions {@link #exists(String)}, {@link #getFileSize(String)}
 * correspondent aux signatures de la section B du dossier (FileManager).
 * La sous-fonction {@link #parse(String)} correspond à la section C
 * (CSVParser), fusionnée ici car OpenCSV combine parse et loadToMemory
 * en une seule opération.
 */
public class FileManagerImpl implements FileManager {

    /** Taille maximale autorisée : 5 Mo. */
    private static final long MAX_SIZE = 5L * 1024L * 1024L;

    // ── Méthode publique — contrat de l'interface ─────────────────────────────

    @Override
    public List<CsvBO> chargerFichier(String filePath)
            throws FichierIntrouvableException,
            FileSizeExceededException,
            InvalidCSVStructureException {

        Objects.requireNonNull(filePath, "filePath ne peut pas être null.");
        System.out.println("Chargement du fichier : " + filePath);

        // Étape 1 — Section B du dossier : exists()
        System.out.print("Vérification de l'existence .............. ");
        if (!exists(filePath)) {
            System.out.println("ECHEC");
            throw new FichierIntrouvableException(filePath);
        }
        System.out.println("OK");

        // Étape 2 — Section B du dossier : getFileSize()
        long taille = getFileSize(filePath);
        System.out.printf("Vérification de la taille (%d Ko / %d Mo) . ",
                taille / 1024L,
                MAX_SIZE / (1024L * 1024L));
        if (taille > MAX_SIZE) {
            System.out.println("ECHEC");
            throw new FileSizeExceededException(
                    "Taille du fichier (" + taille + " octets) dépasse la limite autorisée (" + MAX_SIZE + " octets)."
            );
        }
        System.out.println("OK");

        // Étape 3 — Section C du dossier : parse() + loadToMemory() fusionnés
        System.out.print("Validation de la structure CSV ........... ");
        List<CsvBO> lignes = parse(filePath);
        System.out.println("OK");

        System.out.printf("Chargement en mémoire ..................... OK%n");

        return lignes;
    }

    // ── Sous-fonctions Section B — FileManager (dossier de conception) ────────

    /**
     * Vérifie si le fichier existe et est un fichier régulier.
     * Correspond à {@code exists(filePath)} du dossier de conception.
     *
     * @param filePath chemin vers le fichier
     * @return {@code true} si le fichier existe
     */
    private boolean exists(String filePath) {
        File fichier = new File(filePath);
        return fichier.exists() && fichier.isFile();
    }

    /**
     * Retourne la taille du fichier en octets.
     * Correspond à {@code getFileSize(filePath)} du dossier de conception.
     *
     * @param filePath chemin vers le fichier
     * @return taille en octets
     */
    private long getFileSize(String filePath) {
        return new File(filePath).length();
    }

    // ── Sous-fonction Section C — CSVParser (dossier de conception) ──────────

    /**
     * Lit et parse le fichier CSV avec OpenCSV, mappe chaque ligne sur
     * un {@link CsvBO} via {@code @CsvBindByPosition}.
     *
     * <p>Correspond à {@code parse(filePath)} + {@code loadToMemory()}
     * du dossier de conception — fusionnés en une seule opération car
     * OpenCSV lit et charge en mémoire simultanément.
     *
     * <p>Gère le BOM UTF-8 ({@code \uFEFF}) ajouté par certains
     * éditeurs Windows en lisant et ignorant le premier caractère
     * s'il correspond au BOM.
     *
     * @param filePath chemin vers le fichier CSV
     * @return liste de {@link CsvBO} chargée en mémoire
     * @throws InvalidCSVStructureException si le fichier est vide ou illisible
     */
    private List<CsvBO> parse(String filePath) throws InvalidCSVStructureException {
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            // Suppression du BOM (\uFEFF) si présent
            bufferedReader.mark(1);
            int premierChar = bufferedReader.read();
            if (premierChar != '\uFEFF') {
                bufferedReader.reset();
            }

            List<CsvBO> lignes = new CsvToBeanBuilder<CsvBO>(bufferedReader)
                    .withType(CsvBO.class)
                    .withSeparator(';')
                    .withSkipLines(0)
                    .build()
                    .parse();

            if (lignes == null || lignes.isEmpty()) {
                throw new InvalidCSVStructureException("Le fichier est vide.");
            }

            return lignes;

        } catch (IOException e) {
            throw new InvalidCSVStructureException(
                    "Impossible de lire le fichier : " + e.getMessage()
            );
        }
    }
}
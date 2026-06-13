package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mappers;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Mapper stateless : convertit une {@code List<CsvBO>} en
 * {@code List<QuestionnaireDTO>}.
 *
 * <p>La liste de {@link CsvBO} est déjà entièrement en mémoire —
 * ce mapper ne lit jamais le fichier CSV une seconde fois.
 * Il parcourt la liste une seule fois pour construire la structure.
 *
 * <p>Invariant : chaque {@link QuestionnaireDTO} produit contient
 * au moins une {@link QuestionDTO} (garanti par le format CSV).
 */
public final class CsvBOToQuestionnaireMapper {

    /** BOM UTF-8 pouvant apparaître sur le premier champ du fichier. */
    private static final char BOM = '\uFEFF';

    private CsvBOToQuestionnaireMapper() {
        throw new UnsupportedOperationException("Classe utilitaire.");
    }

    /**
     * Convertit une liste de {@link CsvBO} en liste de {@link QuestionnaireDTO}.
     *
     * <p>Le fichier est parcouru <strong>une seule fois</strong> :
     * chaque ligne est convertie en {@link QuestionDTO} et rattachée
     * au {@link QuestionnaireDTO} correspondant via son {@code idQuestionnaire}.
     * L'ordre d'apparition dans le CSV est conservé (LinkedHashMap).
     *
     * @param lignes liste de {@link CsvBO} déjà chargée en mémoire,
     *               ne doit pas être {@code null}
     * @return liste ordonnée de {@link QuestionnaireDTO},
     *         chacun contenant au moins une question
     */
    public static List<QuestionnaireDTO> map(List<CsvBO> lignes) {
        Objects.requireNonNull(lignes, "La liste de CsvBO ne peut pas être null.");
        // LinkedHashMap : préserve l'ordre d'apparition dans le CSV
        Map<Integer, QuestionnaireDTO> questionnaires = new LinkedHashMap<>();
        for (CsvBO csvBO : lignes) {
            // Suppression défensive du BOM sur le premier champ
            String idBrut = csvBO.getIdQuestionnaire().trim();
            if (!idBrut.isEmpty() && idBrut.charAt(0) == BOM) {
                idBrut = idBrut.substring(1);
            }
            int id = Integer.parseInt(idBrut);
            String libelle = csvBO.getLibelleQuestionnaire().trim();
            // Crée le QuestionnaireDTO uniquement à la première rencontre de cet id
            questionnaires.computeIfAbsent(id, k -> new QuestionnaireDTO(id, libelle));
            // Convertit la ligne en QuestionDTO et l'ajoute — une seule lecture du CSV
            QuestionDTO question = CsvBOToQuestionMapper.map(csvBO);
            questionnaires.get(id).ajouterQuestion(question);
        }

        return new ArrayList<>(questionnaires.values());
    }
}
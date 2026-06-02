package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mappers;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;

import java.util.Objects;

/**
 * Mapper stateless : convertit un {@link CsvBO} en {@link QuestionDTO}.
 *
 * <p>Le {@link CsvBO} est déjà en mémoire — ce mapper ne lit jamais
 * le fichier CSV. Il transforme uniquement les données reçues.
 */
public final class CsvBOToQuestionMapper {
    private CsvBOToQuestionMapper() {
        throw new UnsupportedOperationException("Classe utilitaire.");
    }
    /**
     * Convertit un {@link CsvBO} en {@link QuestionDTO}.
     *
     * <p>Tous les champs String sont nettoyés ({@code trim()}) pour
     * éliminer les espaces parasites et le BOM éventuel.
     *
     * @param csvBO ligne CSV brute, ne doit pas être {@code null}
     * @return le {@link QuestionDTO} correspondant
     */
    public static QuestionDTO map(CsvBO csvBO) {
        Objects.requireNonNull(csvBO, "csvBO ne peut pas être null.");
        return new QuestionDTO(
                Integer.parseInt(csvBO.getNumQuestion().trim()),
                csvBO.getLangue().trim(),
                csvBO.getLibelleQuestion().trim(),
                csvBO.getReponse().trim(),
                Integer.parseInt(csvBO.getDifficulte().trim()),
                csvBO.getExplication().trim(),
                csvBO.getReference().trim()
        );
    }
}
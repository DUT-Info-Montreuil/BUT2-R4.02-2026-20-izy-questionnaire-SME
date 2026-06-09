package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.impls;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.GestionListeQuestionnaire;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.NoQuestionnaireAvailableException;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implémentation de {@link GestionListeQuestionnaire}.
 *
 * <p>Reçoit en injection la liste des questionnaires déjà chargés et
 * mappés par l'appelant. Son seul rôle est de la fournir ou de lever
 * {@link NoQuestionnaireAvailableException} si elle est vide.
 */
public class GestionListeQuestionnaireImpl implements GestionListeQuestionnaire {

    private final List<QuestionnaireDTO> listeQuestionnaires;

    /**
     * @param listeQuestionnaires liste partagée avec l'appelant ; ne doit pas être {@code null}
     */
    public GestionListeQuestionnaireImpl(List<QuestionnaireDTO> listeQuestionnaires) {
        this.listeQuestionnaires = Objects.requireNonNull(listeQuestionnaires, "listeQuestionnaires ne peut pas être null.");
    }

    @Override
    public List<QuestionnaireDTO> fournirListeQuestionnaire() throws NoQuestionnaireAvailableException {
        if (listeQuestionnaires.isEmpty()) {
            throw new NoQuestionnaireAvailableException("Aucun questionnaire disponible.");
        }
        return Collections.unmodifiableList(listeQuestionnaires);
    }
}

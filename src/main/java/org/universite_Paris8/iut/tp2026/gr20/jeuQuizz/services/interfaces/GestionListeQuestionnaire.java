package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.dtos.QuestionnaireDTO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions.NoQuestionnaireAvailableException;

import java.util.List;

public interface GestionListeQuestionnaire {

    public List<QuestionnaireDTO> fournirListeQuestionnaire() throws NoQuestionnaireAvailableException;

}

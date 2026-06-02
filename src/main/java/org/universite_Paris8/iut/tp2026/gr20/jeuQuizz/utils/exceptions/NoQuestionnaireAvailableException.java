package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions;

public class NoQuestionnaireAvailableException extends Exception {

    public NoQuestionnaireAvailableException() {
        super("Aucun questionnaire disponible en mémoire. " +
                "Aucun fichier CSV valide n'a été chargé au démarrage.");
    }
}

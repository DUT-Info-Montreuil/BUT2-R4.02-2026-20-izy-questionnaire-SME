package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions;

public class InvalidCSVStructureException extends Exception {

    public InvalidCSVStructureException(String detail) {
        super("Structure du fichier CSV incorrecte : " + detail);
    }
}
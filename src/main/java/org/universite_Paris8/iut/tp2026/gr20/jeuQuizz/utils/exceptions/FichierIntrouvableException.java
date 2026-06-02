package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions;

public class FichierIntrouvableException extends Exception {

    public FichierIntrouvableException(String filePath) {
        super("Fichier introuvable sur le système de fichiers : \"" + filePath + "\".");
    }
}
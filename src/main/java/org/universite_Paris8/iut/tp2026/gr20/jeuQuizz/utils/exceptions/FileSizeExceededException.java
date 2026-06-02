package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.exceptions;

public class FileSizeExceededException extends Exception {

    private final long tailleFichier;
    private final long tailleMax;

    public FileSizeExceededException(long tailleFichier, long tailleMax) {
        super(String.format(
                "Taille maximale dépassée : fichier = %d Mo, limite = %d Mo.",
                tailleFichier / (1024L * 1024L),
                tailleMax     / (1024L * 1024L)
        ));
        this.tailleFichier = tailleFichier;
        this.tailleMax     = tailleMax;
    }

    public long getTailleFichier() { return tailleFichier; }
    public long getTailleMax()     { return tailleMax; }
}
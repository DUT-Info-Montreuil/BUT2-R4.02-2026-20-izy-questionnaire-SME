package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.utils.enums;

/**
 * Représente les niveaux de difficulté d'une question de quizz.
 */
public enum Difficulte {

    SIMPLE(1),
    INTERMEDIAIRE(2),
    EXPERT(3);

    private final int niveau;

    Difficulte(int niveau) {
        this.niveau = niveau;
    }

    public int getNiveau() {
        return niveau;
    }

    /**
     * Convertit un entier en {@code Difficulte} correspondant.
     *
     * @param n valeur numérique (1, 2 ou 3)
     * @return l'instance {@code Difficulte} correspondante
     * @throws IllegalArgumentException si {@code n} ne correspond à aucun niveau connu
     */
    public static Difficulte fromInt(int n) {
        for (Difficulte d : values()) {
            if (d.niveau == n) {
                return d;
            }
        }
        throw new IllegalArgumentException(
                "Niveau de difficulté inconnu : " + n +
                        ". Valeurs acceptées : 1 (SIMPLE), 2 (INTERMEDIAIRE), 3 (EXPERT)."
        );
    }
}

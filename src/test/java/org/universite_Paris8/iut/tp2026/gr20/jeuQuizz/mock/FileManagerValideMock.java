package org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.mock;

import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.entities.mos.CsvBO;
import org.universite_Paris8.iut.tp2026.gr20.jeuQuizz.services.interfaces.FileManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de test de {@link FileManager} simulant un chargement réussi.
 *
 * <p>{@code chargerFichier()} retourne toujours une liste de {@link CsvBO}
 * cohérente (jamais {@code null}, jamais vide), indépendamment du chemin passé.
 * Les données couvrent deux questionnaires et les trois niveaux de difficulté.
 */
public class FileManagerValideMock implements FileManager {

    @Override
    public List<CsvBO> chargerFichier(String filePath) {
        List<CsvBO> lignes = new ArrayList<>();

        // ── Questionnaire 1 : Culture générale, question 1 ──────────────────
        CsvBO ligne1 = new CsvBO();
        ligne1.setIdQuestionnaire("1");
        ligne1.setLibelleQuestionnaire("Culture générale");
        ligne1.setNumQuestion("1");
        ligne1.setLangue("fr");
        ligne1.setLibelleQuestion("Quelle est la capitale de la France ?");
        ligne1.setReponse("Paris");
        ligne1.setDifficulte("1");
        ligne1.setExplication("Paris est la capitale de la France depuis le Xe siècle.");
        ligne1.setReference("https://fr.wikipedia.org/wiki/Paris");
        lignes.add(ligne1);

        // ── Questionnaire 1 : Culture générale, question 2 ──────────────────
        CsvBO ligne2 = new CsvBO();
        ligne2.setIdQuestionnaire("1");
        ligne2.setLibelleQuestionnaire("Culture générale");
        ligne2.setNumQuestion("2");
        ligne2.setLangue("fr");
        ligne2.setLibelleQuestion("Combien de continents compte la Terre ?");
        ligne2.setReponse("7");
        ligne2.setDifficulte("2");
        ligne2.setExplication("On dénombre traditionnellement sept continents.");
        ligne2.setReference("https://fr.wikipedia.org/wiki/Continent");
        lignes.add(ligne2);

        // ── Questionnaire 2 : Informatique, question 1 ──────────────────────
        CsvBO ligne3 = new CsvBO();
        ligne3.setIdQuestionnaire("2");
        ligne3.setLibelleQuestionnaire("Informatique");
        ligne3.setNumQuestion("1");
        ligne3.setLangue("fr");
        ligne3.setLibelleQuestion("Que signifie l'acronyme CPU ?");
        ligne3.setReponse("Central Processing Unit");
        ligne3.setDifficulte("1");
        ligne3.setExplication("Le CPU est l'unité centrale de traitement.");
        ligne3.setReference("https://fr.wikipedia.org/wiki/Processeur");
        lignes.add(ligne3);

        // ── Questionnaire 2 : Informatique, question 2 ──────────────────────
        CsvBO ligne4 = new CsvBO();
        ligne4.setIdQuestionnaire("2");
        ligne4.setLibelleQuestionnaire("Informatique");
        ligne4.setNumQuestion("2");
        ligne4.setLangue("fr");
        ligne4.setLibelleQuestion("Quel paradigme repose sur l'encapsulation et l'héritage ?");
        ligne4.setReponse("La programmation orientée objet");
        ligne4.setDifficulte("3");
        ligne4.setExplication("La POO structure le code autour d'objets et de classes.");
        ligne4.setReference("https://fr.wikipedia.org/wiki/Programmation_orientée_objet");
        lignes.add(ligne4);

        return lignes;
    }
}

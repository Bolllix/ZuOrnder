package app;

import java.util.*;
import model.*;
import rules.*;


public class AppLogic {                                                                             // Erstellt eine Matrix mit allen Möglichen Personen und Mattratzen Kombinationen. Lässt Regeln drüberlaufen die Punkte vergeben. Die BEste Kombination aus PErsonen und Matratzen mit den meisten Punkten wird ausgewählt
    AppState appState;

        public void runAssignment(AppState appState) {
            Cell[][] scoringMatrix = createScoringMatrix(appState.building, appState.persons);      // Erstellt eine ScoringMatrix mit einem cell Objekt was Person, Matratze und Punktzahl enthalält
            scoringMatrix = makeMatrixSquared(scoringMatrix);                                       // Da es mehr oder weniger Matratzen als Personen geben kann muss die Matrix Quadratisch gemacht werden (Braucht der Hungarian)
            scoringMatrix = runAllRules(scoringMatrix, appState.rules);
            int[][] justScoreMatrix = createOnlyScoreMatrix(scoringMatrix);                         // Erstellt eine Int Matrix die nur die Punktzahl von den cell Elementen enthält. Muss umgewandelt werden da der Hungarian Algorithmus nur eine einfach Int Matrix nimmt
            int[] result = HungarianSimple.assign(justScoreMatrix);
            assignPoepletoMattresses(result, scoringMatrix);                                        
    
            // Testprints
            System.out.println("scoringMatrix:");
            for (Cell[] row : scoringMatrix) {
                System.out.println(Arrays.toString(row));
            }
            System.out.println("\n");
            System.out.println("justScoreMatrix:" + "\n");
            for (int[] row : justScoreMatrix) {
                System.out.println(Arrays.toString(row));
            }
            System.out.println("\n" + "result:" + "\n");
            System.out.println(Arrays.toString(result));
            System.out.println(appState.building);
        }
    
        Cell[][] createScoringMatrix(Building building, List<Person> personsList) {             // Erstellt eine ScoringMatrix mit einem cell Objekt welche Person, Matratze und Punktzahl enthält                                 
            Cell[][] scoringMatrix = new Cell[personsList.size()][building.allMatList.size()];
            for (int i = 0; i < personsList.size(); i++) {
                for (int j = 0; j < building.allMatList.size(); j++) {
                    Cell z = new Cell();
                    z.person = personsList.get(i);
                    z.mattress = building.allMatList.get(j);
                    z.score = 0;
                    scoringMatrix[i][j] = z;
                }
            }
            return scoringMatrix;
        }
    
        Cell[][] makeMatrixSquared(Cell[][] unsquaredMatrix) {                                  
            int zeilen = unsquaredMatrix.length;
            int spalten = unsquaredMatrix[0].length;
            int size = Math.max(zeilen, spalten);                                               // Schaut sich an wie viel Zeilen und Spalten die Matrix hat, dann das was größer 
            Cell[][] square = new Cell[size][size];                                             // Neue Matrix mit der Größe
            for (int i = 0; i < zeilen; i++) {                                                  // Jetzt werden nur die Zellen kopiert wo auch was in der Unquadratischen Matrix ist. Die anderen sind null;
                for (int j = 0; j < spalten; j++) {
                    square[i][j] = unsquaredMatrix[i][j];
                }
            }
            return square;
        }
    
        Cell[][] runAllRules(Cell[][] scoringMatrix, List<Rule> allRules) {                         
            for (int i = 0; i < scoringMatrix.length; i++) {
                for (int j = 0; j < scoringMatrix.length; j++) {
                    if (scoringMatrix[i][j] == null) continue;                                  // Wenn die Matrix empty ist weiter ansonsten runRulesList
                    runRulesList(scoringMatrix[i][j], allRules);
                }
            }
            return scoringMatrix;
        }
    
        void runRulesList(Cell cell, List<Rule> allRules) {                                     // Lässt alle Regeln über diese Matrarzen und PErsonen Kombination laufen und addiert die Punkte
            for (Rule rule : allRules) {
                cell.score += rule.execute(cell);
            }
        }
    
        int[][] createOnlyScoreMatrix(Cell[][] scoringMatrix) {                                 // Erstellt eine Int Matrix die nur die Punktzahl von den cell Elementen enthält
            int[][] justScoreMatrix = new int[scoringMatrix.length][scoringMatrix.length];
            for (int i = 0; i < scoringMatrix.length; i++) {
                for (int j = 0; j < scoringMatrix.length; j++) {
                    if (scoringMatrix[i][j] == null) continue;
                    justScoreMatrix[i][j] = scoringMatrix[i][j].score;
                }
            }
            return justScoreMatrix;
        }
    
        void assignPoepletoMattresses(int[] result, Cell[][] scoringMatrix) {                   // Hier ist irgendwie ein Fehler
            for (int i = 0; i < scoringMatrix.length; i++) {
                for (int j = 0; j < scoringMatrix.length; j++) {
                    if (scoringMatrix[i][j] == null) continue;
                    if (result[i] == j) scoringMatrix[i][j].assignPerToMattress();              // Setzt die Matratzen im Gebäude auf die richtigen Personen
                }
            }
        }
    }
    

    
    class HungarianSimple {
    
        public static int[] assign(int[][] score) {
    
            int people = score.length;
            int beds = score[0].length;
            int n = Math.max(people, beds);
    
            int[][] cost = new int[n][n];
    
            int max = 0;
            for (int[] row : score)
                for (int v : row)
                    max = Math.max(max, v);
    
            // Matrix erweitern + Maximierung → Minimierung
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
    
                    int value = 0;
    
                    if (i < people && j < beds)
                        value = score[i][j];
    
                    cost[i][j] = max - value;
                }
            }
    
            int[] u = new int[n + 1];
            int[] v = new int[n + 1];
            int[] p = new int[n + 1];
            int[] way = new int[n + 1];
    
            for (int i = 1; i <= n; i++) {
    
                p[0] = i;
                int j0 = 0;
    
                int[] minv = new int[n + 1];
                boolean[] used = new boolean[n + 1];
                Arrays.fill(minv, Integer.MAX_VALUE);
    
                do {
    
                    used[j0] = true;
                    int i0 = p[j0];
                    int delta = Integer.MAX_VALUE;
                    int j1 = 0;
    
                    for (int j = 1; j <= n; j++) {
    
                        if (!used[j]) {
    
                            int cur = cost[i0 - 1][j - 1] - u[i0] - v[j];
    
                            if (cur < minv[j]) {
                                minv[j] = cur;
                                way[j] = j0;
                            }
    
                            if (minv[j] < delta) {
                                delta = minv[j];
                                j1 = j;
                            }
                        }
                    }
    
                    for (int j = 0; j <= n; j++) {
    
                        if (used[j]) {
                            u[p[j]] += delta;
                            v[j] -= delta;
                        } else {
                            minv[j] -= delta;
                        }
                    }
    
                    j0 = j1;
    
                } while (p[j0] != 0);
    
                do {
    
                    int j1 = way[j0];
                    p[j0] = p[j1];
                    j0 = j1;
    
                } while (j0 != 0);
            }
    
            int[] assignment = new int[people];
    
            for (int j = 1; j <= n; j++) {
                if (p[j] - 1 < people && j - 1 < beds) {
                    assignment[p[j] - 1] = j - 1;
                }
            }
    
            return assignment;
        }
    }

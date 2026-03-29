import java.util.*;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class AppLogic {
    AppState appState;

        public void runAssignment(AppState appState) {
            Cell[][] scoringMatrix = createScoringMatrix(appState.building, appState.persons);
            scoringMatrix = makeMatrixSquared(scoringMatrix);
            scoringMatrix = runAllRules(scoringMatrix, appState.rules);
            int[][] justScoreMatrix = createOnlyScoreMatrix(scoringMatrix);
            int[] result = HungarianSimple.assign(justScoreMatrix);
            assignPoepletoMattresses(result, scoringMatrix);
    
            // Testausdrücke
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
    
        Cell[][] createScoringMatrix(Building building, List<Person> personsList) {
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
            int size = Math.max(zeilen, spalten);
            Cell[][] square = new Cell[size][size];
            for (int i = 0; i < zeilen; i++) {
                for (int j = 0; j < spalten; j++) {
                    square[i][j] = unsquaredMatrix[i][j];
                }
            }
            return square;
        }
    
        Cell[][] runAllRules(Cell[][] scoringMatrix, List<Rule> allRules) {
            for (int i = 0; i < scoringMatrix.length; i++) {
                for (int j = 0; j < scoringMatrix.length; j++) {
                    if (scoringMatrix[i][j] == null) continue;
                    runRulesList(scoringMatrix[i][j], allRules);
                }
            }
            return scoringMatrix;
        }
    
        void runRulesList(Cell cell, List<Rule> allRules) {
            for (Rule rule : allRules) {
                cell.score += rule.execute(cell);
            }
        }
    
        int[][] createOnlyScoreMatrix(Cell[][] scoringMatrix) {
            int[][] justScoreMatrix = new int[scoringMatrix.length][scoringMatrix.length];
            for (int i = 0; i < scoringMatrix.length; i++) {
                for (int j = 0; j < scoringMatrix.length; j++) {
                    if (scoringMatrix[i][j] == null) continue;
                    justScoreMatrix[i][j] = scoringMatrix[i][j].score;
                }
            }
            return justScoreMatrix;
        }
    
        void assignPoepletoMattresses(int[] result, Cell[][] scoringMatrix) {
            for (int i = 0; i < scoringMatrix.length; i++) {
                for (int j = 0; j < scoringMatrix.length; j++) {
                    if (scoringMatrix[i][j] == null) continue;
                    if (result[i] == j) scoringMatrix[i][j].assignPerToMattress();
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

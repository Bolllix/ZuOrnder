import java.util.ArrayList;
import java.util.List;
import java.util.*;

// --- Mattresse ---

class Mattress {
    Person occupant;
    Room room;
    boolean topBunk;
    boolean botBunk;
    boolean singleBed;
    boolean doubleBed;

    // Konstruktor
    public Mattress(boolean topBunk, boolean botBunk, boolean singleBed, boolean doubleBed, Room room) {
        this.topBunk = topBunk;
        this.botBunk = botBunk;
        this.singleBed = singleBed;
        this.doubleBed = doubleBed;
        this.room = room;
    }

    String getMatType() {
        String s = "";
        if (topBunk) s = "Top Bunk";
        if (botBunk) s = "Bot Bunk";
        if (singleBed) s = "Single Bed";
        if (doubleBed) s = "Double bed";
        return s;
    }

    public String toString() {
        return getMatType() + " -Occuptant: " + occupant;
    }
}

// --- Persons ---

class Person {
    String lastname;
    String firstname;
    String gender;
    int age;
    int partnerId;

    // Konstruktor
    public Person(String lastname, String firstname, String gender, int age, int partnerId) {
        assert age > 0 : "Alter muss größer 0 sein";
        this.lastname = lastname;
        this.firstname = firstname;
        this.gender = gender;
        this.age = age;
        this.partnerId = partnerId;
    }

    public String toStringLong() {
        return "Name: " + lastname + "\n" +
                "Vorname: " + firstname + "\n" +
                "Geschlecht: " + gender + "\n" +
                "Alter: " + age + " Jahre alt\n" +
                "PartnerId: " + partnerId;
    }

    public String toString() {
        return firstname + " " + lastname;
    }
}

// --- Rooms ---

class Room {
    String name;
    int floor;
    List<Mattress> mattressesList = new ArrayList<>();
    int capacity;
    boolean girlsRoom;

    public Room(String name, int floor, boolean girlsRoom) {
        this.name = name;
        this.floor = floor;
        this.girlsRoom = girlsRoom;
        this.capacity = 0;
    }

    int getCapacity() {
        return mattressesList.size();
    }

    void addBunkBed() {
        Mattress i = new Mattress(true, false, false, false, this);
        Mattress j = new Mattress(false, true, false, false, this);
        this.mattressesList.add(i);
        this.mattressesList.add(j);
    }

    void addSingleBed() {
        Mattress i = new Mattress(false, false, true, false, this);
        this.mattressesList.add(i);
    }

    void addDoubleBed() {
        Mattress i = new Mattress(false, false, false, true, this);
        Mattress j = new Mattress(false, false, false, true, this);
        this.mattressesList.add(i);
        this.mattressesList.add(j);
    }

    @Override
    public String toString() {
        String s = "Roomname: " + name + "\n" +
                "Mattresses:" + "\n";
        for (Mattress mattress : mattressesList) {
            s += mattress.toString() + "\n";
        }
        return s + "Capacity: " + mattressesList.size();
    }
}

// --- Gebäude ---

class Building {
    List<Mattress> allMatList;
    List<Room> roomsList;

    List<Mattress> createBigMatList() { // Kreiert eine MatratzenListe aus der Liste der Räume
        List<Mattress> tempList = new ArrayList<>();
        for (Room room : roomsList) {
            tempList.addAll(room.mattressesList);
        }
        return tempList;
    }

    Building(List<Room> roomsList) {
        this.roomsList = roomsList;
        this.allMatList = createBigMatList();
    }

    public String toString() {
        String s = "Building: " + "\n";
        for (Room room : roomsList) {
            s += "\n" + "\n" + room.toString();
        }
        return s;
    }
}

class Cell { // Cell wird für die Scoring Matrix gebraucht
    Person person;
    Mattress mattress;
    int score;

    void assignPerToMattress() {
        this.mattress.occupant = this.person;
    }

    public String toString() {
        return person + "\n" + mattress.getMatType() + "\n" + score + "\n";
    }
}

class Execute { // nimmt das Building, alle Leute und alle Regeln

    Execute() {
    }

    List<Rule> allRules;

    public void run(Building building, List<Person> personsList, List<Rule> allRules) {
        Cell[][] scoringMatrix = createScoringMatrix(building, personsList);
        scoringMatrix = makeMatrixSquared(scoringMatrix);
        scoringMatrix = runAllRules(scoringMatrix, allRules);
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
        System.out.println(building);
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

abstract class Rule {
    boolean active = true;
    int rank;

    public int execute(Cell cell) {
        return 1;
    }
}

class OldPeopleNotUp extends Rule {
    @Override
    public int execute(Cell cell) {
        if (cell.person.age > 65 && cell.mattress.topBunk) return 1;
        return 5;
    }
}

class BoysAndGirlsTogether extends Rule {
    @Override
    public int execute(Cell cell) {
        if (cell.mattress.room.girlsRoom && cell.person.gender.equals("weiblich")) return 10;
        return 1;
    }
}

// --- Hungarian Algorithm ----

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

public class Main4 {
    public static void main(String[] args) {
        Person a = new Person("Soelva", "Oliver", "maennlich", 77, 0);
        Person b = new Person("Jung", "Benjamin", "maennlich", 41, 0);
        Person c = new Person("Isa", "Dinc", "maennlich", 74, 0);
        Person d = new Person("EVEGENI", "Evegeniji", "maennlich", 25, 0);
        Person e = new Person("Hessler", "Rene", "maennlich", 45, 0);
        Person f = new Person("Johnson", "Fridolin", "maennlich", 86, 0);
        Person g = new Person("Claus", "Julia", "weiblich", 27, 0);
        Person h = new Person("Wenzel", "Heidi", "weiblich", 28, 0);
        Person i = new Person("Ganz", "Elisa", "weiblich", 28, 0);
        Person j = new Person("Busch", "Sarah", "weiblich", 86, 0);
        List<Person> personsList1 = List.of(a, b, c, d, e, f, g, h, i, j);

        Room a03 = new Room("A03", 0, true);
        Room a04 = new Room("A04", 0, false);
        List<Room> roomsList1 = List.of(a03, a04);
        List<Rule> rulesList1 = new ArrayList<>();

        OldPeopleNotUp num1 = new OldPeopleNotUp();
        BoysAndGirlsTogether num2 = new BoysAndGirlsTogether();

        rulesList1.add(num1);
        rulesList1.add(num2);

        a03.addBunkBed();
        a04.addSingleBed();
        a04.addDoubleBed();
        a04.addSingleBed();
        a03.addBunkBed();
        a04.addSingleBed();
        a04.addSingleBed();

        Building neumuehle = new Building(roomsList1);
        Execute ws2013 = new Execute();
        ws2013.run(neumuehle, personsList1, rulesList1);
    }
}
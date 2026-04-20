import java.util.ArrayList;
import java.util.List;
import model.*;
import rules.*;
import app.*;

public class SampleDataFactory {

    public static AppState createSampleState() {
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

        List<Person> persons = new ArrayList<>(List.of(a, b, c, d, e, f, g, h, i, j));

        Room a03 = new Room("A03", 0, true);
        Room a04 = new Room("A04", 0, false);

        a03.addBunkBed();
        a03.addBunkBed();

        a04.addSingleBed();
        a04.addDoubleBed();
        a04.addSingleBed();
        a04.addSingleBed();
        a04.addSingleBed();

        List<Room> rooms = new ArrayList<>(List.of(a03, a04));

        List<Rule> rules = new ArrayList<>();
        rules.add(new OldPeopleNotUp());
        rules.add(new BoysAndGirlsTogether());

        Building building = new Building(rooms);

        return new AppState(persons, rooms, rules, building);
    }
}
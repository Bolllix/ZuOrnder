package data;

import java.util.ArrayList;
import java.util.List;
import model.*;

public class DataRepository {
    private List<Building> saveBuildings;
    private List<List<Person>> savedPersonsList;

    public DataRepository() {
        saveBuildings = new ArrayList<>();
        savedPersonsList = new ArrayList<>();
    }

    public void addBuilding(Building g) {
        saveBuildings.add(g);
    }

    public List<Building> getGBuildings() {
        return saveBuildings;
    }

    public void addPersonsList(List<Person> liste) {
        savedPersonsList.add(liste);
    }

    public List<List<Person>> getPersonsLists() {
        return savedPersonsList;
    }
}
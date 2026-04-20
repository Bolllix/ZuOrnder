package app;

import java.util.ArrayList;
import java.util.List;
import model.*;
import rules.*;



public class AppState {
    List<Person> persons;
    List<Room> rooms;
    List<Rule> rules;
    static Building building;

    public AppState(List<Person> persons, List<Room> rooms, List<Rule> rules, Building building) {
        this.persons = persons;
        this.rooms = rooms;
        this.rules = rules;
        this.building = building;
    }

    public List<Person> getPersonsList() {
        return persons;
    }

    public List<Room> getRoomsList() {
        return rooms;
    }

    public List<Rule> getRulesList() {
        return rules;
    }

    public Building getBuilding() {
        return building;
    }

    public String toString() {

        return "";
    }
}
package model;

import java.util.ArrayList;
import java.util.List;

public class Room {
    public String name;
    public int floor;
    public List<Mattress> mattressesList = new ArrayList<>();
    public int capacity;
    public boolean girlsRoom;
    
        public Room(String name, int floor, boolean girlsRoom) {
            this.name = name;
            this.floor = floor;
            this.girlsRoom = girlsRoom;
            this.capacity = 0;
        }
    
        public int getCapacity() {
            return mattressesList.size();
        }
    
        public void addBunkBed() {
            Mattress i = new Mattress(true, false, false, false, this);
            Mattress j = new Mattress(false, true, false, false, this);
            this.mattressesList.add(i);
            this.mattressesList.add(j);
        }
    
        public void addSingleBed() {
            Mattress i = new Mattress(false, false, true, false, this);
            this.mattressesList.add(i);
        }
    
        public void addDoubleBed() {
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

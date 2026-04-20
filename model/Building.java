package model;

import java.util.ArrayList;
import java.util.List;

public class Building {
        public List<Mattress> allMatList;
        public List<Room> roomsList;
    
        List<Mattress> createBigMatList() { // Kreiert eine MatratzenListe aus der Liste der Räume
            List<Mattress> tempList = new ArrayList<>();
            for (Room room : roomsList) {
                tempList.addAll(room.mattressesList);
            }
            return tempList;
        }

        public Mattress getMatfromList (int x) {
            return allMatList.get(x);
        }
    
        public Building(List<Room> roomsList) {
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

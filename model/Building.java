package model;

import java.util.ArrayList;
import java.util.List;

public class Building {
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

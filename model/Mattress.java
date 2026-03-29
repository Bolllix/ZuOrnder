package model;

public class Mattress {
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

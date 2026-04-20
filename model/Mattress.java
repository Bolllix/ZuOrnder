package model;

public class Mattress {
    private Person occupant;
    public Room room;
    public boolean topBunk;
    public boolean botBunk;
    public boolean singleBed;
    public boolean doubleBed;
    
        // Konstruktor
        public Mattress(boolean topBunk, boolean botBunk, boolean singleBed, boolean doubleBed, Room room) {
            this.topBunk = topBunk;
            this.botBunk = botBunk;
            this.singleBed = singleBed;
            this.doubleBed = doubleBed;
            this.room = room;
        }
        public Person getOccupant() {
            return occupant;
        }
        public void setOccupant(Person person) {
            this.occupant = person;
        }
    
        public String getMatType() {
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

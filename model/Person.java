package model;

public class Person {
    private final String lastname;
    private final String firstname;
    private final String gender;
    private final int age;
    private final int partnerId;
    
        // Konstruktor
        public Person(String lastname, String firstname, String gender, int age, int partnerId) {
            assert age > 0 : "Alter muss größer 0 sein";
            this.lastname = lastname;
            this.firstname = firstname;
            this.gender = gender;
            this.age = age;
            this.partnerId = partnerId;
        }

        // Getter
        public String getLastname() {
            return lastname;
        }
        public String getFirstname() {
            return firstname;
        }
        public String getGender() {
            return gender;
        }
        public int getAge() {
           return age; 
        }
        public int getPartnerId() {
            return partnerId;
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

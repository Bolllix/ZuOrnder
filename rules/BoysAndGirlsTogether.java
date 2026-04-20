package rules;
import model.*;

public class BoysAndGirlsTogether extends Rule {
        @Override
        public int execute(Cell cell) {
            if (cell.mattress.room.girlsRoom && cell.person.getGender().equals("weiblich")) return 10;
            return 1;
        }
    }

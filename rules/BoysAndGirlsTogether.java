package rules;
import model.*;

class BoysAndGirlsTogether extends Rule {
        @Override
        public int execute(Cell cell) {
            if (cell.mattress.room.girlsRoom && cell.person.gender.equals("weiblich")) return 10;
            return 1;
        }
    }

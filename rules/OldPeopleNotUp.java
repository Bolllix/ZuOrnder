package rules;
import model.*;

class OldPeopleNotUp extends Rule {
    @Override
    public int execute(Cell cell) {
        if (cell.person.age > 65 && cell.mattress.topBunk) return 1;
        return 5;
    }
}
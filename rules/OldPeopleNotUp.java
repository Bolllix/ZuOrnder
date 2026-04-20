package rules;
import model.*;

public class OldPeopleNotUp extends Rule {
    @Override
    public int execute(Cell cell) {
        if (cell.person.getAge() > 65 && cell.mattress.topBunk) return 1;
        return 5;
    }
}
package rules;
import model.*;

public class Rule {
    boolean active = true;
        int rank;
    
        public int execute(Cell cell) {
            return 1;
        }
}

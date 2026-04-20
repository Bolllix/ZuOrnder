import data.DataRepository;
import userInterface.*;
import app.*;

public class Main {
    public static void main(String[] args) {
        AppState appState = SampleDataFactory.createSampleState();
        AppLogic appLogic = new AppLogic();
        DataRepository dataRepository = new DataRepository();
        new MyGUI(appState, appLogic,dataRepository);
    }
}

// javac -d out Main.java

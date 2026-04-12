import data.DataRepository;

public class Main {
    public static void main(String[] args) {
        AppState appState = SampleDataFactory.createSampleState();
        AppLogic appLogic = new AppLogic();
        DataRepository dataRepository = new DataRepository();

        //  appLogic.runAssignment(appState);               // müsste mit dem Button in der GUI gestartet werden
        new MyGUI(appState, appLogic,dataRepository);
    }
}

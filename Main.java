public class Main {
    public static void main(String[] args) {
        AppState appState = SampleDataFactory.createSampleState();
        AppLogic appLogic = new AppLogic();

        appLogic.runAssignment(appState);
        new MyGUI(appState, appLogic);
    }
}

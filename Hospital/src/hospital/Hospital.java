package hospital;

public class Hospital {
    public static void main(String[] args) {
        // Example of entering new patient's information
        Patient patient = new Patient("", 0, null, null); // Empty patient object
        patient.enterInformation(); // Fill in patient's information
        patient.displayInformation(); // Display entered information
    }
}

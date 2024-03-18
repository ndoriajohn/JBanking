package hospital;

import java.util.Scanner;


class Patient {
    private String name;
    private int age;
    private Date admissionDate;
    private Date dischargeDate;

    public Patient(String name, int age, Date admissionDate, Date dischargeDate) {
        this.name = name;
        this.age = age;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
    }

    public void enterInformation() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter patient's name: ");
        name = scanner.nextLine();
        System.out.print("Enter patient's age: ");
        age = scanner.nextInt();
        System.out.println("Enter admission date:");
        System.out.print("Year: ");
        int year = scanner.nextInt();
        System.out.print("Month: ");
        int month = scanner.nextInt();
        System.out.print("Day: ");
        int day = scanner.nextInt();
        admissionDate = new Date(year, month, day);

        // Add prompt for discharge date
        System.out.println("Enter discharge date:");
        System.out.print("Year: ");
        year = scanner.nextInt();
        System.out.print("Month: ");
        month = scanner.nextInt();
        System.out.print("Day: ");
        day = scanner.nextInt();
        dischargeDate = new Date(year, month, day);
    }

    public void displayInformation() {
        System.out.println("Patient's Name: " + name);
        System.out.println("Patient's Age: " + age);
        System.out.print("Admission Date: ");
        admissionDate.displayDate();
        System.out.print("Discharge Date: ");
        dischargeDate.displayDate();
    }
}

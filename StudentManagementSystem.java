package StudentMGTSYS;

import java.io.*;
import java.util.*;

class Student {
    String name;
    int rollNumber;
    double marks;

    public Student(String name, int rollNumber, double marks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
    }

    @Override
    public String toString() {
        return "Roll Number: " + rollNumber + ", Name: " + name + ", Marks: " + marks;
    }
}

public class StudentManagementSystem {
    private static Student[] students = new Student[100];
    private static int studentCount = 0;

    public static void main(String[] args) {
        loadStudentsFromFile();

        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nStudent Management System");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Delete Student");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // consume newline character

            switch (choice) {
                case 1:
                    addStudent(scanner);
                    break;
                case 2:
                    viewStudents();
                    break;
                case 3:
                    deleteStudent(scanner);
                    break;
                case 4:
                    saveStudentsToFile();
                    System.out.println("Exiting the system...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    // Add new student to the system
    private static void addStudent(Scanner scanner) {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        System.out.print("Enter student roll number: ");
        int rollNumber = scanner.nextInt();
        System.out.print("Enter student marks: ");
        double marks = scanner.nextDouble();

        // Create a new student object
        students[studentCount++] = new Student(name, rollNumber, marks);
        System.out.println("Student added successfully!");
    }

    // View all student records
    private static void viewStudents() {
        if (studentCount == 0) {
            System.out.println("No students found.");
            return;
        }

        System.out.println("\nStudent Records:");
        for (int i = 0; i < studentCount; i++) {
            System.out.println(students[i]);
        }
    }

    // Delete a student based on roll number
    private static void deleteStudent(Scanner scanner) {
        System.out.print("Enter roll number of the student to delete: ");
        int rollNumber = scanner.nextInt();

        // Search for the student by roll number
        int index = -1;
        for (int i = 0; i < studentCount; i++) {
            if (students[i].rollNumber == rollNumber) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            // Shift remaining students to remove the deleted student
            for (int i = index; i < studentCount - 1; i++) {
                students[i] = students[i + 1];
            }
            students[--studentCount] = null; // nullify the last element
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student with roll number " + rollNumber + " not found.");
        }
    }

    // Save student records to a file
    private static void saveStudentsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("students.txt"))) {
            for (int i = 0; i < studentCount; i++) {
                writer.write(students[i].name + "," + students[i].rollNumber + "," + students[i].marks);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error while saving data to file: " + e.getMessage());
        }
    }

    // Load student records from the file
    private static void loadStudentsFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("students.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[0];
                int rollNumber = Integer.parseInt(data[1]);
                double marks = Double.parseDouble(data[2]);
                students[studentCount++] = new Student(name, rollNumber, marks);
            }
        } catch (IOException e) {
            System.out.println("Error while loading data from file: " + e.getMessage());
        }
    }
}

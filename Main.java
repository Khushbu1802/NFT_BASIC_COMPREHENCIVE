import java.util.Scanner;

public class StudentScores {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Read number of students
        System.out.print("Enter number of students: ");
        int n = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        
        String[] names = new String[n];
        double[] scores = new double[n];
        
        // Read names and scores
        for (int i = 0; i < n; i++) {
            System.out.print("Enter name for student " + (i + 1) + ": ");
            names[i] = scanner.nextLine();
            System.out.print("Enter score for student " + (i + 1) + ": ");
            scores[i] = scanner.nextDouble();
            scanner.nextLine();
        }
        
        // Calculate average
        double sum = 0;
        for (double score : scores) {
            sum += score;
        }
        double average = sum / n;
        
        // Print students above average
        System.out.println("Average score: " + average);
        System.out.println("Students with scores above average:");
        for (int i = 0; i < n; i++) {
            if (scores[i] > average) {
                System.out.println(names[i] + ": " + scores[i]);
            }
        }
        
        scanner.close();
    }
}
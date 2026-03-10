package DSA;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc      = new Scanner(System.in);
        ProgressManager manager = new ProgressManager();

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║       Gamified Learning Tracking System          ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        System.out.print("Enter Student Name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) name = "Student";

        Student student = new Student(name);
        manager.addStudent(student);

        // Preload default tasks into Queue
        manager.addTask("Complete Quiz");
        manager.addTask("Submit Assignment");
        manager.addTask("Study Module");

        System.out.println("\n  Welcome, " + name + "!  Let's start learning!");
        System.out.println("  Current Badge : " + RewardSystem.getBadge(student.points));
        System.out.println("  Current Rank  : " + RewardSystem.getRank(student.level));

        int choice;

        do {
            printMenu(student);
            System.out.print("Enter choice: ");

            while (!sc.hasNextInt()) {
                System.out.println("   Please enter a number (1–12).");
                sc.next();
                System.out.print("Enter choice: ");
            }
            choice = sc.nextInt();
            System.out.println();

            switch (choice) {

                case 1:
                    manager.completeTask(student);
                    break;

                case 2:
                    manager.completeBonusTask(student);
                    break;

                case 3:
                    printDetailedProgress(student);
                    break;

                case 4:
                    manager.showActivities();
                    break;

                case 5:
                    manager.showRecentTasks();
                    break;

                case 6:
                    manager.showLeaderboard();
                    break;

                case 7:
                    manager.showPendingTasks();
                    break;

                case 8:
                    manager.showBonusTasks();
                    break;

                case 9:
                    manager.addCustomTask(sc);
                    break;

                case 10:
                    System.out.println("  Reset streak for " + student.name + "? (y/n): ");
                    sc.nextLine();
                    String ans = sc.nextLine().trim().toLowerCase();
                    if (ans.equals("y")) {
                        student.streak = 0;
                        System.out.println("   Streak reset to 0.");
                    }
                    break;

                case 11:
                    printFarewell(student);
                    break;

                default:
                    System.out.println("   Invalid choice. Please enter 1–11.");
            }

        } while (choice != 11);

        sc.close();
    }

    private static void printMenu(Student s) {
        System.out.println("\n┌──────────────────────────────────────────────────┐");
        System.out.printf( "│   %-15s │  %4d pts │ Lvl %2d           │%n", s.name, s.points, s.level);
        System.out.println("├──────────────────────────────────────────────────┤");
        System.out.println("│  1.  Complete Next Task                          │");
        System.out.println("│  2.  Complete Bonus Task                         │");
        System.out.println("│  3.  Show Detailed Progress                      │");
        System.out.println("│  4.  Show Activity Log                           │");
        System.out.println("│  5.  Show Recent Tasks                           │");
        System.out.println("│  6.  Show Leaderboard                            │");
        System.out.println("│  7.  Show Pending Tasks                          │");
        System.out.println("│  8.  Show Bonus Tasks                            │");
        System.out.println("│  9.  Add Custom Task                             │");
        System.out.println("│  10. Reset Streak                                │");
        System.out.println("│  11. Exit                                        │");
        System.out.println("└──────────────────────────────────────────────────┘");
    }

    private static void printDetailedProgress(Student s) {
        System.out.println("  --- Progress Report: " + s.name + " ---");
        System.out.println("  Points       : " + s.points);
        System.out.println("  Level        : " + s.level);
        System.out.println("  XP to Next   : " + s.getXpToNextLevel() + " XP");
        System.out.println("  Progress Bar : " + s.getProgressBar());
        System.out.println("  Streak       : " + s.streak + " tasks");
        System.out.println("  Tasks Done   : " + s.tasksCompleted);
        System.out.println("  Badge        : " + RewardSystem.getBadge(s.points));
        System.out.println("  Rank         : " + RewardSystem.getRank(s.level));
        System.out.println("  Badges Earned: " + s.getBadgeList());
        System.out.println("  💬 " + RewardSystem.getMotivation(s.points));
    }

    private static void printFarewell(Student s) {
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║                  See You Next Time!              ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.printf( "║  Student  : %-37s║%n", s.name);
        System.out.printf( "║  Points   : %-37s║%n", s.points + " pts");
        System.out.printf( "║  Level    : %-37s║%n", s.level);
        System.out.printf( "║  Badge    : %-37s║%n", RewardSystem.getBadge(s.points));
        System.out.printf( "║  Rank     : %-37s║%n", RewardSystem.getRank(s.level));
        System.out.printf( "║  Streak   : %-37s║%n", s.streak + " tasks");
        System.out.printf( "║  Badges   : %-37s║%n", s.getBadgeList());
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println("  Keep learning and come back stronger!");
    }
}
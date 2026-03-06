package Java_Version;

import java.util.*;

public class ProgressManager {

    ArrayList<Student> students  = new ArrayList<>();
    Stack<String>      activityStack = new Stack<>();
    Queue<String>      taskQueue    = new LinkedList<>();

    // ── Default task pool (refills when queue empties) ────────────────
    private static final String[] DEFAULT_TASKS = {
        "Complete Quiz",
        "Submit Assignment",
        "Study Module",
        "Watch Lecture",
        "Read Chapter",
        "Solve Practice Problems",
        "Attend Live Session",
        "Complete Lab Exercise",
        "Review Flashcards",
        "Take Mock Test"
    };

    public void addStudent(Student s) {
        students.add(s);
    }

    public void addTask(String task) {
        taskQueue.add(task);
    }

    // Refill queue with default tasks if empty
    private void refillTaskQueue() {
        System.out.println("  ♻️  Task queue refilled with new tasks!");
        for (String t : DEFAULT_TASKS) taskQueue.add(t);
    }

    // ── Complete next task in queue ───────────────────────────────────
    public void completeTask(Student s) {
        if (taskQueue.isEmpty()) refillTaskQueue();

        String task   = taskQueue.poll();
        int baseXP    = 10;
        int bonus     = RewardSystem.getStreakBonus(s.streak);
        int totalXP   = baseXP + bonus;

        s.addPoints(totalXP);
        String entry = String.format("[Task #%d] %-30s | +%d XP%s | %s",
                s.tasksCompleted, task, totalXP,
                bonus > 0 ? " (+" + bonus + " streak bonus!)" : "",
                new java.util.Date());
        activityStack.push(entry);

        System.out.println("  ✅ Task Completed : " + task);
        System.out.println("  ⭐ XP Earned      : +" + totalXP
                + (bonus > 0 ? " (includes +" + bonus + " streak bonus!)" : ""));
        System.out.println("  📊 Progress Bar   : " + s.getProgressBar());
        System.out.println("  💬 " + RewardSystem.getMotivation(s.points));
    }

    // ── Add a custom task interactively ──────────────────────────────
    public void addCustomTask(Scanner sc) {
        System.out.print("Enter custom task name: ");
        sc.nextLine(); // consume leftover newline
        String task = sc.nextLine().trim();
        if (task.isEmpty()) {
            System.out.println("  ❌ Task name cannot be empty.");
            return;
        }
        taskQueue.add(task);
        System.out.println("  ✅ Custom task added: " + task);
        System.out.println("  📋 Tasks in queue: " + taskQueue.size());
    }

    // ── Show activity log (most recent first) ────────────────────────
    public void showActivities() {
        if (activityStack.isEmpty()) {
            System.out.println("  📭 No recent activities. Complete a task first!");
            return;
        }
        System.out.println("\n  ─── Recent Activity Log ───");
        // Print stack top-to-bottom (most recent first)
        List<String> list = new ArrayList<>(activityStack);
        Collections.reverse(list);
        int count = 1;
        for (String act : list) {
            System.out.println("  " + count++ + ". " + act);
        }
    }

    // ── Show leaderboard sorted by points ────────────────────────────
    public void showLeaderboard() {
        if (students.isEmpty()) {
            System.out.println("  No students registered.");
            return;
        }
        students.sort((a, b) -> b.points - a.points);
        System.out.println("\n  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║               🏆  LEADERBOARD                  ║");
        System.out.println("  ╠══════════════════════════════════════════════════╣");
        String[] medals = {"🥇", "🥈", "🥉"};
        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            String medal = i < medals.length ? medals[i] : "   ";
            System.out.printf("  ║ %s  #%-2d  %-15s | %4d pts | Lvl %-2d | %s%n",
                    medal, (i + 1), s.name, s.points, s.level, RewardSystem.getRank(s.level));
        }
        System.out.println("  ╚══════════════════════════════════════════════════╝");
    }

    // ── Show pending tasks in queue ───────────────────────────────────
    public void showPendingTasks() {
        if (taskQueue.isEmpty()) {
            System.out.println("  📭 No pending tasks in queue.");
            return;
        }
        System.out.println("\n  ─── Pending Tasks (" + taskQueue.size() + ") ───");
        int i = 1;
        for (String t : taskQueue) {
            System.out.println("  " + i++ + ". " + t);
        }
    }
}
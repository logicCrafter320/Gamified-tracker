package DSA;

import java.util.*;

public class ProgressManager {

    // ── Stack – Activity log (LIFO) ──────────────────────────────────────
    Stack<String> activityStack = new Stack<>();

    // ── Queue – Task scheduling (FIFO) ───────────────────────────────────
    Queue<String> taskQueue = new LinkedList<>();

    // ── Priority Queue – Bonus tasks ordered by XP (max-heap) ────────────
    PriorityQueue<int[]> bonusTaskQueue = new PriorityQueue<>((a, b) -> b[1] - a[1]);

    private static final String[] BONUS_TASK_NAMES = {
        "Hackathon Challenge", "Peer Review", "Research Paper",
        "Group Project", "Teach a Concept"
    };

    // ── Circular Queue – Tracks last 5 completed task names ──────────────
    static class CircularQueue {
        private final String[] data;
        private int front, rear, size;
        private final int capacity;

        CircularQueue(int capacity) {
            this.capacity = capacity;
            this.data     = new String[capacity];
            this.front    = 0;
            this.rear     = 0;
            this.size     = 0;
        }

        void enqueue(String item) {
            if (size == capacity) {
                // Overwrite oldest entry (circular wrap)
                front = (front + 1) % capacity;
                size--;
            }
            data[rear] = item;
            rear = (rear + 1) % capacity;
            size++;
        }

        void display() {
            if (size == 0) {
                System.out.println("  Circular queue is empty.");
                return;
            }
            System.out.println("\n  --- Last " + size + " Completed Tasks (Circular Queue) ---");
            for (int i = 0; i < size; i++) {
                System.out.println("  " + (i + 1) + ". " + data[(front + i) % capacity]);
            }
        }
    }

    CircularQueue recentTasks = new CircularQueue(5);

    // ── HashMap – Task name to XP value mapping – O(1) average lookup ────
    HashMap<String, Integer> taskXpMap = new HashMap<>();

    // ── Student list ──────────────────────────────────────────────────────
    ArrayList<Student> students = new ArrayList<>();

    private static final String[] DEFAULT_TASKS = {
        "Complete Quiz", "Submit Assignment", "Study Module",
        "Watch Lecture", "Read Chapter", "Solve Practice Problems",
        "Attend Live Session", "Complete Lab Exercise",
        "Review Flashcards", "Take Mock Test"
    };

    public ProgressManager() {
        // Populate HashMap with default task XP values
        taskXpMap.put("Complete Quiz",           15);
        taskXpMap.put("Submit Assignment",        20);
        taskXpMap.put("Study Module",             10);
        taskXpMap.put("Watch Lecture",            10);
        taskXpMap.put("Read Chapter",             10);
        taskXpMap.put("Solve Practice Problems",  20);
        taskXpMap.put("Attend Live Session",      15);
        taskXpMap.put("Complete Lab Exercise",    25);
        taskXpMap.put("Review Flashcards",        10);
        taskXpMap.put("Take Mock Test",           30);

        // Load bonus tasks into Priority Queue
        int[] bonusXP = {50, 30, 40, 35, 25};
        for (int i = 0; i < BONUS_TASK_NAMES.length; i++) {
            bonusTaskQueue.offer(new int[]{i, bonusXP[i]});
        }
    }

    public void addStudent(Student s) {
        students.add(s);
    }

    public void addTask(String task) {
        taskQueue.offer(task);
    }

    private void refillTaskQueue() {
        System.out.println("  ♻️  Task queue refilled with new tasks!");
        for (String t : DEFAULT_TASKS) taskQueue.offer(t);
    }

    // Complete next task from Queue
    public void completeTask(Student s) {
        if (taskQueue.isEmpty()) refillTaskQueue();

        String task   = taskQueue.poll();
        int baseXP    = taskXpMap.getOrDefault(task, 10);
        int bonus     = RewardSystem.getStreakBonus(s.streak);
        int totalXP   = baseXP + bonus;

        s.addPoints(totalXP);

        String entry = String.format("[Task #%d] %-30s | +%d XP%s | %s",
                s.tasksCompleted, task, totalXP,
                bonus > 0 ? " (+" + bonus + " streak bonus!)" : "",
                new java.util.Date());

        activityStack.push(entry);
        recentTasks.enqueue(task);

        System.out.println("   Task Completed : " + task);
        System.out.println("   XP Earned      : +" + totalXP
                + (bonus > 0 ? " (includes +" + bonus + " streak bonus!)" : ""));
        System.out.println("   Progress Bar   : " + s.getProgressBar());
        System.out.println("   " + RewardSystem.getMotivation(s.points));
    }

    // Complete highest-XP bonus task from Priority Queue
    public void completeBonusTask(Student s) {
        if (bonusTaskQueue.isEmpty()) {
            System.out.println("  No bonus tasks available!");
            return;
        }
        int[] top     = bonusTaskQueue.poll();
        String task   = BONUS_TASK_NAMES[top[0]];
        int xp        = top[1];

        s.addPoints(xp);

        String entry = String.format("[BONUS #%d] %-28s | +%d XP | %s",
                s.tasksCompleted, task, xp, new java.util.Date());
        activityStack.push(entry);
        recentTasks.enqueue(task);

        System.out.println("   🌟 Bonus Task   : " + task);
        System.out.println("   XP Earned      : +" + xp);
        System.out.println("   Progress Bar   : " + s.getProgressBar());
        System.out.println("   " + RewardSystem.getMotivation(s.points));
    }

    // Add custom task to Queue and HashMap
    public void addCustomTask(Scanner sc) {
        System.out.print("Enter custom task name: ");
        sc.nextLine();
        String task = sc.nextLine().trim();
        if (task.isEmpty()) {
            System.out.println("   Task name cannot be empty.");
            return;
        }
        System.out.print("Enter XP value for this task (default 10): ");
        int xp = 10;
        if (sc.hasNextInt()) xp = Math.max(1, sc.nextInt());

        taskQueue.offer(task);
        taskXpMap.put(task, xp);
        System.out.println("   Custom task added: \"" + task + "\" with " + xp + " XP");
        System.out.println("   Tasks in queue: " + taskQueue.size());
    }

    // Show activity log from Stack (most recent first)
    public void showActivities() {
        if (activityStack.isEmpty()) {
            System.out.println("  No recent activities. Complete a task first!");
            return;
        }
        System.out.println("\n  --- Recent Activity Log ---");
        List<String> list = new ArrayList<>(activityStack);
        Collections.reverse(list);
        int count = 1;
        for (String act : list) System.out.println("  " + count++ + ". " + act);
    }

    // Show last 5 completed tasks from Circular Queue
    public void showRecentTasks() {
        recentTasks.display();
    }

    // Show leaderboard sorted by points using Merge Sort
    public void showLeaderboard() {
        if (students.isEmpty()) {
            System.out.println("  No students registered.");
            return;
        }

        Student[] arr = students.toArray(new Student[0]);
        mergeSort(arr, 0, arr.length - 1);

        System.out.println("\n  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║                   LEADERBOARD                    ║");
        System.out.println("  ╠══════════════════════════════════════════════════╣");
        String[] medals = {"🥇", "🥈", "🥉"};
        for (int i = 0; i < arr.length; i++) {
            Student s = arr[i];
            String medal = i < medals.length ? medals[i] : "  ";
            System.out.printf("  ║ %s #%-2d %-15s | %4d pts | Lvl %-2d | %s%n",
                    medal, (i + 1), s.name, s.points, s.level, RewardSystem.getRank(s.level));
        }
        System.out.println("  ╚══════════════════════════════════════════════════╝");
    }

    // Bubble Sort – O(n²) worst/average, O(n) best
    private void bubbleSort(Student[] arr) {
        int n = arr.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            for (int j = 0; j < n - 1 - i; j++) {
                if (arr[j].points < arr[j + 1].points) {
                    Student tmp = arr[j];
                    arr[j]     = arr[j + 1];
                    arr[j + 1] = tmp;
                    swapped    = true;
                }
            }
            if (!swapped) break;
        }
    }

    // Insertion Sort – O(n²) worst, O(n) best
    private void insertionSort(Student[] arr) {
        for (int i = 1; i < arr.length; i++) {
            Student key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].points < key.points) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

    // Merge Sort – O(n log n)
    private void mergeSort(Student[] arr, int l, int r) {
        if (l < r) {
            int mid = l + (r - l) / 2;
            mergeSort(arr, l, mid);
            mergeSort(arr, mid + 1, r);
            merge(arr, l, mid, r);
        }
    }

    private void merge(Student[] arr, int l, int mid, int r) {
        int n1 = mid - l + 1, n2 = r - mid;
        Student[] L = new Student[n1], R = new Student[n2];
        System.arraycopy(arr, l, L, 0, n1);
        System.arraycopy(arr, mid + 1, R, 0, n2);
        int i = 0, j = 0, k = l;
        while (i < n1 && j < n2)
            arr[k++] = (L[i].points >= R[j].points) ? L[i++] : R[j++];
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // Show pending tasks in Queue
    public void showPendingTasks() {
        if (taskQueue.isEmpty()) {
            System.out.println("  No pending tasks in queue.");
            return;
        }
        System.out.println("\n  --- Pending Tasks (" + taskQueue.size() + ") ---");
        int i = 1;
        for (String t : taskQueue)
            System.out.println("  " + i++ + ". " + t + "  [" + taskXpMap.getOrDefault(t, 10) + " XP]");
    }

    // Show bonus tasks from Priority Queue (without removing)
    public void showBonusTasks() {
        if (bonusTaskQueue.isEmpty()) {
            System.out.println("  No bonus tasks available.");
            return;
        }
        System.out.println("\n  --- Bonus Tasks (highest XP first) ---");
        List<int[]> temp = new ArrayList<>(bonusTaskQueue);
        temp.sort((a, b) -> b[1] - a[1]);
        int i = 1;
        for (int[] t : temp)
            System.out.println("  " + i++ + ". " + BONUS_TASK_NAMES[t[0]] + "  →  " + t[1] + " XP");
    }

}
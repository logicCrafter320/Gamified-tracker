package DSA;

public class Student {

    // ── Singly Linked List Node for Badges ──────────────────────────────
    static class BadgeNode {
        String badge;
        BadgeNode next;
        BadgeNode(String badge) {
            this.badge = badge;
            this.next  = null;
        }
    }

    // ── Singly Linked List for storing unlocked badges ──────────────────
    static class BadgeLinkedList {
        BadgeNode head;
        int size;

        BadgeLinkedList() {
            head = null;
            size = 0;
        }

        // Insert at tail – O(n)
        void insert(String badge) {
            BadgeNode newNode = new BadgeNode(badge);
            if (head == null) {
                head = newNode;
            } else {
                BadgeNode curr = head;
                while (curr.next != null) curr = curr.next;
                curr.next = newNode;
            }
            size++;
        }

        // Search – O(n)
        boolean contains(String badge) {
            BadgeNode curr = head;
            while (curr != null) {
                if (curr.badge.equals(badge)) return true;
                curr = curr.next;
            }
            return false;
        }

        // Delete by value – O(n)
        void delete(String badge) {
            if (head == null) return;
            if (head.badge.equals(badge)) {
                head = head.next;
                size--;
                return;
            }
            BadgeNode curr = head;
            while (curr.next != null) {
                if (curr.next.badge.equals(badge)) {
                    curr.next = curr.next.next;
                    size--;
                    return;
                }
                curr = curr.next;
            }
        }

        // Traverse and return all badges as a string – O(n)
        String toDisplayString() {
            if (head == null) return "None yet";
            StringBuilder sb = new StringBuilder();
            BadgeNode curr = head;
            while (curr != null) {
                sb.append(curr.badge);
                if (curr.next != null) sb.append(", ");
                curr = curr.next;
            }
            return sb.toString();
        }
    }

    // ── Student Fields ───────────────────────────────────────────────────
    String          name;
    int             points;
    int             level;
    int             streak;
    int             tasksCompleted;
    BadgeLinkedList unlockedBadges;

    public Student(String name) {
        this.name           = name;
        this.points         = 0;
        this.level          = 1;
        this.streak         = 0;
        this.tasksCompleted = 0;
        this.unlockedBadges = new BadgeLinkedList();
    }

    public void addPoints(int p) {
        this.points += p;
        this.tasksCompleted++;
        this.streak++;
        updateLevel();
        checkBadges();
    }

    private void updateLevel() {
        int newLevel = (points / 100) + 1;
        if (newLevel > this.level) {
            this.level = newLevel;
            System.out.println("  ⬆️  LEVEL UP! You are now Level " + this.level + "!");
        } else {
            this.level = newLevel;
        }
    }

    private void checkBadges() {
        if (points >= 50  && !unlockedBadges.contains("Beginner"))   { unlockedBadges.insert("Beginner");    System.out.println("  🏅 Badge Unlocked: Beginner!"); }
        if (points >= 150 && !unlockedBadges.contains("Achiever"))   { unlockedBadges.insert("Achiever");    System.out.println("  🥈 Badge Unlocked: Achiever!"); }
        if (points >= 300 && !unlockedBadges.contains("Master"))     { unlockedBadges.insert("Master");      System.out.println("  🥇 Badge Unlocked: Master!"); }
        if (points >= 500 && !unlockedBadges.contains("Legend"))     { unlockedBadges.insert("Legend");      System.out.println("  👑 Badge Unlocked: Legend!"); }
        if (streak >= 3   && !unlockedBadges.contains("On a Roll"))  { unlockedBadges.insert("On a Roll");   System.out.println("  🔥 Badge Unlocked: On a Roll (3-task streak)!"); }
        if (streak >= 5   && !unlockedBadges.contains("Unstoppable")){ unlockedBadges.insert("Unstoppable"); System.out.println("  ⚡ Badge Unlocked: Unstoppable (5-task streak)!"); }
    }

    public int getXpToNextLevel() {
        return (level * 100) - points;
    }

    public String getProgressBar() {
        int xpInLevel = points % 100;
        int filled    = xpInLevel / 10;
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < 10; i++) bar.append(i < filled ? "█" : "░");
        bar.append("] ").append(xpInLevel).append("/100 XP");
        return bar.toString();
    }

    public String getBadgeList() {
        return unlockedBadges.toDisplayString();
    }

    @Override
    public String toString() {
        return String.format("%-15s | Pts: %4d | Lvl: %2d | Streak: %d | Badge: %s",
                name, points, level, streak, RewardSystem.getBadge(points));
    }
}
package Java_Version;

import java.util.ArrayList;
import java.util.List;

public class Student {

    String name;
    int points;
    int level;
    int streak;
    int tasksCompleted;
    List<String> unlockedBadges;

    public Student(String name) {
        this.name = name;
        this.points = 0;
        this.level = 1;
        this.streak = 0;
        this.tasksCompleted = 0;
        this.unlockedBadges = new ArrayList<>();
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
            System.out.println("  🎉 LEVEL UP! You are now Level " + this.level + "!");
        } else {
            this.level = newLevel;
        }
    }

    private void checkBadges() {
        if (points >= 50  && !unlockedBadges.contains("Beginner"))  { unlockedBadges.add("Beginner");  System.out.println("  🏅 Badge Unlocked: Beginner!"); }
        if (points >= 150 && !unlockedBadges.contains("Achiever"))  { unlockedBadges.add("Achiever");  System.out.println("  🥈 Badge Unlocked: Achiever!"); }
        if (points >= 300 && !unlockedBadges.contains("Master"))    { unlockedBadges.add("Master");    System.out.println("  🥇 Badge Unlocked: Master!"); }
        if (streak >= 3   && !unlockedBadges.contains("On a Roll")) { unlockedBadges.add("On a Roll"); System.out.println("  🔥 Badge Unlocked: On a Roll (3-task streak)!"); }
    }

    public int getXpToNextLevel() {
        return (level * 100) - points;
    }

    public String getProgressBar() {
        int xpInLevel  = points % 100;
        int filled     = xpInLevel / 10;
        StringBuilder bar = new StringBuilder("[");
        for (int i = 0; i < 10; i++) bar.append(i < filled ? "█" : "░");
        bar.append("] ").append(xpInLevel).append("/100 XP");
        return bar.toString();
    }

    @Override
    public String toString() {
        return String.format("%-15s | Pts: %4d | Lvl: %2d | Streak: %d | Badge: %s",
                name, points, level, streak, RewardSystem.getBadge(points));
    }
}
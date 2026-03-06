package Java_Version;

public class RewardSystem {

    // ── Badge by points ───────────────────────────────────────────────
    public static String getBadge(int points) {
        if (points >= 500) return "🏆 Legend";
        if (points >= 300) return "🥇 Master";
        if (points >= 150) return "🥈 Achiever";
        if (points >= 50)  return "🏅 Beginner";
        return "⬜ None";
    }

    // ── XP bonus for fast completion ──────────────────────────────────
    public static int getStreakBonus(int streak) {
        if (streak >= 5) return 10;   // +10 bonus at 5-task streak
        if (streak >= 3) return 5;    // +5 bonus at 3-task streak
        return 0;
    }

    // ── Rank label by level ───────────────────────────────────────────
    public static String getRank(int level) {
        if (level >= 10) return "🌟 Grandmaster";
        if (level >= 7)  return "💎 Diamond";
        if (level >= 5)  return "🔮 Platinum";
        if (level >= 3)  return "🥉 Gold";
        if (level >= 2)  return "🪙 Silver";
        return "🔰 Bronze";
    }

    // ── Motivational message based on points ─────────────────────────
    public static String getMotivation(int points) {
        if (points == 0)   return "Complete your first task to earn points!";
        if (points < 50)   return "Keep going, you're building momentum!";
        if (points < 150)  return "Great work! You've earned Beginner status!";
        if (points < 300)  return "Impressive! Achiever rank within reach!";
        if (points < 500)  return "Outstanding! You're a Master learner!";
        return "🌟 You are a Legend! Incredible dedication!";
    }
}
package DSA;

import java.util.HashMap;

public class RewardSystem {

    // ── Sorted thresholds for Binary Search ─────────────────────────────
    private static final int[]    BADGE_THRESHOLDS = {0, 50, 150, 300, 500};
    private static final String[] BADGE_NAMES      = {" None", " Beginner", " Achiever", " Master", " Legend"};

    private static final int[]    RANK_THRESHOLDS  = {1, 2, 3, 5, 7, 10};
    private static final String[] RANK_NAMES       = {" Bronze", " Silver", " Gold", " Platinum", " Diamond", " Grandmaster"};

    // ── HashMap for O(1) motivational message lookup ─────────────────────
    private static final HashMap<Integer, String> MOTIVATION_MAP = new HashMap<>();
    static {
        MOTIVATION_MAP.put(0, "Complete your first task to earn points!");
        MOTIVATION_MAP.put(1, "Keep going, you're building momentum!");
        MOTIVATION_MAP.put(2, "Great work! You've earned Beginner status!");
        MOTIVATION_MAP.put(3, "Impressive! Achiever rank within reach!");
        MOTIVATION_MAP.put(4, "Outstanding! You're a Master learner!");
        MOTIVATION_MAP.put(5, "👑 You are a Legend! Incredible dedication!");
    }

    // Binary Search to find badge tier by points – O(log n)
    public static String getBadge(int points) {
        int lo = 0, hi = BADGE_THRESHOLDS.length - 1, result = 0;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (BADGE_THRESHOLDS[mid] <= points) {
                result = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return BADGE_NAMES[result];
    }

    // Binary Search to find rank tier by level – O(log n)
    public static String getRank(int level) {
        int lo = 0, hi = RANK_THRESHOLDS.length - 1, result = 0;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (RANK_THRESHOLDS[mid] <= level) {
                result = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return RANK_NAMES[result];
    }

    // HashMap lookup for motivation message – O(1) average
    public static String getMotivation(int points) {
        int key;
        if      (points == 0)  key = 0;
        else if (points < 50)  key = 1;
        else if (points < 150) key = 2;
        else if (points < 300) key = 3;
        else if (points < 500) key = 4;
        else                   key = 5;
        return MOTIVATION_MAP.getOrDefault(key, "Keep learning!");
    }

    // Streak bonus – O(1)
    public static int getStreakBonus(int streak) {
        if (streak >= 5) return 10;
        if (streak >= 3) return 5;
        return 0;
    }
}
package org.example;

public abstract class Ticket {
    private final int id;
    private final String requester;
    private final int priority; // 1 (low) .. 5 (high)
    private final int daysOpen;

    public Ticket(int id, String requester, int priority, int daysOpen) {
        if (priority < 1 || priority > 5) throw new IllegalArgumentException("priority must be 1..5");
        if (daysOpen < 0) throw new IllegalArgumentException("daysOpen must be >= 0");
        this.id = id;
        this.requester = requester;
        this.priority = priority;
        this.daysOpen = daysOpen;
    }

    public int getId() { return id; }
    public String getRequester() { return requester; }
    public int getPriority() { return priority; }
    public int getDaysOpen() { return daysOpen; }

    /** Higher means more urgent */
    public abstract int urgencyScore();

    // -------------------------------
    // TODO #1 (Method Overloading)
    // Implement ALL three overloads.
    // - Base estimate depends on priority and daysOpen.
    // - Add complexityFactor if provided (>=1).
    // - Add afterHoursPenalty if provided (>=0).
    // Suggested formula (you can follow exactly):
    //   base = (6 - priority) * 2 + daysOpen
    //   return max(1, base * complexityFactor + afterHoursPenalty)
    // -------------------------------

    public int estimateResolutionHours() {
        // TODO #1a
        return estimateResolutionHours(1, 0);
    }

    public int estimateResolutionHours(int complexityFactor) {
        // TODO #1b
        return estimateResolutionHours(complexityFactor, 0);
    }

    public int estimateResolutionHours(int complexityFactor, int afterHoursPenalty) {
        // TODO #1c
        if (complexityFactor < 1) complexityFactor = 1;
        if (afterHoursPenalty < 0) afterHoursPenalty = 0;

        int base = (6 - priority) * 2 + daysOpen;
        int result = (base * complexityFactor) + afterHoursPenalty;

        return Math.max(1, result);
    }

    @Override
    public String toString() {
        return String.format("#%d (%s) pr=%d open=%dd score=%d est=%dh",
                id, getRequester(), priority, daysOpen, urgencyScore(), estimateResolutionHours());
    }
}
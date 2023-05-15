package com.wddmg.competition.week344;

/**
 * @author duym
 * @version $ Id: Test2, v 0.1 2023/05/07 10:26 duym Exp $
 */
public class Test2 {
    public static void main(String[] args) {
        FrequencyTracker tracker = new FrequencyTracker();
        tracker.add(1);
        tracker.deleteOne(1);
        boolean res = tracker.hasFrequency(1);
        System.out.println(tracker);
    }
}

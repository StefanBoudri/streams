package examples;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WhenToUseStreamsExample {

    record User(String name, boolean active) {}

    public static void main(String[] args) {

        List<User> users = List.of(
                new User("Tom", true),
                new User("Sarah", false),
                new User("Julia", true),
                new User("Michael", true),
                new User("Anna", false)
        );

        // ----------------------------------------------------------
        // 1. TRADITIONAL LOOP VERSION
        // ----------------------------------------------------------
        List<String> activeNamesLoop = new ArrayList<>();
        for (User u : users) {
            if (u.active()) {
                activeNamesLoop.add(u.name().toUpperCase());
            }
        }
        activeNamesLoop.sort(String::compareTo);

        System.out.println("Loop result: " + activeNamesLoop);


        // ----------------------------------------------------------
        // 2. SEQUENTIAL STREAM VERSION
        // ----------------------------------------------------------
        List<String> activeNamesStream = users.stream()
                .filter(User::active)
                .map(u -> u.name().toUpperCase())
                .sorted()
                .toList();

        System.out.println("Sequential stream result: " + activeNamesStream);


        // ----------------------------------------------------------
        // 3. PARALLEL STREAM VERSION (MAKES SENSE HERE)
        // ----------------------------------------------------------

        List<String> heavyProcessedNamesParallel = users.parallelStream()
                .filter(User::active)
                .map(u -> simulateHeavyComputation(u.name()))
                .sorted(Comparator.naturalOrder())
                .toList();

        System.out.println("Parallel stream result: " + heavyProcessedNamesParallel);
    }

    private static String simulateHeavyComputation(String name) {
        long x = 0;
        for (int i = 0; i < 2_000_000; i++) {
            x += i % 7;
        }
        return name.toUpperCase() + "_X" + x;
    }
}

package examples;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ReadabilityComparisonExample {

    record User(String name, boolean active, int age, String city) {}

    public static void main(String[] args) {

        List<User> users = List.of(
                new User("Tom", true, 21, "London"),
                new User("Sarah", false, 33, "Paris"),
                new User("Julia", true, 19, "Berlin"),
                new User("Mark", true, 44, "London"),
                new User("Anna", false, 28, "Rome")
        );

        // ---------------------------------------------------------------------
        // 1. COMPLEX TRADITIONAL LOOP VERSION
        // ---------------------------------------------------------------------

        // Step 1: filter active AND older than 20
        List<User> activeAndAdult = new ArrayList<>();
        for (User u : users) {
            if (u.active() && u.age() > 20) {
                activeAndAdult.add(u);
            }
        }

        // Step 2: filter by city = "London"
        List<User> londonUsers = new ArrayList<>();
        for (User u : activeAndAdult) {
            if (u.city().equals("London")) {
                londonUsers.add(u);
            }
        }

        // Step 3: extract name and transform to uppercase
        List<String> names = new ArrayList<>();
        for (User u : londonUsers) {
            names.add(u.name().toUpperCase());
        }

        // Step 4: sort alphabetically
        names.sort(String::compareTo);

        List<String> resultLoop = new ArrayList<>(names);

        System.out.println("Loop result: " + resultLoop);


        // ---------------------------------------------------------------------
        // 2. STREAM VERSION
        // ---------------------------------------------------------------------
        List<String> resultStream = users.stream()
                .filter(User::active)
                .filter(u -> u.age() > 20)
                .filter(u -> u.city().equals("London"))
                .map(u -> u.name().toUpperCase())
                .sorted()
                .toList();

        System.out.println("Stream result: " + resultStream);
    }
}

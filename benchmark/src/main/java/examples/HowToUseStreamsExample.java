package examples;

import java.util.ArrayList;
import java.util.List;

public class HowToUseStreamsExample {

    static class Person {
        String name;
        int age;
        Person(String name, int age) {
            this.name = name; this.age = age;
        }
    }

    public static void main(String[] args) {
        List<Person> people = List.of(
                new Person("Mark", 17),
                new Person("Anna", 22),
                new Person("Bob", 30)
        );

        List<String> adultsLoop = new ArrayList<>();
        for (Person p : people) {
            if (p.age >= 18) {
                adultsLoop.add(p.name.toUpperCase());
            }
        }

        System.out.println(String.join(", ", adultsLoop));

        List<String> adultsStream = people.stream()
                .filter(p -> p.age >= 18)
                .map(p -> p.name.toUpperCase())
                .toList();

        System.out.println(String.join(", ", adultsStream));
    }
}

package examples;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class WhenToAvoidStreamsExample {

    public static void main(String[] args) {

        System.out.println("Indexed loop example:");
        List<Integer> numbers = List.of(10, 20, 30, 40, 50);

        for (int i = 0; i < numbers.size() - 1; i++) {
            int current = numbers.get(i);
            int next = numbers.get(i + 1);
            System.out.println(current + " -> " + next);
        }

        System.out.println("\nUnsafe parallel stream example 1:");
        List<Integer> numbersToUnsafeList = new ArrayList<>();

        numbers.parallelStream()
                .forEach(numbersToUnsafeList::add);

        System.out.println("Numbers are now likely unordered:" + numbersToUnsafeList);

        System.out.println("\nUnsafe parallel stream example 2:");
        List<Integer> unsafeList = new ArrayList<>();

        IntStream.range(0, 100_000)
                .parallel()
                .forEach(unsafeList::add);

        System.out.println("Expected size: 100000");
        System.out.println("Actual size:   " + unsafeList.size());

        // Check for duplicates or missing values
        long uniqueElementsCount = unsafeList.stream().distinct().count();

        System.out.println("Unique elements: " + uniqueElementsCount);
        System.out.println("Amount of duplicates:" + (unsafeList.size() - uniqueElementsCount));
    }
}

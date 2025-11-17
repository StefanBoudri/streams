package examples;

import java.util.List;

public class HowStreamsWorkExample {

    public static void main(String[] args) {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        int result = numbers.stream()                // Source
                .filter(n -> n % 2 == 1)             // Intermediate: filter
                .map(n -> n * 10)                    // Intermediate: map
                .reduce(0, Integer::sum);            // Terminal: reduce

        System.out.println(result); // Output: 90
    }
}


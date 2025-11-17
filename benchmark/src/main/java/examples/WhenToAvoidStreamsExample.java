package examples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WhenToAvoidStreamsExample {

    public static void main(String[] args) {

        System.out.println("Indexed loop example (good for sequential logic):");
        List<Integer> numbers = List.of(10, 20, 30, 40, 50);

        for (int i = 0; i < numbers.size() - 1; i++) {
            int current = numbers.get(i);
            int next = numbers.get(i + 1);
            System.out.println(current + " -> " + next);
        }

        List<Integer> unsafeList = new ArrayList<>();

        numbers.parallelStream()
                .forEach(unsafeList::add);

        System.out.println("Result of unsafeList" + unsafeList);
    }
}

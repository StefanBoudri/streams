package org.benchmark;

import org.openjdk.jmh.annotations.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.AverageTime, Mode.SampleTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class ListBenchmark {

    private List<Integer> numberList;

    @Setup(Level.Iteration)
    public void setup() {
        numberList = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            numberList.add(i);
        }
    }

    @Benchmark
    public long listLoopSum() {
        long sum = 0;
        for (Integer num : numberList) {
            sum += num;
        }
        return sum;
    }

    @Benchmark
    public long listStreamSum() {
        return numberList.stream().mapToLong(Integer::longValue).sum();
    }

    @Benchmark
    public long listParallelStreamSum() {
        return numberList.parallelStream().mapToLong(Integer::longValue).sum();
    }
}

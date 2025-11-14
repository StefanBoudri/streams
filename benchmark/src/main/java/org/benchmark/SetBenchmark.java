package org.benchmark;

import org.openjdk.jmh.annotations.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.AverageTime, Mode.SampleTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class SetBenchmark {

    private Set<Integer> numberSet;

    @Setup(Level.Iteration)
    public void setup() {
        numberSet = new HashSet<>();
        for (int i = 0; i < 1_000_000; i++) {
            numberSet.add(i);
        }
    }

    @Benchmark
    public long setLoopSum() {
        long sum = 0;
        for (Integer num : numberSet) {
            sum += num;
        }
        return sum;
    }

    @Benchmark
    public long setStreamSum() {
        return numberSet.stream().mapToLong(Integer::longValue).sum();
    }

    @Benchmark
    public long setParallelStreamSum() {
        return numberSet.parallelStream().mapToLong(Integer::longValue).sum();
    }
}

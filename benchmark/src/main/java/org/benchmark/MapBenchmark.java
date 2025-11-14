package org.benchmark;

import org.openjdk.jmh.annotations.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@BenchmarkMode({Mode.AverageTime, Mode.SampleTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class MapBenchmark {

    private Map<Integer, Integer> numberMap;

    @Setup(Level.Iteration)
    public void setup() {
        numberMap = new HashMap<>();
        for (int i = 0; i < 1_000_000; i++) {
            numberMap.put(i, i);
        }
    }

    @Benchmark
    public long mapLoopSum() {
        long sum = 0;
        for (Integer val : numberMap.values()) {
            sum += val;
        }
        return sum;
    }

    @Benchmark
    public long mapStreamSum() {
        return numberMap.values().stream().mapToLong(Integer::longValue).sum();
    }

    @Benchmark
    public long mapParallelStreamSum() {
        return numberMap.values().parallelStream().mapToLong(Integer::longValue).sum();
    }
}

package org.benchmark;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkRunner {
    public static void main(String[] args) throws Exception {
        // Benchmark for ListBenchmark
        Options listOptions = new OptionsBuilder()
                .include(".*ListBenchmark.*")
                .forks(1)
                .result("list-benchmark-results.csv")
                .resultFormat(ResultFormatType.CSV)
                .build();
        new Runner(listOptions).run();

        // Benchmark for MapBenchmark
        Options mapOptions = new OptionsBuilder()
                .include(".*MapBenchmark.*")
                .forks(1)
                .result("map-benchmark-results.csv")
                .resultFormat(ResultFormatType.CSV)
                .build();
        new Runner(mapOptions).run();

        // Benchmark for SetBenchmark
        Options setOptions = new OptionsBuilder()
                .include(".*SetBenchmark.*")
                .forks(1)
                .result("set-benchmark-results.csv")
                .resultFormat(ResultFormatType.CSV)
                .build();
        new Runner(setOptions).run();
    }
}

# Benchmarking streams in Java with JMH

## What is JMH?

**JMH (Java Microbenchmark Harness)** is a framework created by the developers of the Java Virtual Machine (JVM) to **accurately measure the performance of small pieces of Java code**, like methods or loops.

Normal timing methods, like using `System.currentTimeMillis()` or `System.nanoTime()`, often give **misleading results** because the JVM does things like:

* **Just-In-Time (JIT) compilation** — code can run faster after being compiled at runtime.
* **Garbage collection (GC)** — memory cleanup pauses your code unpredictably.
* **CPU optimizations** — the JVM may inline methods, remove unused code, or reorder instructions.

JMH solves this by:

1. **Running the code many times** to avoid random fluctuations.
2. **Separating warmup from measurement** so JIT optimizations don’t distort results.
3. **Providing multiple benchmarking modes**, like:

   * **Average Time (AVGT):** measures average time per operation over many iterations.
   * **Sample Time (SAMPLE):** measures each operation individually for high precision.
4. **Handling multi-threaded benchmarks** safely.

In short, JMH is the **industry-standard tool for precise and reliable Java microbenchmarks**, especially for measuring things like **loops, streams, or small utility methods**.

---

## What is measured in the benchmark?

There are measurements made on three data structures: a **list**, a **map**, and a **set**. For each data structure, we measure the **time it takes to perform certain operations**, specifically **summing all elements**.

The benchmark compares three approaches:

1. **Classic loop** – iterating manually over the data structure with a `for` loop.
2. **Sequential stream** – using Java Streams in normal, single-threaded mode.
3. **Parallel stream** – using Java Streams in parallel, letting multiple CPU cores process the data concurrently.

For each operation, JMH measures:

* **Average time per operation (AVGT mode)** – how long each operation takes on average.
* **Sampled times (SAMPLE mode)** – measuring individual operation times to see variability and percentiles, like the fastest 50% or slowest 1%.

This helps us understand both **typical performance** and **variation** in time, as well as how parallel processing affects performance on different data structures.

---

## What does the data mean?

There are three outputted CSV files from the benchmark; for each data structure, there is a separate file:

* [List benchmark](./benchmark/list-benchmark-results.csv)
* [Map benchmark](./benchmark/map-benchmark-results.csv)
* [Set benchmark](./benchmark/set-benchmark-results.csv)

Each CSV file contains several columns that help understand the performance of the operations:

* **Benchmark** – the name of the method being tested (e.g., `listLoopSum`, `listStreamSum`).

* **Mode** – the type of measurement:

  * `avgt` (average time): measures the total time of an iteration and divides by the number of operations.
  * `sample`: measures each operation individually, giving more detailed timing distribution.

* **Threads** – the number of threads used for the benchmark. For parallel streams, this may still show `1` because JMH runs a single benchmark thread, but the stream internally uses multiple CPU cores.

* **Samples** – the number of measurements taken. In `avgt` mode this is usually the number of iterations, while in `sample` mode this can be very large because each operation is measured individually.

* **Score** – the time per operation in milliseconds (ms/op). In `avgt` mode, this is the average of all operations in an iteration. In `sample` mode, it is the average of all individually measured operations.

* **Score Error (99.9%)** – an estimate of how much the measured score could vary due to randomness, CPU scheduling, or other small variations. A smaller error means more confidence in the measured time.

* **Unit** – the unit of measurement, usually milliseconds per operation (`ms/op`).

Some CSV rows also show **percentiles**, labeled like `p0.50` or `p0.99`. These indicate how long it took for a certain percentage of operations:

* `p0.50` – 50% of operations were faster than this time (median).
* `p0.90` – 90% of operations were faster than this time.
* `p1.00` – the slowest operation observed.

---

## Memory Usage Metrics

JMH also includes **memory usage information** in the CSV files. These fields are related to **Garbage Collection (GC)**:

* **gc.alloc.rate (MB/sec)** – Rate of memory allocated by the code during the benchmark, measured in **megabytes per second**.
* **gc.alloc.rate.norm (B/op)** – Normalized memory allocation **per operation**, in bytes. Shows how "heavy" each operation is in terms of memory use.
* **gc.count** – The number of **garbage collection events** that occurred during the benchmark.
* **gc.time (ms)** – Total **time spent in garbage collection**, measured in milliseconds.

These fields help track not only how fast your code runs but also how much memory it uses, which can affect real-world performance and JVM behavior.

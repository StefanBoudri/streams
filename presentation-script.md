# **Slide 2 - introduction**

* Streams were introduced in Java 8.

* They offer a modern, declarative way to process collections.

* Instead of telling Java how to loop step-by-step, streams let you describe what you want to happen.
* This creates more readable and maintainable code, especially for complex data transformations.

# **Slide 6 — How do streams work?**

* Streams in Java process data using a pipeline made of three parts: a source, intermediate operations, and a terminal operation.
* The source is usually a collection, like a list, set, or map.
* Intermediate operations—like `filter`, `map`, and `sorted`—do not run immediately. Streams use *lazy evaluation*, meaning work is only done when the terminal operation is called.
* The terminal operation—for example `sum()`, `count()`, or `collect()`—triggers the whole pipeline.
* Streams can also run in parallel, where the data is split across multiple CPU cores for faster processing.
* This model is different from loops, where everything executes step-by-step and immediately.

# **Slide 7 — How to use streams?**

* Using a stream typically involves three steps.
* Step 1: Start with a collection and call `.stream()` or `.parallelStream()`.
* Step 2: Add intermediate operations to define what you want to do—these can filter, transform, or sort the elements.
* Step 3: Add a terminal operation, such as `sum()`, `toList()`, or `count()`, which produces the final result.
* What makes streams nice is that we describe *what* should happen, instead of writing loops that tell Java *how* to do everything.

# **Slide 8 — When to use streams?**

* Streams are great when the transformation of the data is more important than the mechanics of looping.
* They work especially well when we chain multiple steps—like filtering, mapping, sorting, and collecting.
* Streams improve readability in cases where loops would require temporary variables or multiple passes.
* Parallel streams also make it much easier to use multiple CPU cores without writing thread code.
* In short: use streams when readability improves and when operations naturally fit into a pipeline.

# **Slide 9 — When to avoid streams?**

* Streams are not always the best choice.
* If you need index-based logic—for example `list.get(i + 1)`—then a loop is more clear and efficient.
* Parallel streams should be avoided when thread-safety is not guaranteed. They can cause race conditions when modifying shared data structures.
* For very small datasets, streams may add unnecessary overhead compared to a simple loop.
* If the lambdas make the code harder to read rather than easier, a loop is the better option.

# **Slide 10 — Streams vs. loops: code readability**

* Loops are imperative: they focus on *how* the work is done. This can make them verbose as the logic grows.
* Streams are declarative: we describe *what* should happen and Java takes care of the details.
* This declarative style removes boilerplate code—no index tracking, no temporary variables, and no manual iteration.
* For complex data transformations, streams are usually much clearer than loops.
* But for simple tasks, loops remain more intuitive.

# **Slide 11 — Benchmark methodology**

*(Improved, more explicit, explains JVM/JIT and why JMH is trustworthy)*

* **Benchmarks were run using JMH (Java Microbenchmark Harness).**
  JMH is the official benchmarking tool created by the OpenJDK team.

* **JMH produces trustworthy results because it understands the JVM.**
  The JVM (Java Virtual Machine) is the system that runs Java programs and includes many optimizations that affect timing.

* **JMH handles the JIT compiler correctly.**
  The JIT (Just-In-Time compiler) optimizes code while the program runs.
  JMH warms up the code first so it measures *optimized* performance, not slow startup performance.

* **JMH avoids misleading results caused by compiler optimizations.**
  It prevents:

  * **Dead-code elimination** → JVM removes code that produces no visible result.
  * **Constant folding** → JVM replaces expressions with precomputed constants.
  * **Loop unrolling** → JVM restructures loops in ways that change timing.

* **JMH isolates each benchmark method.**
  Ensures that the JVM cannot merge or optimize across benchmarks.

* **Measurements include:**

  * Average execution time (ms/op)
  * Memory allocation (B/op via `gc.alloc.rate.norm`)

* **Three data structures tested:**
  `ArrayList`, `HashSet`, `HashMap`.

* **Results exported to CSV for analysis.**

# **Slide 12 — How do streams perform (compared to loops)?**

* Loops and sequential streams have almost the same performance because they both process elements one by one. However, loops avoid the extra overhead of creating a stream pipeline, which is why loops are slightly faster.
* Sequential streams allocate more memory because each intermediate operation creates small functional objects internally, such as lambdas and pipeline nodes.
* Parallel streams split the data across multiple threads. This dramatically speeds up the work on large collections because several CPU cores process the list at the same time.
* However, parallel streams also use more memory because they create:
  – thread-local buffers for partial results
  – task objects for the fork/join framework
  – splitting structures to divide the work
* HashSet and HashMap have slower sequential performance because they have no guaranteed ordering, causing additional internal overhead for mapping or summing operations.

# **Slide 13 — Conclusion**

* Performance: Parallel streams are fastest, loops slightly outperform sequential streams.
* Memory: Loops use the least memory, parallel streams use the most.
* Readability: Streams are clearer for complex multi-step operations; loops are simpler for basic tasks.

Which approach you choose depends on your needs. For projects (especially big ones) where performance isn't very important, streams would probably be the way to go because of its code clarity. 
Of course for good performance you can use parallel streams, but it must be safe for parallelism.
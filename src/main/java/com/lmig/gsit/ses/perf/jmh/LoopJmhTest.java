package com.lmig.gsit.ses.perf.jmh;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


@State(Scope.Benchmark)
@Warmup(iterations = 5, timeUnit = TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, timeUnit = TimeUnit.NANOSECONDS)
@Fork(value = 1)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class LoopJmhTest {
    private static final int RUNS = 1000;
    private static final int SIZE = 1_000_000_0;

    private static final List<Integer> VALUES = new ArrayList<>(SIZE);

    static {
        Collections.fill(VALUES, 1);
    }

    @Benchmark
    @OperationsPerInvocation(RUNS)
    public long traditionalLoop() {
        long sum = 0;
        for (int i = 0; i < VALUES.size(); i++) {
            sum += VALUES.get(i);
        }
        return sum;
    }

    @Benchmark
    @OperationsPerInvocation(RUNS)
    public long traditionalLoopOptimized() {
        long sum = 0;
        int size = VALUES.size();
        for (int i = 0; i < size; i++) {
            sum += VALUES.get(i);
        }
        return sum;
    }

    @Benchmark
    @OperationsPerInvocation(RUNS)
    public long foreachLoop() {
        long sum = 0;
        for (int value : VALUES) {
            sum += value;
        }
        return sum;
    }



    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(".*" + LoopJmhTest.class.getSimpleName() + ".*")
                .build();

        new Runner(opt).run();
    }
}

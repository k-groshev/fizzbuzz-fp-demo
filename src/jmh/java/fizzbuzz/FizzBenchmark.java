package fizzbuzz;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 50, time = 200, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 5, time = 100, timeUnit = TimeUnit.MILLISECONDS)
@Fork(1)
@State(Scope.Benchmark)
public class FizzBenchmark {

    @Benchmark
    public void functional(Blackhole bh) {
        IntStream.rangeClosed(1, 100)
                .mapToObj(FizzBuzz::fizzBuzz)
                .forEach(bh::consume);
    }

    @Benchmark
    public void functionalInfiniteStream(Blackhole bh) {
        FizzBuzz.fizzBuzz(IntStream.iterate(1, i -> i + 1))
                .limit(100)
                .forEach(bh::consume);
    }

    @Benchmark
    public void imperativeSolution(Blackhole bh) {
        for (int i = 1; i <= 100; i++) {
            if (i % 15 == 0) {
                bh.consume("FizzBuzz");
            } else if (i % 3 == 0) {
                bh.consume("Fizz");
            } else if (i % 5 == 0) {
                bh.consume("Buzz");
            } else {
                bh.consume(Integer.toString(i));
            }
        }
    }
}

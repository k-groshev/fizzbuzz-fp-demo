package fizzbuzz;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.System.out;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public interface FizzBuzz {

    static Predicate<Integer> divisibleBy(Integer div) {
        return (i) -> i % div == 0;
    }

    class Replacement {
        final Predicate<Integer> when; final String output;

        public Replacement(Predicate<Integer> when, String output) {
            this.when = when; this.output = output;
        }
    }

    static String applyReplacementRules(Integer i, List<Replacement> rules) {
        Stream<String> applicableReplacements = rules.stream()
                .filter(replacement -> replacement.when.test(i))
                .map(replacement -> replacement.output);

        return applicableReplacements
                .reduce(String::concat)
                .orElse(i.toString());
    }

    List<Replacement> fizzAndOrBuzz = unmodifiableList(asList(
                    new Replacement(divisibleBy(3), "Fizz"),
                    new Replacement(divisibleBy(5), "Buzz")));

    static String fizzBuzz(Integer i) {
        return applyReplacementRules(i, fizzAndOrBuzz);
    }

    static Stream<String> fizzBuzz(IntStream intStream) {
        return intStream.mapToObj(FizzBuzz::fizzBuzz);
    }

    static void main(String... args) {
        IntStream.rangeClosed(1, 100)
                .mapToObj(FizzBuzz::fizzBuzz)
                .forEach(out::println);
    }
}

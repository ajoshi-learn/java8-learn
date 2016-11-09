package app.streams.dishexamples;

import app.streams.dishexamples.domain.Dish;
import app.streams.dishexamples.domain.DishRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by ajoshi on 02-Nov-16.
 */
public class Main {
    public static void main(String[] args) {
        List<String> threeHighCaloricDishes = DishRepository.dishesList()
                .stream()
                .map(Dish::getName)
                .collect(toList());

        List<Integer> numbers1 = Arrays.asList(1, 2, 3);
        List<Integer> numbers2 = Arrays.asList(3, 4);

        long start = System.currentTimeMillis();
        System.out.println(parallelSum(100000000));
        long end = System.currentTimeMillis();
        System.out.println(end - start);


    }

    public static long sum(long n) {
        return LongStream.rangeClosed(1, n)
                .reduce(0L, Long::sum);
    }

    public static long parallelSum(int n) {
        return LongStream.rangeClosed(1, n)
                .parallel()
                .reduce(0L, Long::sum);
    }
}

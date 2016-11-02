package app.streams.dishexamples;

import app.streams.dishexamples.domain.Dish;
import app.streams.dishexamples.domain.DishRepository;

import java.util.Arrays;
import java.util.List;

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


    }
}

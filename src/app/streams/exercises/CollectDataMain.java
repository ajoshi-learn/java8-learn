package app.streams.exercises;

import app.streams.dishexamples.domain.Dish;
import app.streams.dishexamples.domain.DishRepository;
import app.streams.exercises.domain.Trader;
import app.streams.exercises.domain.TraderTransactionRepository;
import app.streams.exercises.domain.Transaction;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

/**
 * Created by ajoshi on 03-Nov-16.
 */
public class CollectDataMain {
    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(1, 2, 3, 4);
        TraderTransactionRepository.transactions()
                .stream()
                .map(Transaction::getValue)
                .reduce(Integer::sum);
        TraderTransactionRepository.traders()
                .stream()
                .map(Trader::toString)
                .collect(joining());
        TraderTransactionRepository.transactions()
                .stream()
                .collect(reducing(0, Transaction::getValue, Integer::sum));
        TraderTransactionRepository.transactions()
                .stream()
                .collect(summarizingInt(Transaction::getValue));
        TraderTransactionRepository.transactions()
                .stream()
                .collect(maxBy(Comparator.comparing(Transaction::getValue)));
        Map<Trader, List<Transaction>> tradersTransactions = TraderTransactionRepository.transactions()
                .stream()
                .collect(groupingBy(Transaction::getTrader));

        TraderTransactionRepository.traders()
                .stream()
                .collect(groupingBy(Trader::getCity,
                        collectingAndThen(maxBy(comparingInt(trader -> trader.getCity().length())), Optional::get)));

        TraderTransactionRepository.traders()
                .stream()
                .collect(partitioningBy(trader -> trader.getCity().equals("NY")));
        DishRepository.dishesList()
                .stream()
                .collect(partitioningBy(Dish::isVegetarian, groupingBy(Dish::getType)));

        DishRepository.dishesList()
                .stream()
                .collect(partitioningBy(Dish::isVegetarian, collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
    }
}

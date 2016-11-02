package app.streams.exercises;

import app.streams.exercises.domain.Trader;
import app.streams.exercises.domain.TraderTransactionRepository;
import app.streams.exercises.domain.Transaction;

import java.util.Arrays;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Comparator.comparing;

/**
 * Created by ajoshi on 02-Nov-16.
 */
public class Main {
    public static void main(String[] args) {
        new Main().test();
    }

    public void test() {
        TraderTransactionRepository.transactions()
                .stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .forEach(System.out::println);
        TraderTransactionRepository.traders()
                .stream()
                .map(Trader::getCity)
                .distinct()
                .forEach(System.out::println);
        TraderTransactionRepository.traders()
                .stream()
                .anyMatch(trader -> trader.getCity().equals("Milan"));
        TraderTransactionRepository.transactions()
                .stream()
                .filter(transaction -> transaction.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getValue)
                .forEach(System.out::println);
        TraderTransactionRepository.transactions()
                .stream()
                .map(Transaction::getValue)
                .reduce(Integer::max);

        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);
    }
}

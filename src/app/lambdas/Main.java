package app.lambdas;

import app.lambdas.domain.Apple;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Comparator.comparing;

/**
 * Created by ajoshi on 31-Oct-16.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReaderHelper.processFile((BufferedReader br) -> br.readLine() + br.readLine());
    }

    public void test() {
        List<Apple> apples = Arrays.asList(new Apple(1), new Apple(2), new Apple(3), new Apple(4));
        apples.sort(comparing(Apple::getWeight).reversed());
        apples.sort((a,b) -> a.getWeight().compareTo(b.getWeight()));
        apples.sort(comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));

        Predicate<Apple> readApple = (Apple a) -> a.getColor().equals("red");
        Predicate<Apple> notRedApple = readApple.negate();
        Predicate<Apple> redHeavvyApple = readApple.and((a) -> a.getWeight() > 150);

        Function<Integer, Integer> f = x -> x + 1;
        Function<Integer, Integer> g = x -> x * 2;
        Function<Integer, Integer> h = f.andThen(g);
        int result = h.apply(2);

    }

}

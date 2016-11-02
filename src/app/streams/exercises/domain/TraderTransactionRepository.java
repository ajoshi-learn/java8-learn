package app.streams.exercises.domain;

import java.util.Arrays;
import java.util.List;

/**
 * Created by ajoshi on 02-Nov-16.
 */
public class TraderTransactionRepository {
    static Trader raoul = new Trader("Raoul", "Cambridge");
    static Trader mario = new Trader("Mario","Milan");
    static Trader alan = new Trader("Alan","Cambridge");
    static Trader brian = new Trader("Brian","Cambridge");

    static List<Transaction> transactions = Arrays.asList(
            new Transaction(brian, 2011, 300),
            new Transaction(raoul, 2012, 1000),
            new Transaction(raoul, 2011, 400),
            new Transaction(mario, 2012, 710),
            new Transaction(mario, 2012, 700),
            new Transaction(alan, 2012, 950)
    );

    public static List<Trader> traders() {
        return Arrays.asList(raoul, mario, alan, brian);
    }

    public static List<Transaction> transactions() {
        return transactions;
    }
}

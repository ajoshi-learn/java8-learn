package app.lambdas;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by ajoshi on 31-Oct-16.
 */
@FunctionalInterface
public interface BufferedReaderProcessor {
    String process(BufferedReader reader) throws IOException;
}

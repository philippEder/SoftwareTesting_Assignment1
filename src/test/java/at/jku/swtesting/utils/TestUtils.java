package at.jku.swtesting.utils;

import java.util.function.Consumer;

public class TestUtils {

    public final static String FISRT_ITEM = "first";
    public final static String SECOND_ITEM = "second";
    public final static String THIRD_ITEM = "third";

    public static void repeat(int n, Consumer<Integer> consumer)  {
        for (int i = 1; i <= n; i++) {
            consumer.accept(i);
        }
    }
}

package com.mukit09;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class BenchmarkRunner {
    private static final int TOTAL_STRING = 100_000;
    private static List<String> list = new ArrayList<>();

    static {
        var random = new Random();
        for (var i = 0; i < TOTAL_STRING; i++) {
            list.add(getRandomString(random));
        }
    }

    @Fork(value = 10, warmups = 5)
    @Benchmark
    @OutputTimeUnit(TimeUnit.SECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void doBenchMarkOfPlusOperator() {
        int len = concatWithPlusOperator(list);
    }

    @Fork(value = 10, warmups = 5)
    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void doBenchMarkOfStringBuilder() {
        int len = concatWithStringBuilder(list);
    }

    private static String getRandomString(Random random) {
        var leftLimit = 48; // numeral '0'
        var rightLimit = 122; // letter 'z'
        var targetStringLength = 10;

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private int concatWithPlusOperator(List<String> list) {
        var string = "";
        for (var i = 0;i<list.size(); i++)
            string += list.get(i);
        return string.length();
    }

    private int concatWithStringBuilder(List<String> list) {
        var stringBuilder = new StringBuilder();
        for (var i = 0;i<list.size(); i++)
            stringBuilder.append(list.get(i));
        return stringBuilder.length();
    }
}
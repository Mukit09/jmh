package com.mukit09;

import org.openjdk.jmh.annotations.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

public class BenchmarkRunner {
    private static final int TOTAL_STRING = 100_000;
    private static final List<String> randomStringList = new ArrayList<>();

    static {
        var random = new Random();
        for (var i = 0; i < TOTAL_STRING; i++) {
            randomStringList.add(getRandomString(random));
        }
    }

    @Fork(value = 10, warmups = 5)
    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void doBenchMarkOfPlusOperator() {
        int len = concatWithPlusOperator();
    }

    @Fork(value = 10, warmups = 5)
    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void doBenchMarkOfStringBuilder() {
        int len = concatWithStringBuilder();
    }

    @Fork(value = 10, warmups = 5)
    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void doBenchMarkOfStringJoiner() {
        int len = concatWithStringJoiner();
    }

    @Fork(value = 10, warmups = 5)
    @Benchmark
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.AverageTime)
    public void doBenchMarkOfStringJoin() {
        int len = concatWithStringJoin();
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

    private int concatWithPlusOperator() {
        var string = "";
        for (var i = 0;i<randomStringList.size(); i++)
            string += randomStringList.get(i) + ",";
        return string.length();
    }

    private int concatWithStringBuilder() {
        var stringBuilder = new StringBuilder();
        for (var i = 0;i<randomStringList.size(); i++)
            stringBuilder.append(randomStringList.get(i)).append(",");
        return stringBuilder.toString().length();
    }

    private int concatWithStringJoiner() {
        var stringJoiner = new StringJoiner(",");
        for (var string : randomStringList)
            stringJoiner.add(string);
        return stringJoiner.toString().length();
    }

    private int concatWithStringJoin() {
        var string = String.join(",", randomStringList);
        return string.length();
    }
}
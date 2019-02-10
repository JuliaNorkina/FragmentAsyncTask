package com.example.factasynctask.Utils;

public final class MathUtils {
    private MathUtils() {
    }

    public static int calculateFactorial(int number) {
        int fact = 1;
        for (int count = number; count > 1; count--) {
            fact = fact * count;
        }
        return fact;
    }
}
package com.example.runner;

import java.math.BigInteger;

public class ViewCountRunner {
    private static final BigInteger m = new BigInteger("1000000");
    private static final BigInteger k = new BigInteger("1000");
    private final BigInteger viewCount;

    public ViewCountRunner(BigInteger viewCount) {
        this.viewCount = viewCount;
    }

    public String viewCountToKM() {
        if (viewCount.compareTo(BigInteger.ZERO) < 0) {
            return "0";
        }
        if (viewCount.compareTo(m) >= 0) {
            return viewCount.divide(m) + "M";
        }
        if (viewCount.compareTo(k) >= 0) {
            return viewCount.divide(k) + "K";
        }
        return viewCount.toString();
    }

    @Override
    public String toString() {
        return viewCountToKM();
    }

}

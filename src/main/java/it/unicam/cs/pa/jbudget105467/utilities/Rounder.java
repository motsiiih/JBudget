package it.unicam.cs.pa.jbudget105467.utilities;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Utility class used to round a double value
 */
public class Rounder {

    /**
     * Given a double value, returns the same value
     * considering only the desired number of ciphers.
     *
     * @param value the double to round
     * @param places the number of ciphers
     * @return the rounded double value
     */
    public static double round(double value, int places) {
            if (places < 0)
                throw new IllegalArgumentException();
            BigDecimal bd = BigDecimal.valueOf(value);
            bd = bd.setScale(places, RoundingMode.HALF_UP);
            return bd.doubleValue();
    }
}
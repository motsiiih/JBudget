package it.unicam.cs.pa.jbudget105467;

import java.time.LocalDate;

/**
 * Provides data about user's economic trend
 */
public interface IStats {

    /**
     * Provides the total amount spent using the input account.
     *
     * @param account the account to check
     * @param start the start date
     * @param end the end date
     * @return the total amount spent
     */
    double getIncomes(IAccount account, LocalDate start, LocalDate end);

    /**
     * Provides the total amount earned using the input account.
     *
     * @param account the account to check
     * @param start the start date
     * @param end the end date
     * @return the total amount earned
     */
    double getOutcomes(IAccount account, LocalDate start, LocalDate end);
}

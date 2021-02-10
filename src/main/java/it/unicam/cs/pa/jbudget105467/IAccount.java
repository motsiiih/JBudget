package it.unicam.cs.pa.jbudget105467;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;
import java.util.function.Predicate;

/**
 * Manages an account allowing to get and set its data (type, name,
 * description, balance, movements and the associated ledger).
 */
@JsonDeserialize(as = Account.class)
public interface IAccount {

    /**
     * @return account's type
     */
    AccountType getType();

    /**
     * @return account's name
     */
    String getName();

    /**
     * Sets this account's name to the given argument.
     *
     * @param name the new account's name
     */
    void setName(String name);

    /**
     * @return account's description
     */
    String getDescription();

    /**
     * Sets this account's description to the given argument.
     *
     * @param description the new account's description
     */
    void setDescription(String description);

    /**
     * @return account's id
     */
    int getId();

    /**
     * @return account's balance at the opening
     */
    double getOpeningBalance();

    /**
     * @return account's current balance
     */
    double getBalance();

    /**
     * @return account's associated movements
     */
    List<IMovement> getMovements();

    /**
     * Returns the list of movements associated to this account
     * filtered by a certain condition defined by an instance
     * of {@code Predicate}.
     *
     * @param p predicate
     * @return filtered account's associated movements
     */
    List<IMovement> getMovements(Predicate<IMovement> p);
}

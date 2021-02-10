package it.unicam.cs.pa.jbudget105467;

/**
 * An enumeration created to distinguish
 * different types of account.
 */
public enum AccountType {

    /**
     * Type of account that treats {@link MovementType#CREDIT} movements
     * as incomes and {@link MovementType#DEBIT} as outcomes.
     */
    ASSETS,

    /**
     * Type of account that treats {@link MovementType#DEBIT} movements
     * as incomes and {@link MovementType#CREDIT} as outcomes.
     */
    LIABILITIES;

    /**
     * A {@code String} instance that defines the name.
     */
    private String name;

    /**
     * Constructor that initializes the name.
     */
    AccountType() {
        this.name = name();
    }

    /**
     * Getter method of the {@link AccountType#name} param.
     *
     * @return {@link AccountType#name}
     */
    public String getName() {
        return name;
    }
}
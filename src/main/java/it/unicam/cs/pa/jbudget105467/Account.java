package it.unicam.cs.pa.jbudget105467;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import it.unicam.cs.pa.jbudget105467.utilities.Rounder;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * Basic implementation of the {@code IAccount} interface. Implements
 * methods to get and set this {@code Account} instance's data.
 */
@JsonIdentityInfo(
        generator= ObjectIdGenerators.PropertyGenerator.class,
        property="id",
        scope = Account.class)
public class Account implements IAccount {

    /**
     * An instance of {@code AtomicInteger} that starts from 0 and
     * keeps track of the next id number to assign.
     */
    private static final AtomicInteger count = new AtomicInteger(0);

    /**
     * The id number of this instance.
     */
    private int id = count.incrementAndGet();

    /**
     * A {@code String} instance that defines the name.
     */
    private String name;

    /**
     * A {@code String} instance that defines the description.
     */
    private String description;

    /**
     * A {@code AccountType} instance that defines the type.
     */
    private AccountType type;

    /**
     * A double that defines the balance at this account's opening.
     */
    private double openingBalance;

    /**
     * A double that defines the current balance.
     */
    private double balance;

    /**
     * A list that contains this account's movements
     */
    private List<IMovement> movements = new ArrayList<>();

    /**
     * Creates a new instance of {@code IAccount} by using the input parameters. At the opening
     * the values of {@link #balance} and {@link #openingBalance} are the same.
     *
     * @param name account name
     * @param description account description
     * @param openingBalance account balance to start with
     * @param type account type
     */
    public Account(String name, String description, double openingBalance, AccountType type) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.openingBalance = openingBalance;
        this.balance = openingBalance;
    }

    public Account() {
    }

    /**
     * Returns this account's type.
     *
     * @return {@link #type}
     */
    @Override
    public AccountType getType() {
        return type;
    }

    /**
     * Returns this account's name.
     *
     * @return {@link #name}
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * Sets {@link #name} to the given argument.
     *
     * @param name the new account's name
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns this account's description.
     *
     * @return {@link #description}
     */
    @Override
    public String getDescription() {
        return this.description;
    }

    /**
     * Sets {@link #description} to the given argument.
     *
     * @param description the new account's name
     */
    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns this account's id.
     *
     * @return {@link #id}
     */
    @Override
    public int getId() {
        return this.id;
    }

    /**
     * Returns this account's balance at the opening.
     *
     * @return {@link #openingBalance}
     */
    @Override
    public double getOpeningBalance() {
        return this.openingBalance;
    }

    /**
     * Calculates the balance of this account.
     *
     * @return {@link Account#balance}
     */
    @Override
    public double getBalance() {
        balance = openingBalance;
        for (IMovement m : this.getMovements(movement -> !movement.getTransaction().isScheduled())){
            if (this.type.equals(AccountType.ASSETS)){
                if (m.getType().equals(MovementType.CREDIT))
                    balance += m.getAmount();
                else if (m.getType().equals(MovementType.DEBIT))
                    balance -= m.getAmount();
            } else if (this.type.equals(AccountType.LIABILITIES)){
                if (m.getType().equals(MovementType.DEBIT))
                    balance += m.getAmount();
                else if (m.getType().equals(MovementType.CREDIT))
                    balance -= m.getAmount();
            }
        }
        return balance = Rounder.round(balance, 2);
    }

    /**
     * For each present/past transaction of the associated ledger, gets the
     * movement list associated to this account and returns the whole list.
     *
     * @return this account's movements
     */
    @Override
    public List<IMovement> getMovements() {
        return movements;
    }

    /**
     * Each movement returned by {@link #getMovements()}, that applied to {@link Predicate#test(Object)}
     * method returns true, gets added to a list that is returned at the end of the method.
     *
     * @param p the filter predicate
     * @return this account's movements filtered by a certain condition
     */
    @Override
    public List<IMovement> getMovements(Predicate<IMovement> p) {
        List<IMovement> tmpList = new ArrayList<>();
        for (IMovement m : this.getMovements()){
            if (p.test(m))
                tmpList.add(m);
        }
        return tmpList;
    }
}
package it.unicam.cs.pa.jbudget105467;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LedgerTest {
    ILedger l = Ledger.getInstance();
    IAccount a1 = new Account("myAccount", "This is my account", 0.0, AccountType.LIABILITIES);
    ITransaction t1 = new Transaction(LocalDate.now());
    ITransaction t2 = new Transaction(LocalDate.now());
    IMovement m1 = new Movement(MovementType.CREDIT, 120, a1, t1);
    ITag tag = new Tag("Food", "I use this tag when I buy food", 10.0 );
    Predicate<ITransaction> p = transaction -> transaction.getTags().contains(tag);

    @Test
    void addAccount() {
        assertTrue(l.getAccounts().isEmpty());
        a1 = l.addAccount("myAccount", "This is my account", 0.0, AccountType.ASSETS);
        assertTrue(l.getAccounts().contains(a1));
    }

    @Test
    void removeAccount() {
        addAccount();
        l.removeAccount(a1);
        assertTrue(l.getAccounts().isEmpty());
    }

    @Test
    void addTransaction() {
        l.addTransaction(t1);
        l.addTransaction(t2);
        assertTrue(l.getTransactions().contains(t1));
        assertTrue(l.getTransactions().contains(t2));
    }

    @Test
    void removeTransaction() {
        addTransaction();
        l.removeTransaction(t1);
        assertFalse(l.getTransactions().contains(t1));
        assertFalse(a1.getMovements().contains(m1));
    }

    @Test
    void testGetTransactions() {
        assertTrue(l.getTransactions(p).isEmpty());
        l.addTransaction(t1);
        l.addTransaction(t2);
        t1.addTag(tag);
        assertTrue(l.getTransactions(p).contains(t1));
        assertFalse(l.getTransactions(p).contains(t2));
    }

    @Test
    void addTag() {
        tag = l.addTag("Food", "I use this tag when I buy food", 10.0);
        assertTrue(l.getTags().contains(tag));
    }

    @Test
    void removeTag() {
        addTag();
        l.removeTag(tag);
        assertFalse(l.getTags().contains(tag));
    }
}
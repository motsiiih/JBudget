package it.unicam.cs.pa.jbudget105467;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {
    ILedger l = Ledger.getInstance();
    IAccount a1 = new Account("mioConto", "Questo è il mio conto", 0.0, AccountType.LIABILITIES);
    IAccount a2 = new Account("tuoConto", "Questo è il tuo conto", 0.0, AccountType.ASSETS);
    ITransaction t = new Transaction(LocalDate.now());
    IMovement m1 = new Movement(MovementType.CREDIT, 0, a1, t);
    IMovement m2 = new Movement(MovementType.DEBIT, 12, a1, t);
    IMovement m3 = new Movement(MovementType.CREDIT, 300, a2, t);
    IMovement m4 = new Movement(MovementType.CREDIT, 120, a1, t);
    IMovement m5 = new Movement(MovementType.DEBIT, 59, a2, t);

    @Test
    void getMovements() {
        t.addMovement(m1);
        t.addMovement(m2);
        t.addMovement(m3);
        t.addMovement(m4);
        t.addMovement(m5);
        l.addTransaction(t);
        assertTrue(a1.getMovements().contains(m1));
        assertTrue(a1.getMovements().contains(m2));
        assertTrue(a2.getMovements().contains(m3));
        assertTrue(a1.getMovements().contains(m4));
        assertTrue(a2.getMovements().contains(m5));
        assertEquals(2, a1.getMovements(movement -> movement.getAmount() < 80).size());
        assertEquals(1, a2.getMovements(movement -> movement.getAmount() > 100).size());
    }

    @Test
    void getBalance() {
        t.addMovement(m1);
        t.addMovement(m2);
        t.addMovement(m3);
        t.addMovement(m4);
        t.addMovement(m5);
        l.addTransaction(t);
        assertEquals(a1.getBalance(), -108);
        assertEquals(a2.getBalance(), 241);
    }
}
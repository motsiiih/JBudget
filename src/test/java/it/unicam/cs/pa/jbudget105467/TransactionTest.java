package it.unicam.cs.pa.jbudget105467;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {
    IAccount a1 = new Account("myAccount", "This is my account", 0.0, AccountType.LIABILITIES);
    IAccount a2 = new Account("yourAccount", "This is your account", 0.0, AccountType.ASSETS);
    ITransaction t1 = new Transaction(LocalDate.now());
    IMovement m1 = new Movement(MovementType.CREDIT, 45, a1, t1);
    IMovement m2 = new Movement(MovementType.DEBIT, 1.60, a1, t1);
    IMovement m3 = new Movement(MovementType.CREDIT, 110, a2, t1);
    IMovement m4 = new Movement(MovementType.DEBIT, 13.20, a2, t1);
    ITag tag = new Tag("Food", "I use this tag when I buy food", 10.0);

    private void addMovements() {
        t1.addMovement(m1);
        t1.addMovement(m2);
        t1.addMovement(m3);
        t1.addMovement(m4);
    }

    @Test
    void addMovement() {
       assertTrue(t1.getMovements().isEmpty());
       addMovements();
       assertTrue(t1.getMovements().contains(m1));
       assertTrue(t1.getMovements().contains(m2));
       assertTrue(t1.getMovements().contains(m3));
       assertTrue(t1.getMovements().contains(m4));
    }

    @Test
    void removeMovement() {
        t1.addMovement(m1);
        t1.addMovement(m2);
        t1.removeMovement(m1);
        assertTrue(t1.getMovements().contains(m2));
        t1.removeMovement(m2);
        assertTrue(t1.getMovements().isEmpty());
    }

    @Test
    void getMovements(){
        addMovements();
        assertTrue(t1.getMovements(m -> m.getAmount() > 2000).isEmpty());
        assertTrue(t1.getMovements(m -> m.getAmount() > 20).size() == 2);
    }

    @Test
    void addTag() {
        assertTrue(t1.getTags().isEmpty());
        assertTrue(m1.getTags().isEmpty());
        assertTrue(m2.getTags().isEmpty());
        addMovement();
        t1.addTag(tag);
        assertTrue(t1.getTags().contains(tag));
        assertTrue(m1.getTags().contains(tag));
        assertTrue(m2.getTags().contains(tag));
    }

    @Test
    void removeTag() {
        addTag();
        t1.removeTag(tag);
        assertTrue(t1.getTags().isEmpty());
        assertTrue(m1.getTags().isEmpty());
        assertTrue(m2.getTags().isEmpty());
    }

    @Test
    void getTotal() {
        assertTrue(t1.getMovements().isEmpty());
        assertEquals(0.0, t1.getTotal());
        addMovements();
        assertEquals(53.40, t1.getTotal());
    }
}
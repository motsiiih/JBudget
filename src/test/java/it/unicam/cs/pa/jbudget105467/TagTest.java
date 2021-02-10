package it.unicam.cs.pa.jbudget105467;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;


class TagTest {

    ILedger ledger = Ledger.getInstance();
    IAccount a2 = new Account("yourAccount", "This is your account", 0.0, AccountType.ASSETS);
    ITransaction t1 = new Transaction(LocalDate.now());
    IMovement m1 = new Movement(MovementType.CREDIT, 45, a2, t1);
    IMovement m2 = new Movement(MovementType.DEBIT, 110, a2, t1);
    IMovement m3 = new Movement(MovementType.DEBIT, 13.20, a2, t1);
    ITag tag = new Tag("Food", "I use this tag when I buy food", 10.0);

    @Test
    void getActual(){
        ledger.addTransaction(t1);
        t1.addMovement(m1);
        t1.addMovement(m2);
        t1.addMovement(m3);
        t1.addTag(tag);
        tag.calculateActual(ledger);
        assertEquals(78.20, tag.getActual());
    }

}
package it.unicam.cs.pa.jbudget105467;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class MovementTest {
    IAccount a1 = new Account("myAccount", "This is my account", 0.0, AccountType.LIABILITIES);
    ITransaction t = new Transaction(LocalDate.now());
    IMovement m = new Movement(MovementType.CREDIT, 120, a1, t);
    ITag tag = new Tag("Food", "I use this tag when I buy food", 10.0);

    @Test
    void getDate() {
        t.addMovement(m);
        t.setDate(LocalDate.now());
        assertEquals(m.getDate(), t.getDate());
    }

    @Test
    void addTag() {
        assertFalse(m.getTags().contains(tag));
        m.addTag(tag);
        assertTrue(m.getTags().contains(tag));
    }

    @Test
    void removeTag() {
        this.addTag();
        m.removeTag(tag);
        assertFalse(m.getTags().contains(tag));
    }
}